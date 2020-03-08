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

    TaskMaster() {

    }

    TaskSet[] getTaskSets() {
        return taskSets;
    }

    public int getTaskSetsSize() {
        return taskSets.length;
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

    int[] getTaskNumbers(ScenarioName scenarioName) {
        switch (scenarioName) {
            case OUTBREAK:
                return new int[]{5, 4, 2};

            case BELOW_FREEZING_POINT:
                return new int[]{3, 3, 5};

            case THE_HIVE:
                return new int[]{4, 3};

            case HELLFIRE:
                return new int[]{4, 2};

            case DECISIONS_DECISIONS:
                return new int[]{5, 5, 5};

            default:
                return new int[]{0};
        }
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

    public int getTasksSize() {
        return tasks.length;
    }
}
