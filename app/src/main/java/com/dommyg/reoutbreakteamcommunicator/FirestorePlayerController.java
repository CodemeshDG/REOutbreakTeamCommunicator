package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

class FirestorePlayerController {
    private static final String TAG = "FirestorePlayerControll";

    final String KEY_PLAYER_NUMBER = "playerNumber";
    private final String KEY_CHARACTER_NAME = "characterName";
    private final String KEY_HEADSHOT_PATH = "headshotPath";
    private final String KEY_PLAYER_STATUS = "playerStatus";
    private final String KEY_PLAYER_SUB_STATUS = "playerSubStatus";

    private Room room = null;
    private ListenerRegistration registration = null;

    /**
     * Constructor used to read and write to "players" collection through createPlayer() and
     * updateStatus().
     */
    FirestorePlayerController() {

    }

    /**
     * Constructor used by a Room to listen to the "players" collection to update the Room's
     * characterNames.
     */
    FirestorePlayerController(@NonNull Room room, String roomName) {
        this.room = room;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference playersReference = db.collection("rooms")
                .document(roomName)
                .collection("players");
        Query playersReferenceQuery = playersReference.orderBy(KEY_PLAYER_NUMBER,
                Query.Direction.ASCENDING);
        this.registration = playersReferenceQuery.addSnapshotListener(
                new PlayersReferenceListener());
    }

    void createPlayer(Resources resources, WriteBatch writeBatch,
                      DocumentReference documentReference, Character character,
                      int playerNumber) {

        Map<String, Object> mapPlayer = new HashMap<>();
        mapPlayer.put(KEY_PLAYER_NUMBER, playerNumber);
        mapPlayer.put(KEY_CHARACTER_NAME, resources.getString(character.getName()));
        mapPlayer.put(KEY_HEADSHOT_PATH, character.getHeadshotPath());
        mapPlayer.put(KEY_PLAYER_STATUS, new StatusBuilder().create(resources,
                resources.getString(character.getName()), StatusType.NONE));
        mapPlayer.put(KEY_PLAYER_SUB_STATUS, new SubStatusBuilder().create(null, null,
                null, StatusType.NONE));

        writeBatch.set(documentReference, mapPlayer);
    }

    void updateStatus(ChangeStatusActivity activity, StatusType statusType,
                      DocumentReference documentReference, String status, String subStatus) {
        Map<String, Object> data = new HashMap<>();
        data.put(KEY_PLAYER_STATUS, status);
        data.put(KEY_PLAYER_SUB_STATUS, subStatus);

        documentReference.update(data)
                .addOnSuccessListener(new UpdateOnSuccessListener(activity, statusType))
                .addOnFailureListener(new UpdateOnFailureListener(activity.getBaseContext()));
    }

    private static class UpdateOnSuccessListener implements OnSuccessListener<Void> {
        ChangeStatusActivity activity;
        StatusType statusType;

        UpdateOnSuccessListener(ChangeStatusActivity activity, StatusType statusType) {
            this.statusType = statusType;
            this.activity = activity;
        }

        @Override
        public void onSuccess(Void aVoid) {
            activity.setResult(statusType.getType());
            activity.finish();
        }
    }

    private static class UpdateOnFailureListener implements OnFailureListener {
        private Context context;

        UpdateOnFailureListener(Context context) {
            this.context = context;
        }

        @Override
        public void onFailure(@NonNull Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error attempting to update status.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    void stopListeningToPlayerReference() {
        registration.remove();
    }

    /**
     * Listens for changes in the "players" collection. When a change occurs, it takes the documents
     * in order of playerNumber, adds the characterNames to a String[] and sets the Room's
     * characterNames to it.
     */
    private class PlayersReferenceListener implements EventListener<QuerySnapshot> {
        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                            @Nullable FirebaseFirestoreException e) {
            if (e != null) {
                Log.w(TAG, "onEvent: Listen failed.", e);
                return;
            }

            if (room != null && queryDocumentSnapshots != null) {
                String[] updatedCharacterNames = new String[4];

                for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                    String characterName = (String) queryDocumentSnapshots.getDocuments()
                            .get(i)
                            .get(KEY_CHARACTER_NAME);

                    updatedCharacterNames[i] = characterName;
                }

                room.setCharacterNames(updatedCharacterNames);
            } else {
                Log.i(TAG, "onEvent: Something went wrong; room and/or documentSnapshot is null.");
            }
        }
    }
}
