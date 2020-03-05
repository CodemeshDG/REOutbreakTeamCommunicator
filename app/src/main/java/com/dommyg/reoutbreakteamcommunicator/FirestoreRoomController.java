package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

class FirestoreRoomController {
    private static final String TAG = "FirestoreRoomController";

    private final String uid = FirebaseAuth.getInstance().getUid();

    private final Context context;

    private static final String KEY_INPUT_PASSWORD = "inputPassword";
    private static final String KEY_ROOM_NAME = "roomName";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_OWNER = "owner";
    private static final String KEY_IS_OWNER = "isOwner";
    private static final String KEY_NUMBER_PLAYERS = "numberPlayers";
    private static final String KEY_SCENARIO = "scenario";
    private static final String KEY_TASK_SET = "taskSet";
    private static final String KEY_PLAYER_1_TASK_STATUS = "player1TaskStatus";
    private static final String KEY_PLAYER_2_TASK_STATUS = "player2TaskStatus";
    private static final String KEY_PLAYER_3_TASK_STATUS = "player3TaskStatus";
    private static final String KEY_PLAYER_4_TASK_STATUS = "player4TaskStatus";


    private static final int CODE_CREATE = 1;
    private static final int CODE_JOIN = 2;
    private static final int CODE_LEAVE = 3;
    private static final int CODE_DELETE = 4;
    private static final int CODE_CHANGE_PASSWORD = 5;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference roomsReference = db.collection("rooms");
    private final CollectionReference masterUserReference = db.collection("masterUserList");
    private final CollectionReference masterRoomReference = db.collection("masterRoomList");

    FirestoreRoomController(Context context) {
        this.context = context;
    }

    /**
     * Processes a request to create a room.
     */
    void createRoom(Resources resources, String password, String username, String roomName,
                    Character selectedCharacter, ScenarioName selectedScenario) {
        readMasterRoomList(resources, password, username, roomName, selectedCharacter,
                selectedScenario);
    }

    /**
     * Processes a request to join a room.
     */
    void joinRoom(String password, String username, String roomName, boolean newJoin) {
        setInputPassword(CODE_JOIN, password, username, roomName, newJoin);
    }

    /**
     * Processes a request to leave a room; available only to non-owners of the room.
     */
    void leaveRoom(String password, String username, String roomName) {
        setInputPassword(CODE_LEAVE, password, username, roomName, false);
    }

    /**
     * Processes a request to delete a room; available only to owners of the room.
     */
    void deleteRoom(String password, String username, String roomName) {
        setInputPassword(CODE_DELETE, password, username, roomName, false);
    }

    /**
     * Sets the "inputPassword" field for the user so that they may read and write to certain documents
     * in the room. This is a required set to join, leave, or delete a room.
     */
    private void setInputPassword(int command, String password, String username, String roomName,
                                  boolean newJoin) {
        Map<String, String> mapInputPassword = new HashMap<>();
        mapInputPassword.put(KEY_INPUT_PASSWORD, password);

        masterUserReference.document(uid)
                .set(mapInputPassword, SetOptions.merge())
                .addOnSuccessListener(new WriteInputPasswordOnSuccessListener(command, password,
                        username, roomName, newJoin))
                .addOnFailureListener(new ActionFailureListener(command));
    }

    /**
     * Reads the "masterRoomList" to verify if a room already exists.
     */
    private void readMasterRoomList(Resources resources, String password, String username,
                                    String roomName, Character selectedCharacter,
                                    ScenarioName selectedScenario) {
        masterRoomReference.document(roomName)
                .get()
                .addOnCompleteListener(new ReadMasterRoomListOnCompleteListener(resources, password,
                        username, roomName, selectedCharacter, selectedScenario));
    }

