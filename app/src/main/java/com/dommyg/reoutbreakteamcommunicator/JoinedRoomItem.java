package com.dommyg.reoutbreakteamcommunicator;

class JoinedRoomItem {
    private String roomName;
    private String password;
    private boolean isOwner;
    private int scenario;
    private int character;
    private int playerNumber;

    public JoinedRoomItem() {
        // Empty constructor for Firestore RecyclerView use
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean owner) {
        isOwner = owner;
    }

    public int getScenario() {
        return scenario;
    }

    public void setScenario(int scenario) {
        this.scenario = scenario;
    }

    public int getCharacter() {
        return character;
    }

    public void setCharacter(int character) {
        this.character = character;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
