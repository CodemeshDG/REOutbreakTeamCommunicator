package com.dommyg.reoutbreakteamcommunicator;

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

class Player {
    private Status status;
    private Character character;

    public Player(Character character) {
        this.character = character;
        this.status = new Status(StatusType.NONE);
    }

    public Status getStatus() {
        return status;
    }

    public int getCharacterName() {
        return character.getName();
    }

    public String getHeadshot() {
        return character.getHeadshotPath();
    }

}
