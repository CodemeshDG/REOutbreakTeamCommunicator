package com.dommyg.reoutbreakteamcommunicator;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Contains all the information used to create a room and maintain the Control Panel displays.
 */
class Room {
    private Player playerUser;
    private Scenario scenario;
    private String roomName;
    private String username;
    private String[] characterNames;
    private CollectionReference playersReference;
    private CollectionReference tasksReference;
    private FirestorePlayerController firestorePlayerController;

    Room(Player playerUser, Scenario scenario, String roomName, String username) {
        this.playerUser = playerUser;
        this.scenario = scenario;
        this.roomName = roomName;
        this.username = username;
        this.characterNames = new String[4];
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference roomReference = db.collection("rooms")
                .document(roomName);
        this.playersReference = roomReference.collection("players");
        this.tasksReference = roomReference.collection("tasks");
    }

    Player getPlayerUser() {
        return playerUser;
    }

    Scenario getScenario() {
        return scenario;
    }

    String getRoomName() {
        return roomName;
    }

    String getUsername() {
        return username;
    }

    String[] getCharacterNames() {
        return characterNames;
    }

    void setCharacterNames(String[] characterNames) {
        this.characterNames = characterNames;
    }

    CollectionReference getPlayersReference() {
        return playersReference;
    }

    CollectionReference getTasksReference() {
        return tasksReference;
    }

    void startListeningToPlayerReference() {
        firestorePlayerController = new FirestorePlayerController(this);
    }

    void stopListeningToPlayerReference() {
        firestorePlayerController.stopListeningToPlayerReference();
    }
}
