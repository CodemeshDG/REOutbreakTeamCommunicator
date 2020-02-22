package com.dommyg.reoutbreakteamcommunicator;

public class TaskItem {
    private String taskName;
    private String taskStatus;

    private int[] taskPlayerStatuses = new int[4];

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

    public int[] getTaskPlayerStatuses() {
        return taskPlayerStatuses;
    }

    public void setTaskPlayerStatuses(int[] taskPlayerStatuses) {
        this.taskPlayerStatuses = taskPlayerStatuses;
    }
}
