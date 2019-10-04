package com.dommyg.reoutbreakteamcommunicator;

enum StatusType {
    NONE (0, ""),
    PANIC (1, "Panic"),
    NEED (2, "Need"),
    DEAD (3, "Dead");

    private final int type;
    private final String name;

    StatusType(int type, String name) {
        this.type = type;
        this.name = name;
    }
}

class Status {
    StatusType statusType;
    String location;
    String item;

    public Status(StatusType statusType) {
        this.statusType = statusType;
    }
}
