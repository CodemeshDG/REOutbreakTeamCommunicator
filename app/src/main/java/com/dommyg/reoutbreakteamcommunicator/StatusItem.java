package com.dommyg.reoutbreakteamcommunicator;

class StatusItem {

    private String playerStatus;
    private String playerSubStatus;
    private String headshotPath;

    public StatusItem() {
        // Empty constructor for Firestore RecyclerView use
    }

    public String getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(String playerStatus) {
        this.playerStatus = playerStatus;
    }

    public String getPlayerSubStatus() {
        return playerSubStatus;
    }

    public void setPlayerSubStatus(String playerSubStatus) {
        this.playerSubStatus = playerSubStatus;
    }

    public String getHeadshotPath() {
        return headshotPath;
    }

    public void setHeadshotPath(String headshotPath) {
        this.headshotPath = headshotPath;
    }
}
