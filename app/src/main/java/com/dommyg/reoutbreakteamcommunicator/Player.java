package com.dommyg.reoutbreakteamcommunicator;

enum Character {
    ALYSSA (R.string.character_alyssa),
    CINDY (R.string.character_cindy),
    DAVID (R.string.character_david),
    GEORGE (R.string.character_george),
    JIM (R.string.character_jim),
    KEVIN (R.string.character_kevin),
    MARK (R.string.character_mark),
    YOKO (R.string.character_yoko);

    private final int name;

    Character(int name) {
        this.name = name;
    }
}

class Player {
    private Status status;
    private Character character;

    public Player(Character character) {
        this.character = character;
        this.status = new Status(StatusType.NONE);
    }
}
