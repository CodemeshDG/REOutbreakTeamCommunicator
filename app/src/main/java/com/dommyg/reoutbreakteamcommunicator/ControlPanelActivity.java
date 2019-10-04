package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class ControlPanelActivity extends SingleFragmentActivity {
    private static final String EXTRA_SELECTED_PLAYER = "com.dommyg.reoutbreakteamcommunicator.selected_player";
    private static final String EXTRA_SELECTED_SCENARIO = "com.dommyg.reoutbreakteamcommunicator.selected_scenario";
    private static final String EXTRA_ROOM_NAME = "com.dommyg.reoutbreakteamcommunicator.room_name";
    private static final String EXTRA_PASSWORD = "com.dommyg.reoutbreakteamcommunicator.password";

    @Override
    protected Fragment createFragment() {
        int selectedPlayer = getIntent().getIntExtra(EXTRA_SELECTED_PLAYER,0);
        int selectedScenario = getIntent().getIntExtra(EXTRA_SELECTED_SCENARIO, 0);
        String roomName = getIntent().getStringExtra(EXTRA_ROOM_NAME);
        String password = getIntent().getStringExtra(EXTRA_PASSWORD);
        return ControlPanelFragment.newInstance(selectedPlayer, selectedScenario, roomName, password);
    }

    public static Intent newIntent(Context packageContext, int selectedPlayer, int selectedScenario, String roomName, String password) {
        Intent intent = new Intent(packageContext, ControlPanelActivity.class);
        intent.putExtra(EXTRA_SELECTED_PLAYER, selectedPlayer);
        intent.putExtra(EXTRA_SELECTED_SCENARIO, selectedScenario);
        intent.putExtra(EXTRA_ROOM_NAME, roomName);
        intent.putExtra(EXTRA_PASSWORD, password);
        return intent;
    }
}
