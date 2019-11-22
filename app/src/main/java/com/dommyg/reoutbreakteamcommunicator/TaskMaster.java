package com.dommyg.reoutbreakteamcommunicator;

import android.content.res.Resources;

class TaskMaster {
    private TaskSet[] taskSets;

    TaskMaster(ScenarioName scenarioName, Resources resources) {
        switch (scenarioName) {
            case OUTBREAK:
                initializeScenario1Tasks(resources);
                break;

            case BELOW_FREEZING_POINT:
                initializeScenario2Tasks(resources);
                break;

            case THE_HIVE:
                initializeScenario3Tasks(resources);
                break;

            case HELLFIRE:
                initializeScenario4Tasks(resources);
                break;

            case DECISIONS_DECISIONS:
                initializeScenario5Tasks(resources);
                break;
        }
    }

    TaskSet[] getTaskSets() {
        return taskSets;
    }

    private void initializeScenario1Tasks(Resources resources) {
        taskSets = new TaskSet[3];

        taskSets[0] = new TaskSet(resources.getString(R.string.task_set_1_1_1), new String[]
                {resources.getString(R.string.task_1_1_1_1),
                resources.getString(R.string.task_1_1_1_2),
                resources.getString(R.string.task_1_1_1_3),
                resources.getString(R.string.task_1_1_1_4),
                resources.getString(R.string.task_1_1_1_5)});

        taskSets[1] = new TaskSet(resources.getString(R.string.task_set_1_1_2), new String[]
                {resources.getString(R.string.task_1_1_2_1),
                resources.getString(R.string.task_1_1_2_2),
                resources.getString(R.string.task_1_1_2_3),
                resources.getString(R.string.task_1_1_2_4)});

        taskSets[2] = new TaskSet(resources.getString(R.string.task_set_1_1_3), new String[]
                {resources.getString(R.string.task_1_1_3_1),
                resources.getString(R.string.task_1_1_3_2)});

    }

    private void initializeScenario2Tasks(Resources resources) {
        taskSets = new TaskSet[3];

        taskSets[0] = new TaskSet(resources.getString(R.string.task_set_1_2_1), new String[]
                {resources.getString(R.string.task_1_2_1_1),
                resources.getString(R.string.task_1_2_1_2),
                resources.getString(R.string.task_1_2_1_3)});

        taskSets[1] = new TaskSet(resources.getString(R.string.task_set_1_2_2), new String[]
                {resources.getString(R.string.task_1_2_2_1),
                resources.getString(R.string.task_1_2_2_2),
                resources.getString(R.string.task_1_2_2_3)});

        taskSets[2] = new TaskSet(resources.getString(R.string.task_set_1_2_3), new String[]
                {resources.getString(R.string.task_1_2_3_1),
                resources.getString(R.string.task_1_2_3_2),
                resources.getString(R.string.task_1_2_3_3),
                resources.getString(R.string.task_1_2_3_4),
                resources.getString(R.string.task_1_2_3_5)});
    }

    private void initializeScenario3Tasks(Resources resources) {
        taskSets = new TaskSet[2];

        taskSets[0] = new TaskSet(resources.getString(R.string.task_set_1_3_1), new String[]
                {resources.getString(R.string.task_1_3_1_1),
                resources.getString(R.string.task_1_3_1_2),
                resources.getString(R.string.task_1_3_1_3),
                resources.getString(R.string.task_1_3_1_4)});

        taskSets[1] = new TaskSet(resources.getString(R.string.task_set_1_3_2), new String[]
                {resources.getString(R.string.task_1_3_2_1),
                resources.getString(R.string.task_1_3_2_2),
                resources.getString(R.string.task_1_3_2_3)});
    }

    private void initializeScenario4Tasks(Resources resources) {
        taskSets = new TaskSet[2];

        taskSets[0] = new TaskSet(resources.getString(R.string.task_set_1_4_1), new String[]
                {resources.getString(R.string.task_1_4_1_1),
                resources.getString(R.string.task_1_4_1_2),
                resources.getString(R.string.task_1_4_1_3),
                resources.getString(R.string.task_1_4_1_4)});

        taskSets[1] = new TaskSet(resources.getString(R.string.task_set_1_4_2), new String[]
                {resources.getString(R.string.task_1_4_2_1),
                resources.getString(R.string.task_1_4_2_2)});
    }

    private void initializeScenario5Tasks(Resources resources) {
        taskSets = new TaskSet[3];

        taskSets[0] = new TaskSet(resources.getString(R.string.task_set_1_5_1), new String[]
                {resources.getString(R.string.task_1_5_1_1),
                resources.getString(R.string.task_1_5_1_2),
                resources.getString(R.string.task_1_5_1_3),
                resources.getString(R.string.task_1_5_1_4),
                resources.getString(R.string.task_1_5_1_5)});

        taskSets[1] = new TaskSet(resources.getString(R.string.task_set_1_5_2), new String[]
                {resources.getString(R.string.task_1_5_2_1),
                resources.getString(R.string.task_1_5_2_2),
                resources.getString(R.string.task_1_5_2_3),
                resources.getString(R.string.task_1_5_2_4),
                resources.getString(R.string.task_1_5_2_5)});

        taskSets[2] = new TaskSet(resources.getString(R.string.task_set_1_5_3), new String[]
                {resources.getString(R.string.task_1_5_3_1),
                resources.getString(R.string.task_1_5_3_2),
                resources.getString(R.string.task_1_5_3_3),
                resources.getString(R.string.task_1_5_3_4),
                resources.getString(R.string.task_1_5_3_5)});
    }
}

class TaskSet {
    private String name;
    private String[] tasks;

    TaskSet(String name, String[] tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public String[] getTasks() {
        return tasks;
    }
}
