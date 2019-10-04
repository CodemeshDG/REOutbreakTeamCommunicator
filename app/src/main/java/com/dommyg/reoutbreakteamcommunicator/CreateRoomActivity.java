package com.dommyg.reoutbreakteamcommunicator;

import androidx.fragment.app.Fragment;

public class CreateRoomActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return CreateRoomFragment.newInstance();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
