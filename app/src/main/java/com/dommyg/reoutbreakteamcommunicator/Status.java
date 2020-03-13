package com.dommyg.reoutbreakteamcommunicator;

/**
 * Contains a numerical "type" (starting at zero) and a String name for each status.
 */
enum StatusType {
    NONE (0, R.string.text_status_fine),
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

    public int getType() {
        return type;
    }
}

/**
 * Contains information about a player's status to be displayed on the Control Panel.
 */
class Status {
    StatusType statusType;
    String location;
    String item;

    Status(StatusType statusType) {
        this.statusType = statusType;
    }

    int getStatusType() {
        return statusType.getName();
    }

    void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }
}
