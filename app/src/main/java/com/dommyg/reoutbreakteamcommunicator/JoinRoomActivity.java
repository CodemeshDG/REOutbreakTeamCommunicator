package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

/**
 * Provides the interface for joining an existing room.
 */
public class JoinRoomActivity extends SingleFragmentActivity {
    private static final String KEY_USERNAME = "username";

    @Override
    protected Fragment createFragment() {
        String username = getIntent().getStringExtra(KEY_USERNAME);
        return JoinRoomFragment.newInstance(username);
    }

    public static Intent newIntent(Context packageContext, String username) {
        Intent intent = new Intent(packageContext, JoinRoomActivity.class);
        intent.putExtra(KEY_USERNAME, username);
        return intent;
    }
}
