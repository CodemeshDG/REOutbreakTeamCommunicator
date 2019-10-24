package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class ChangeStatusActivity extends SingleFragmentActivity implements ChangeStatusPanicFragment.OnDataPass {
    private static final String EXTRA_SELECTED_STATUS = "com.dommyg.reoutbreakteamcommunicator.selected_status";
    private static final String EXTRA_LOCATIONS = "com.dommyg.reoutbreakteamcommunicator.locations";
    private static final String EXTRA_ITEMS_HEALING = "com.dommyg.reoutbreakteamcommunicator.items_healing";
    private static final String EXTRA_ITEMS_WEAPON = "com.dommyg.reoutbreakteamcommunicator.items_weapon";
    private static final String EXTRA_ITEMS_AMMO = "com.dommyg.reoutbreakteamcommunicator.items_ammo";
    private static final String EXTRA_ITEMS_KEY = "com.dommyg.reoutbreakteamcommunicator.items_key";
    private static final String EXTRA_IS_YOKO = "com.dommyg.reoutbreakteamcommunicator.is_yoko";

    static final String EXTRA_PANIC = "com.dommyg.reoutbreakteamcommunicator.panic";

    @Override
    protected Fragment createFragment() {
        int selectedStatus = getIntent().getIntExtra(EXTRA_SELECTED_STATUS, 0);
        String[] locations = getIntent().getStringArrayExtra(EXTRA_LOCATIONS);
        String[] itemsHealing = getIntent().getStringArrayExtra(EXTRA_ITEMS_HEALING);
        String[] itemsWeapon = getIntent().getStringArrayExtra(EXTRA_ITEMS_WEAPON);
        String[] itemsAmmo = getIntent().getStringArrayExtra(EXTRA_ITEMS_AMMO);
        String[] itemsKey = getIntent().getStringArrayExtra(EXTRA_ITEMS_KEY);

        if (selectedStatus == StatusType.PANIC.getType()) {
            return ChangeStatusPanicFragment.newInstance(locations);
        }
        if (selectedStatus == StatusType.NEED.getType()) {
            return ChangeStatusNeedFragment.newInstance(locations, itemsHealing, itemsWeapon,
                    itemsAmmo, itemsKey);
        }
        if (selectedStatus == StatusType.DEAD.getType()) {
            boolean isYoko = getIntent().getBooleanExtra(EXTRA_IS_YOKO, false);
            return ChangeStatusDeadFragment.newInstance(locations, itemsHealing, itemsWeapon,
                    itemsAmmo, itemsKey, isYoko);
        }
        return null;
    }

    public static Intent newIntent(Context packageContext, int selectedStatus, String[] locations,
                                   String[] itemsHealing, String[] itemsWeapon, String[] itemsAmmo,
                                   String[] itemsKey, boolean isYoko) {
        Intent intent = new Intent(packageContext, ChangeStatusActivity.class);
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

    @Override
    public void onDataPass(boolean data) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PANIC, data);
        setResult(1, intent);
        finish();
    }
}