    /**
     * Creates and commits a batch write to set up a new room.
     */
    private void batchWriteToCreateRoom(Resources resources, String password, String username,
                                        String roomName, Character selectedCharacter,
                                        ScenarioName selectedScenario) {
        WriteBatch creationBatch = db.batch();

        // Stores the room name in the "masterRoomList" collection for future querying (to prevent
        // someone from making a room with the same name and overwriting this room's data).
        Map<String, Object> mapRoom = new HashMap<>();
        mapRoom.put(KEY_ROOM_NAME, roomName);

        creationBatch.set(masterRoomReference.document(roomName), mapRoom);

        // Creates the room document in the "rooms" collection, storing its name, password, the
        // owner, which allows for other users to join with a password and enables the owner to
        // delete the room rather than leave. It also stores the scenario level and the number of
        // players.
        mapRoom.put(KEY_PASSWORD, password);
        mapRoom.put(KEY_OWNER, username);
        mapRoom.put(KEY_SCENARIO, selectedScenario.getLevel());
        mapRoom.put(KEY_NUMBER_PLAYERS, 1);

        creationBatch.set(roomsReference.document(roomName), mapRoom);

        // Creates the "players" collection in the room's document and adds the owner as the first
        // player. This document holds information related to the player's character info and
        // status.
        DocumentReference playerReference = roomsReference.document(roomName)
                .collection("players").document(username);

        new FirestorePlayerController().createPlayer(resources, creationBatch, playerReference,
                selectedCharacter, 1);

        // Creates the "tasks" collection in the room's document and creates a document for each
        // task in the scenario. This document holds information about its associated task set (for
        // filtered querying for use with the TaskAdapter) and each player's status for that task.
        CollectionReference tasksReference = roomsReference.document(roomName)
                .collection("tasks");

        setTaskDocumentsIntoBatch(creationBatch, tasksReference, selectedScenario);

        // Stores into the user's "joinedRooms" collection the room name of the room being joined,
        // its password, and if the user is the room's owner. This allows for rejoining without
        // inputting a password in the future from the main menu, as well as appropriate actions for
        // disengagement with the room (leaving room for non-owners and deleting the room for
        // owners).
        Map<String, Object> mapRoomInfo = new HashMap<>();
        mapRoomInfo.put(KEY_ROOM_NAME, roomName);
        mapRoomInfo.put(KEY_PASSWORD, password);
        mapRoomInfo.put(KEY_IS_OWNER, true);

        creationBatch.set(masterUserReference
                        .document(uid)
                        .collection("joinedRooms")
                        .document(roomName),
                mapRoomInfo);

        creationBatch.commit()
                .addOnSuccessListener(new BatchWriteCreateRoomOnSuccessListener(username, roomName))
                .addOnFailureListener(new ActionFailureListener(CODE_CREATE));
    }

    /**
     * Creates and sets documents for each scenario task into the creationBatch.
     */
    private void setTaskDocumentsIntoBatch(WriteBatch creationBatch,
                                   CollectionReference tasksReference,
                                   ScenarioName scenarioName) {
        int[] taskNumbers = new TaskMaster().getTaskNumbers(scenarioName);
        int taskSetsNumber = taskNumbers.length + 1;
        for (int i = 1; i < taskSetsNumber; i++) {
            char j = 'a';
            for (int x = 0; x < taskNumbers[i]; x++) {
                Map<String, Integer> mapTask = new HashMap<>();
                mapTask.put(KEY_TASK_SET, i);
                mapTask.put(KEY_PLAYER_1_TASK_STATUS, 0);
                mapTask.put(KEY_PLAYER_2_TASK_STATUS, 0);
                mapTask.put(KEY_PLAYER_3_TASK_STATUS, 0);
                mapTask.put(KEY_PLAYER_4_TASK_STATUS, 0);
                String documentName = Integer.toString(i) + j;

                creationBatch.set(tasksReference.document(documentName), mapTask);

                j++;
            }
        }
    }

//    /**
//     * Returns the Level of the scenario selected to be used for storing into the database.
//     */
//    private int getScenarioLevel(int selectedScenario) {
//        switch (selectedScenario) {
//            case R.id.radioButtonScenario1:
//                return ScenarioName.OUTBREAK.getLevel();
//            case R.id.radioButtonScenario2:
//                return ScenarioName.BELOW_FREEZING_POINT.getLevel();
//            case R.id.radioButtonScenario3:
//                return ScenarioName.THE_HIVE.getLevel();
//            case R.id.radioButtonScenario4:
//                return ScenarioName.HELLFIRE.getLevel();
//            case R.id.radioButtonScenario5:
//                return ScenarioName.DECISIONS_DECISIONS.getLevel();
//            default:
//                return -1;
//        }
//    }

    /**
     * Listens for completion of searching for a room in the database.
     */
    private class ReadMasterRoomListOnCompleteListener implements OnCompleteListener<DocumentSnapshot> {
        private Resources resources;
        private String password;
        private String username;
        private String roomName;
        private Character selectedCharacter;
        private ScenarioName selectedScenario;

