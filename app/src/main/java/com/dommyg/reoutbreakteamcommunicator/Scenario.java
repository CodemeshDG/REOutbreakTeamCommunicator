package com.dommyg.reoutbreakteamcommunicator;

import android.content.res.Resources;

/**
 * Contains a numerical level value (starting at zero) for each scenario and its name.
 */
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

/**
 * Contains information about a scenario, including locations and items.
 */
class Scenario {
    private ScenarioName scenarioName;
    private String[] locations;
    private String[] itemsHealing;
    private String[] itemsWeapon;
    private String[] itemsAmmo;
    private String[] itemsKey;
    private TaskMaster taskMaster;

    Scenario(ScenarioName scenarioName, Resources resources) {
        this.scenarioName = scenarioName;
        loadLocations(scenarioName.getLevel(), resources);
        loadItems(scenarioName.getLevel(), resources);
        taskMaster = new TaskMaster(scenarioName, resources);
    }

    int getScenarioName() {
        return scenarioName.getName();
    }

    String[] getLocations() {
        return locations;
    }

    String[] getItemsHealing() {
        return itemsHealing;
    }

    String[] getItemsWeapon() {
        return itemsWeapon;
    }

    String[] getItemsAmmo() {
        return itemsAmmo;
    }

    String[] getItemsKey() {
        return itemsKey;
    }

    TaskMaster getTaskMaster() {
        return taskMaster;
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

    private void loadItems(int scenario, Resources resources) {
        itemsHealing = resources.getStringArray(R.array.items_healing);
        itemsWeapon = resources.getStringArray(R.array.items_weapon);
        itemsAmmo = resources.getStringArray(R.array.items_ammo);

        // Unlike other items, key items are specialized to each scenario level.
        switch (scenario) {
            case 0:
                itemsKey = resources.getStringArray(R.array.items_key_1_1);
                break;
            case 1:
                itemsKey = resources.getStringArray(R.array.items_key_1_2);
                break;
            case 2:
                itemsKey = resources.getStringArray(R.array.items_key_1_3);
                break;
            case 3:
                itemsKey = resources.getStringArray(R.array.items_key_1_4);
                break;
            case 4:
                itemsKey = resources.getStringArray(R.array.items_key_1_5);
                break;
        }
    }
}
