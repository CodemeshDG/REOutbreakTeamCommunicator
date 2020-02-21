package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class ChangeStatusActivity extends SingleFragmentActivity {

    private static final String EXTRA_SELECTED_STATUS = "com.dommyg.reoutbreakteamcommunicator.selected_status";
    private static final String EXTRA_LOCATIONS = "com.dommyg.reoutbreakteamcommunicator.locations";
    private static final String EXTRA_ITEMS_HEALING = "com.dommyg.reoutbreakteamcommunicator.items_healing";
    private static final String EXTRA_ITEMS_WEAPON = "com.dommyg.reoutbreakteamcommunicator.items_weapon";
    private static final String EXTRA_ITEMS_AMMO = "com.dommyg.reoutbreakteamcommunicator.items_ammo";
    private static final String EXTRA_ITEMS_KEY = "com.dommyg.reoutbreakteamcommunicator.items_key";
    private static final String EXTRA_IS_YOKO = "com.dommyg.reoutbreakteamcommunicator.is_yoko";

    private static final String EXTRA_USERNAME = "com.dommyg.reoutbreakteamcommunicator.username";
    private static final String EXTRA_CHARACTER_NAME = "com.dommyg.reoutbreakteamcommunicator.character_name";

    private String USERNAME;
    private String CHARACTER_NAME;

    @Override
    protected Fragment createFragment() {
        USERNAME = getIntent().getStringExtra(EXTRA_USERNAME);
        CHARACTER_NAME = getIntent().getStringExtra(EXTRA_CHARACTER_NAME);
        int selectedStatus = getIntent().getIntExtra(EXTRA_SELECTED_STATUS, 0);
        String[] locations = getIntent().getStringArrayExtra(EXTRA_LOCATIONS);
        String[] itemsHealing = getIntent().getStringArrayExtra(EXTRA_ITEMS_HEALING);
        String[] itemsWeapon = getIntent().getStringArrayExtra(EXTRA_ITEMS_WEAPON);
        String[] itemsAmmo = getIntent().getStringArrayExtra(EXTRA_ITEMS_AMMO);
        String[] itemsKey = getIntent().getStringArrayExtra(EXTRA_ITEMS_KEY);

        if (selectedStatus == StatusType.PANIC.getType()) {
            return ChangeStatusPanicFragment.newInstance(this, locations);
        }
        if (selectedStatus == StatusType.NEED.getType()) {
            return ChangeStatusNeedFragment.newInstance(this, locations,
                    itemsHealing, itemsWeapon, itemsAmmo, itemsKey);
        }
        if (selectedStatus == StatusType.DEAD.getType()) {
            boolean isYoko = getIntent().getBooleanExtra(EXTRA_IS_YOKO, false);
            return ChangeStatusDeadFragment.newInstance(this, locations,
                    itemsHealing, itemsWeapon, itemsAmmo, itemsKey, isYoko);
        }
        return null;
    }

    public static Intent newIntent(Context packageContext, String username, String characterName,
                                   int selectedStatus, String[] locations, String[] itemsHealing,
                                   String[] itemsWeapon, String[] itemsAmmo, String[] itemsKey,
                                   boolean isYoko) {
        Intent intent = new Intent(packageContext, ChangeStatusActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        intent.putExtra(EXTRA_CHARACTER_NAME, characterName);
        intent.putExtra(EXTRA_SELECTED_STATUS, selectedStatus);
        intent.putExtra(EXTRA_LOCATIONS, locations);
        intent.putExtra(EXTRA_ITEMS_HEALING, itemsHealing);
        intent.putExtra(EXTRA_ITEMS_WEAPON, itemsWeapon);
        intent.putExtra(EXTRA_ITEMS_AMMO, itemsAmmo);
        intent.putExtra(EXTRA_ITEMS_KEY, itemsKey);
        if (isYoko) {
            intent.putExtra(EXTRA_IS_YOKO, true);
        }
        return intent;
    }

    void updateStatus(StatusType statusType, boolean[] data, String[] selectedItems,
                              String selectedLocation) {
        String status = new StatusBuilder().create(getResources(), CHARACTER_NAME,
                statusType);
        String subStatus = new SubStatusBuilder().create(data, selectedItems,
                selectedLocation, statusType);
        new FirestoreStatusController().update(this, statusType, USERNAME, status,
                subStatus);
    }
}
