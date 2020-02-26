package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class CreateRoomActivity extends SingleFragmentActivity {
    private static final String KEY_USERNAME = "username";

    @Override
    protected Fragment createFragment() {
        return CreateRoomFragment.newInstance();
    }

    public static Intent newIntent(Context packageContext, String username) {
        Intent intent = new Intent(packageContext, CreateRoomActivity.class);
        intent.putExtra(KEY_USERNAME, username);
        return intent;
    }

}
