package com.dommyg.reoutbreakteamcommunicator;

import com.google.firebase.firestore.CollectionReference;

/**
 * Contains all the information used to create a room and maintain the Control Panel displays.
 */
class Room {
    private Player playerUser;
    private Scenario scenario;
    private String name;
    private String[] characterNames;
    private FirestorePlayerController firestorePlayerController;

    Room(Player playerUser, Scenario scenario, String name) {
        this.playerUser = playerUser;
        this.scenario = scenario;
        this.name = name;
        this.characterNames = new String[4];
        this.firestorePlayerController = null;
    }

    Player getPlayerUser() {
        return playerUser;
    }

    Scenario getScenario() {
        return scenario;
    }

    String getName() {
        return name;
    }

    String[] getCharacterNames() {
        return characterNames;
    }

    void setCharacterNames(String[] characterNames) {
        this.characterNames = characterNames;
    }

    void listenToPlayerReference() {
        firestorePlayerController = new FirestorePlayerController(this);
    }

    void stopListeningToPlayerReference() {
        firestorePlayerController.stopListeningToPlayerReference();
    }
}
