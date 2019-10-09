package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class ChangeStatusActivity extends SingleFragmentActivity {
    private static final String EXTRA_SELECTED_STATUS = "com.dommyg.reoutbreakteamcommunicator.selected_status";
    private static final String EXTRA_LOCATIONS = "com.dommyg.reoutbreakteamcommunicator.locations";

    @Override
    protected Fragment createFragment() {
        int selectedStatus = getIntent().getIntExtra(EXTRA_SELECTED_STATUS, 0);
        String[] locations = getIntent().getStringArrayExtra(EXTRA_LOCATIONS);
        if (selectedStatus == StatusType.PANIC.getType()) {
            return ChangeStatusPanicFragment.newInstance(locations);
        }
        if (selectedStatus == StatusType.NEED.getType()) {
            return ChangeStatusNeedFragment.newInstance();
        }
        if (selectedStatus == StatusType.DEAD.getType()) {
            return ChangeStatusDeadFragment.newInstance();
        }
        return null;
    }

    public static Intent newIntent(Context packageContext, int selectedStatus, String[] locations) {
        Intent intent = new Intent(packageContext, ChangeStatusActivity.class);
        intent.putExtra(EXTRA_SELECTED_STATUS, selectedStatus);
        intent.putExtra(EXTRA_LOCATIONS, locations);
        return intent;
    }

}
