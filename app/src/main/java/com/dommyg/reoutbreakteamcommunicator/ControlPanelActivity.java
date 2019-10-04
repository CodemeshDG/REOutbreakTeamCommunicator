package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class ControlPanelActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return ControlPanelFragment.newInstance();
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, ControlPanelActivity.class);
        return intent;
    }
}
