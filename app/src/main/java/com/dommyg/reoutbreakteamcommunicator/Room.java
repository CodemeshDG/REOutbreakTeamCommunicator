package com.dommyg.reoutbreakteamcommunicator;

/**
 * Contains all the information used to create a room and maintain the Control Panel displays.
 */
class Room {
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Scenario scenario;
    private String name;
    private String password;

    Room(Player player1, Scenario scenario, String name, String password) {
        this.player1 = player1;
        this.player2 = null;
        this.player3 = null;
        this.player4 = null;
        this.scenario = scenario;
        this.name = name;
        this.password = password;
    }

    /**
     * The user's player.
     */
    Player getPlayer1() {
        return player1;
    }

    Player getPlayer2() {
        return player2;
    }

    Player getPlayer3() {
        return player3;
    }

    Player getPlayer4() {
        return player4;
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
