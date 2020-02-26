package com.dommyg.reoutbreakteamcommunicator;

/**
 * Contains all the information used to create a room and maintain the Control Panel displays.
 */
class Room {
    private Player playerUser;
    private Scenario scenario;
    private String name;
    private String password;

    Room(Player playerUser, Scenario scenario, String name, String password) {
        this.playerUser = playerUser;
        this.scenario = scenario;
        this.name = name;
        this.password = password;
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

    String getPassword() {
        return password;
    }
}
