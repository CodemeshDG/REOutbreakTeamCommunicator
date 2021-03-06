package com.dommyg.reoutbreakteamcommunicator;

import android.content.res.Resources;

class StatusBuilder {

    String create(Resources resources, String characterName, StatusType statusType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(characterName.toUpperCase());

        String statusName = resources.getString(statusType.getName());
        stringBuilder.append(" - ")
                .append(statusName);

        return stringBuilder.toString();
    }
}