        ReadMasterRoomListOnCompleteListener(Resources resources, String password, String username,
                                             String roomName, Character selectedCharacter,
                                             ScenarioName selectedScenario) {
            this.resources = resources;
            this.password = password;
            this.username = username;
            this.roomName = roomName;
            this.selectedCharacter = selectedCharacter;
            this.selectedScenario = selectedScenario;
        }

        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                if (document.exists()) {
                    Toast.makeText(context, "This room name already exists.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // User entered a room name which does not exist and a password; create the room.
                    batchWriteToCreateRoom(resources, password, username, roomName,
                            selectedCharacter, selectedScenario);
                }
            }
        }
    }

    /**
     * Listens for success on modifying the user's "inputPassword" field, and then takes appropriate
     * action based upon the user's request (joining, leaving, or deleting room).
     */
    private class WriteInputPasswordOnSuccessListener implements OnSuccessListener<Void> {
        private int command;
        private String password;
        private String username;
        private String roomName;
        private boolean newJoin;

        WriteInputPasswordOnSuccessListener(int command, String password, String username,
                                            String roomName, boolean newJoin) {
            this.command = command;
            this.password = password;
            this.username = username;
            this.roomName = roomName;
            this.newJoin = newJoin;
        }

        @Override
        public void onSuccess(Void aVoid) {
            switch (command) {
                case CODE_JOIN:
                    // Search for room.
                    roomsReference.document(roomName).get()
                            .addOnCompleteListener(new ReadRoomsOnCompleteListener(password,
                                    username, roomName, newJoin));
                    break;

                case CODE_LEAVE:
                    // Delete user from room.
                    WriteBatch leaveBatch = db.batch();

                    leaveBatch.delete(roomsReference
                            .document(roomName)
                            .collection("users")
                            .document(username));

                    leaveBatch.delete(masterUserReference.document(uid)
                            .collection("joinedRooms")
                            .document(roomName));

                    leaveBatch.commit()
                            .addOnSuccessListener(new BatchWriteLeaveRoomOnSuccessListener(roomName))
                            .addOnFailureListener(new ActionFailureListener(command));
                    break;

                case CODE_DELETE:
                    // Search for all users' documents related to the room.
                    roomsReference.document(roomName)
                            .collection("users")
                            .get()
                            .addOnCompleteListener(new ReadUsersOnCompleteListener(roomName));
            }
        }
    }

    /**
     * Listens for completion of searching for a room in the database, and then attempts to join it
     * if it exists.
     */
    private class ReadRoomsOnCompleteListener implements OnCompleteListener<DocumentSnapshot> {
        private String password;
        private String username;
        private String roomName;
        private boolean newJoin;

        ReadRoomsOnCompleteListener(String password, String username, String roomName,
                                    boolean newJoin) {
            this.password = password;
            this.username = username;
            this.roomName = roomName;
            this.newJoin = newJoin;
        }

        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            // TODO: See if more accurate errors can be reported based upon current database security
            //  rules. Use onSuccessListener instead?
            if (task.isSuccessful()) {
                // Was able to search for room.
                DocumentSnapshot document = task.getResult();

                if (document.exists()) {
                    // Room document exists.
                    if (password.equals(document.getString(MainPanelActivity.KEY_PASSWORD))) {
                        // Password is correct.
                        if (newJoin) {
                            // User is joining this room for the first time. Stores into the user's
                            // "joinedRooms" collection the room name of the room being joined, its
                            // password, and if the user is the room's owner. This allows for
                            // rejoining without inputting a password in the future from the main
                            // menu, as well as appropriate actions for disengagement with the room
                            // (leaving room for non-owners and deleting the room for owners).
                            Map<String, Object> mapRoomInfo = new HashMap<>();
                            mapRoomInfo.put(KEY_ROOM_NAME, roomName);
                            mapRoomInfo.put(KEY_PASSWORD, password);
                            mapRoomInfo.put(KEY_OWNER, false);

                            masterUserReference.document(uid)
                                    .collection("joinedRooms")
                                    .document(roomName)
                                    .set(mapRoomInfo)
                                    .addOnSuccessListener(new WriteUsersJoinedRoomOnSuccessListener(
                                            username, roomName))
                                    .addOnFailureListener(new ActionFailureListener(CODE_JOIN));
                        } else {
                            // User is rejoining this room and does not require his "joinedRooms"
                            // collection to be updated.
                            startMainPanelActivity(username, roomName);
                        }
                    } else {
                        // User entered the wrong password for the room.
                        Toast.makeText(context, "ERROR: Incorrect password.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // User entered a room name which does not exist.
                    Toast.makeText(context, "ERROR: No such room exists.",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                // Could not search for rooms for some reason.
                Toast.makeText(context, "ERROR: Could not search for room.",
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onComplete: " + task.getException());
            }
        }
    }

    /**
     * Listens for completion of searching for a room's user documents in the database, and then
     * attempts to delete them, along with the room's document in both the "rooms" and
     * "masterRoomsList" collections.
     */
    private class ReadUsersOnCompleteListener implements OnCompleteListener<QuerySnapshot> {
        private String roomName;

        ReadUsersOnCompleteListener(String roomName) {
            this.roomName = roomName;
        }

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                // Was able to retrieve users' documents.

                QuerySnapshot documents = task.getResult();

                WriteBatch deletionBatch = db.batch();

                for (QueryDocumentSnapshot document : documents) {
                    deletionBatch.delete(document.getReference());
                }

                deletionBatch.delete(roomsReference.document(roomName));

                deletionBatch.delete(masterUserReference.document(uid)
                        .collection("joinedRooms")
                        .document(roomName));

                deletionBatch.delete(masterRoomReference.document(roomName));

                deletionBatch.commit()
                        .addOnSuccessListener(new BatchWriteDeleteRoomOnSuccessListener(roomName))
                        .addOnFailureListener(new ActionFailureListener(CODE_DELETE));

            } else {
                Toast.makeText(context, "Error connecting to database.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Listens for success of the write required to save the room info into the user's "joinedRooms"
     * collection in the database, and then launches the MainPanelActivity to bring the user to the
     * room.
     */
    private class WriteUsersJoinedRoomOnSuccessListener implements OnSuccessListener<Void> {
        private String username;
        private String roomName;

        WriteUsersJoinedRoomOnSuccessListener(String username, String roomName) {
            this.username = username;
            this.roomName = roomName;
        }

        @Override
        public void onSuccess(Void aVoid) {
            startMainPanelActivity(username, roomName);
        }
    }

    /**
     * Listens for success of the batched write required to create room documentation in the
     * database, and then launches the MainPanelActivity to bring the user to their new room.
     */
    private class BatchWriteCreateRoomOnSuccessListener implements OnSuccessListener<Void> {
        private String username;
        private String roomName;

        BatchWriteCreateRoomOnSuccessListener(String username, String roomName) {
            this.username = username;
            this.roomName = roomName;
        }

        @Override
        public void onSuccess(Void aVoid) {
            startMainPanelActivity(username, roomName);
        }
    }

    /**
     * Listens for success of the batched write required to delete the proper documentation in the
     * database for leaving a room, and then shows a successful Toast message.
     */
    private class BatchWriteLeaveRoomOnSuccessListener implements OnSuccessListener<Void> {
        private String roomName;

        BatchWriteLeaveRoomOnSuccessListener(String roomName) {
            this.roomName = roomName;
        }

        @Override
        public void onSuccess(Void aVoid) {
            Toast.makeText(context, "Left room: " + roomName, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Listens for success of the batched write required to delete the proper documentation in the
     * database for deleting a room, and then shows a successful Toast message.
     */
    private class BatchWriteDeleteRoomOnSuccessListener implements OnSuccessListener<Void> {
        private String roomName;

        BatchWriteDeleteRoomOnSuccessListener(String roomName) {
            this.roomName = roomName;
        }

        @Override
        public void onSuccess(Void aVoid) {
            Toast.makeText(context, "Deleted room: " + roomName, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Listens for action failures in the RoomController class and provides the appropriate Toast
     * message for the user.
     */
    private class ActionFailureListener implements OnFailureListener {
        private String failureMessage;

        ActionFailureListener(int errorCode) {
            switch (errorCode) {
                case CODE_CREATE:
                    failureMessage = "Error creating room.";
                    break;

                case CODE_JOIN:
                    failureMessage = "Error searching for room.";
                    break;

                case CODE_LEAVE:
                    failureMessage = "Error trying to leave room.";
                    break;

                case CODE_DELETE:
                    failureMessage = "Error trying to delete room.";
                    break;

                case CODE_CHANGE_PASSWORD:
                    failureMessage = "Error accessing database.";
            }
        }

        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onFailure: " + e);
        }
    }

    private void startMainPanelActivity(String username, String roomName) {
        Intent intent = MainPanelActivity.newIntent(context, username, roomName);
        context.startActivity(intent);
    }
}
