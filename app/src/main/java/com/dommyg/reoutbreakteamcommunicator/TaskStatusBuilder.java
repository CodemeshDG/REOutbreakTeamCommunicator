package com.dommyg.reoutbreakteamcommunicator;

import android.content.res.Resources;

class TaskStatusBuilder {

    private final int TASK_NONE = 0;
    private final int TASK_PLAN = 1;
    private final int TASK_IN_PROGRESS = 2;
    private final int TASK_COMPLETE = 3;

    String create(Resources resources, int highestOrderTaskStatus, int[] taskPlayerStatuses,
                  String[] characterNames) {
        StringBuilder stringBuilder = new StringBuilder();
        switch (highestOrderTaskStatus) {
            case TASK_COMPLETE:
                    return stringBuilder.append(resources.getString(R.string.task_status_complete))
                            .append(addCharacterNames(highestOrderTaskStatus, taskPlayerStatuses,
                                    characterNames))
                            .toString();

            case TASK_IN_PROGRESS:
                return stringBuilder.append(resources.getString(R.string.task_status_in_progress))
                        .append(addCharacterNames(highestOrderTaskStatus, taskPlayerStatuses,
                                characterNames))
                        .toString();

            case TASK_PLAN:
                return stringBuilder.append(resources.getString(R.string.task_status_plan))
                        .append(addCharacterNames(highestOrderTaskStatus, taskPlayerStatuses,
                                characterNames))
                        .toString();

            case TASK_NONE:
                return stringBuilder.append(resources.getString(R.string.task_status_none))
                        .toString();

            default:
                return "";
        }
    }

    private String addCharacterNames(int taskStatusToReport, int[] taskPlayerStatuses,
                                     String[] characterNames) {
        boolean comma = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (taskPlayerStatuses[i] == taskStatusToReport) {
                if (comma) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(characterNames[i]);
                comma = true;
            }
        }
        return stringBuilder.toString();
    }
}
