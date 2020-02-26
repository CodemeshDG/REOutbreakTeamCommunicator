package com.dommyg.reoutbreakteamcommunicator;

public class TaskItem {
    private String taskName;
    private String taskStatus;

    private int player1TaskStatus;
    private int player2TaskStatus;
    private int player3TaskStatus;
    private int player4TaskStatus;

    public TaskItem() {
        // Empty constructor for Firestore RecyclerView use
    }

    public TaskItem(String taskName, String taskStatus) {
        this.taskName = taskName;
        this.taskStatus = taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getPlayer1TaskStatus() {
        return player1TaskStatus;
    }

    public void setPlayer1TaskStatus(int player1TaskStatus) {
        this.player1TaskStatus = player1TaskStatus;
    }

    public int getPlayer2TaskStatus() {
        return player2TaskStatus;
    }

    public void setPlayer2TaskStatus(int player2TaskStatus) {
        this.player2TaskStatus = player2TaskStatus;
    }

    public int getPlayer3TaskStatus() {
        return player3TaskStatus;
    }

    public void setPlayer3TaskStatus(int player3TaskStatus) {
        this.player3TaskStatus = player3TaskStatus;
    }

    public int getPlayer4TaskStatus() {
        return player4TaskStatus;
    }

    public void setPlayer4TaskStatus(int player4TaskStatus) {
        this.player4TaskStatus = player4TaskStatus;
    }
}
