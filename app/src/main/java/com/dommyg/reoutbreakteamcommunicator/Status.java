package com.dommyg.reoutbreakteamcommunicator;

enum StatusType {
    NONE (0, 0),
    PANIC (1, R.string.button_status_panic),
    NEED (2, R.string.button_status_need),
    DEAD (3, R.string.button_status_dead);

    private final int type;
    private final int name;

    StatusType(int type, int name) {
        this.type = type;
        this.name = name;
    }

    public int getName() {
        return name;
    }
}

class Status {
    StatusType statusType;
    String location;
    String item;

    public Status(StatusType statusType) {
        this.statusType = statusType;
    }

    public int getStatusType() {
        return statusType.getName();
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }
}
