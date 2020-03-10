package com.dommyg.reoutbreakteamcommunicator;

/**
 * Contains each character's name and headshot file name.
 */
enum Character {
    ALYSSA (0, R.string.character_alyssa, "headshot_alyssa.jpg"),
    CINDY (1, R.string.character_cindy, "headshot_cindy.jpg"),
    DAVID (2, R.string.character_david, "headshot_david.jpg"),
    GEORGE (3, R.string.character_george, "headshot_george.jpg"),
    JIM (4, R.string.character_jim, "headshot_jim.jpg"),
    KEVIN (5, R.string.character_kevin, "headshot_kevin.png"),
    MARK (6, R.string.character_mark, "headshot_mark.jpg"),
    YOKO (7, R.string.character_yoko, "headshot_yoko.jpg");

    private final int number;
    private final int name;
    private final String headshotPath;
    private static final String HEADSHOT_DIRECTORY = "character_headshots";

    Character(int number, int name, String headshotPath) {
        this.number = number;
        this.name = name;
        this.headshotPath = headshotPath;
    }

    public int getNumber() {
        return number;
    }

    public int getName() {
        return name;
    }

    public String getHeadshotPath() {
        return HEADSHOT_DIRECTORY + "/" + headshotPath;
    }
}

/**
 * Contains information about a user's character and their status.
 */
class Player {
    private int playerNumber;
    private Status status;
    private Character character;
    private boolean[][][] taskProgress;

    Player(int playerNumber, Character character, TaskMaster taskMaster) {
        this.playerNumber = playerNumber;
        this.character = character;
        this.status = new Status(StatusType.NONE);
        initializeTaskProgress(taskMaster);
    }

    private void initializeTaskProgress(TaskMaster taskMaster) {
        int taskSetsSize = taskMaster.getTaskSetsSize();
        taskProgress = new boolean[taskSetsSize][][];

        for (int i = 0; i < taskSetsSize; i++) {
            int tasksSize = taskMaster.getTaskSets()[i].getTasksSize();
            taskProgress[i] = new boolean[tasksSize][3];
        }
    }

    int getPlayerNumber() {
        return playerNumber;
    }

    Status getStatus() {
        return status;
    }

    int getCharacterName() {
        return character.getName();
    }

    boolean[][][] getTaskProgress() {
        return taskProgress;
    }

    void setTaskProgress(int taskSet, int task, int checkBox, boolean isChecked) {
        this.taskProgress[taskSet][task][checkBox] = isChecked;
    }
}
