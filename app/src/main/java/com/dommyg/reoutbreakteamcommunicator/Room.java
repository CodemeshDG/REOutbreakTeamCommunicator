package com.dommyg.reoutbreakteamcommunicator;

/**
 * Contains all the information used to create a room and maintain the Control Panel displays.
 */
class Room {
    private Player playerUser;
    private Scenario scenario;
    private String name;
    private String[] characterNames;

    Room(Player playerUser, Scenario scenario, String name, String[] characterNames) {
        this.playerUser = playerUser;
        this.scenario = scenario;
        this.name = name;
        this.characterNames = characterNames;
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
}
