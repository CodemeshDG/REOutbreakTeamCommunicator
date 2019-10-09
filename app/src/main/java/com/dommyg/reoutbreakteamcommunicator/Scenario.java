package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.res.Resources;

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
    private ScenarioName scenarioName;
    private String [] locations;

    public Scenario(ScenarioName scenarioName, Resources resources) {
        this.scenarioName = scenarioName;
        loadLocations(scenarioName.getLevel(), resources);
    }

    public int getScenarioName() {
        return scenarioName.getName();
    }

    public String[] getLocations() {
        return locations;
    }

    private void loadLocations(int scenario, Resources resources) {
        switch (scenario) {
            case 0:
                locations = resources.getStringArray(R.array.locations_1_1);
                break;
            case 1:
                locations = resources.getStringArray(R.array.locations_1_2);
                break;
            case 2:
                locations = resources.getStringArray(R.array.locations_1_3);
                break;
            case 3:
                locations = resources.getStringArray(R.array.locations_1_4);
                break;
            case 4:
                locations = resources.getStringArray(R.array.locations_1_5);
                break;
        }
    }
}
