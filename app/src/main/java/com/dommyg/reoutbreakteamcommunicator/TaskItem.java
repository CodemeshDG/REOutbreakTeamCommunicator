package com.dommyg.reoutbreakteamcommunicator;

public class TaskItem {
    private String taskName;
    private String taskStatus;

    public TaskItem(String taskName, String taskStatus) {
        this.taskName = taskName;
        this.taskStatus = taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }
}
