package com.dommyg.reoutbreakteamcommunicator;

enum ScenarioName {
    OUTBREAK (0, "Outbreak"),
    BELOW_FREEZING_POINT (1, "Below Freezing Point"),
    THE_HIVE (2, "The Hive"),
    HELLFIRE (3, "Hellfire"),
    DECISIONS_DECISIONS (4, "Decisions, Decisions");

    private final int level;
    private final String name;

    ScenarioName(int level, String name) {
        this.level = level;
        this.name = name;
    }
}

class Scenario {
    ScenarioName scenarioName;

    public Scenario(ScenarioName scenarioName) {
        this.scenarioName = scenarioName;
    }
}
