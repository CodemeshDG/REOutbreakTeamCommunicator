package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class ChangeStatusActivity extends SingleFragmentActivity {
    private static final String EXTRA_SELECTED_STATUS = "com.dommyg.reoutbreakteamcommunicator.selected_status";

    @Override
    protected Fragment createFragment() {
        int selectedStatus = getIntent().getIntExtra(EXTRA_SELECTED_STATUS, 0);
        if (selectedStatus == StatusType.PANIC.getType()) {
            return ChangeStatusPanicFragment.newInstance();
        }
        if (selectedStatus == StatusType.NEED.getType()) {
            return ChangeStatusNeedFragment.newInstance();
        }
        if (selectedStatus == StatusType.DEAD.getType()) {
            return ChangeStatusDeadFragment.newInstance();
        }
        return null;
    }

    public static Intent newIntent(Context packageContext, int selectedStatus) {
        Intent intent = new Intent(packageContext, ChangeStatusActivity.class);
        intent.putExtra(EXTRA_SELECTED_STATUS, selectedStatus);
        return intent;
    }

}
