package com.dommyg.reoutbreakteamcommunicator;

class Room {
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    String name;
    String password;
    Scenario scenario;

    public Room(Player player1, String name, String password, Scenario scenario) {
        this.player1 = player1;
        this.player2 = null;
        this.player3 = null;
        this.player4 = null;
        this.name = name;
        this.password = password;
        this.scenario = scenario;
    }
}
