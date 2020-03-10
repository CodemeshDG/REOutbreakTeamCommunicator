package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import androidx.fragment.app.Fragment;

public class ControlPanelActivity extends SingleFragmentActivity {
    private static final String EXTRA_SELECTED_CHARACTER = "com.dommyg.reoutbreakteamcommunicator.selected_player";
    private static final String EXTRA_SELECTED_SCENARIO = "com.dommyg.reoutbreakteamcommunicator.selected_scenario";
    private static final String EXTRA_ROOM_NAME = "com.dommyg.reoutbreakteamcommunicator.room_name";
    private static final String EXTRA_USERNAME = "com.dommyg.reoutbreakteamcommunicator.username";
    private static final String EXTRA_PLAYER_NUMBER = "com.dommyg.reoutbreakteamcommunicator.player_number";

    @Override
    protected Fragment createFragment() {
        Character selectedCharacter = (Character) getIntent()
                .getSerializableExtra(EXTRA_SELECTED_CHARACTER);
        ScenarioName selectedScenario = (ScenarioName) getIntent()
                .getSerializableExtra(EXTRA_SELECTED_SCENARIO);
        String roomName = getIntent().getStringExtra(EXTRA_ROOM_NAME);
        String username = getIntent().getStringExtra(EXTRA_USERNAME);
        int playerNumber = getIntent().getIntExtra(EXTRA_PLAYER_NUMBER, 4);

        return ControlPanelFragment.newInstance(initializeRoom(selectedCharacter, selectedScenario,
                roomName, username, playerNumber));
    }

    public static Intent newIntent(Context packageContext, Character selectedPlayer,
                                   ScenarioName selectedScenario, String roomName,
                                   String username, int playerNumber) {
        Intent intent = new Intent(packageContext, ControlPanelActivity.class);
        intent.putExtra(EXTRA_SELECTED_CHARACTER, selectedPlayer);
        intent.putExtra(EXTRA_SELECTED_SCENARIO, selectedScenario);
        intent.putExtra(EXTRA_ROOM_NAME, roomName);
        intent.putExtra(EXTRA_USERNAME, username);
        intent.putExtra(EXTRA_PLAYER_NUMBER, playerNumber);
        return intent;
    }

    private Room initializeRoom(Character selectedCharacter, ScenarioName selectedScenario,
                                String roomName, String username, int playerNumber) {
        Scenario scenario = initializeScenario(selectedScenario, getResources());
        Player player = initializePlayer(playerNumber, selectedCharacter, scenario);
        return new Room(player, scenario, roomName, username);
    }

    /**
     * Creates a new Player, which will be the user's, for the room.
     * @param selectedCharacter Selection made by the user with CreateRoomFragment's character radio
     *                       group.
     */
    private Player initializePlayer(int playerNumber, Character selectedCharacter, Scenario scenario) {
        return new Player(playerNumber, selectedCharacter, scenario.getTaskMaster());
    }

    /**
     * Creates a new Scenario for the room.
     * @param selectedScenario Selection made by the user with CreateRoomFragment's scenario radio
     *                         group.
     */
    private Scenario initializeScenario(ScenarioName selectedScenario, Resources resources) {
        return new Scenario(selectedScenario, resources);
    }
}
