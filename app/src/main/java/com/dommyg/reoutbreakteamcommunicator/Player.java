package com.dommyg.reoutbreakteamcommunicator;

/**
 * Contains each character's name and headshot file name.
 */
enum Character {
    ALYSSA (R.string.character_alyssa, "headshot_alyssa.jpg"),
    CINDY (R.string.character_cindy, "headshot_cindy.jpg"),
    DAVID (R.string.character_david, "headshot_david.jpg"),
    GEORGE (R.string.character_george, "headshot_george.jpg"),
    JIM (R.string.character_jim, "headshot_jim.jpg"),
    KEVIN (R.string.character_kevin, "headshot_kevin.png"),
    MARK (R.string.character_mark, "headshot_mark.jpg"),
    YOKO (R.string.character_yoko, "headshot_yoko.jpg");

    private final int name;
    private final String headshotPath;
    private static final String HEADSHOT_DIRECTORY = "character_headshots";

    Character(int name, String headshotPath) {
        this.name = name;
        this.headshotPath = headshotPath;
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
    private Status status;
    private Character character;
    private boolean[][] taskProgress;

    Player(Character character, TaskMaster taskMaster) {
        this.character = character;
        this.status = new Status(StatusType.NONE);
        initializeTaskProgress(taskMaster);
    }

    private void initializeTaskProgress(TaskMaster taskMaster) {
        int taskSetsSize = taskMaster.getTaskSetsSize();
        taskProgress = new boolean[taskSetsSize][];

        for (int i = 0; i < taskSetsSize; i++) {
            int tasksSize = taskMaster.getTaskSets()[i].getTasksSize();
            taskProgress[i] = new boolean[tasksSize];
        }
    }

    Status getStatus() {
        return status;
    }

    int getCharacterName() {
        return character.getName();
    }

    String getHeadshot() {
        return character.getHeadshotPath();
    }

    boolean[][] getTaskProgress() {
        return taskProgress;
    }
}
