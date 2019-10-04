package com.dommyg.reoutbreakteamcommunicator;

enum ScenarioName {
    OUTBREAK (0, R.string.scenario_1_1),
    BELOW_FREEZING_POINT (1, R.string.scenario_1_2),
    THE_HIVE (2, R.string.scenario_1_3),
    HELLFIRE (3, R.string.scenario_1_4),
    DECISIONS_DECISIONS (4, R.string.scenario_1_5);

    private final int level;
    private final int name;

    ScenarioName(int level, int name) {
        this.level = level;
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public int getName() {
        return name;
    }
}

class Scenario {
    ScenarioName scenarioName;

    public Scenario(ScenarioName scenarioName) {
        this.scenarioName = scenarioName;
    }

    public int getScenarioName() {
        return scenarioName.getName();
    }
}
