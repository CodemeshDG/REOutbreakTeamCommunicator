package com.dommyg.reoutbreakteamcommunicator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChangeStatusNeedFragment extends Fragment {
    private CheckBox checkBoxHealing;
    private CheckBox checkBoxWeapon;
    private CheckBox checkBoxAmmo;
    private CheckBox checkBoxKey;

    private AutoCompleteTextView textViewHealing;
    private AutoCompleteTextView textViewWeapon;
    private AutoCompleteTextView textViewAmmo;
    private AutoCompleteTextView textViewKey;
    private AutoCompleteTextView textViewLocation;

    private Button buttonCancel;
    private Button buttonSubmit;

    private String[] locations;
    private String[] itemsHealing;
    private String[] itemsWeapon;
    private String[] itemsAmmo;
    private String[] itemsKey;

    private boolean healing;
    private boolean weapon;
    private boolean ammo;
    private boolean key;

    public static ChangeStatusNeedFragment newInstance(String[] locations, String[] itemsHealing,
                                                       String[] itemsWeapon, String[] itemsAmmo,
                                                       String[] itemsKey) {
        return new ChangeStatusNeedFragment(locations, itemsHealing, itemsWeapon, itemsAmmo, itemsKey);
    }

    public ChangeStatusNeedFragment(String[] locations, String[] itemsHealing,  String[] itemsWeapon,
                                    String[] itemsAmmo, String[] itemsKey) {
        this.locations = locations;
        this.itemsHealing = itemsHealing;
        this.itemsWeapon = itemsWeapon;
        this.itemsAmmo = itemsAmmo;
        this.itemsKey = itemsKey;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_status_need, container, false);

        ArrayAdapter<String> healingAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, itemsHealing);
        textViewHealing = v.findViewById(R.id.autoCompleteTextViewHealing);
        textViewHealing.setAdapter(healingAdapter);
        textViewHealing.setThreshold(0);

        checkBoxHealing = v.findViewById(R.id.checkBoxNeedHealing);
        checkBoxHealing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                healing = b;
                checkIfShowAutoCompleteTextView(healing, textViewHealing);
            }
        });

        ArrayAdapter<String> weaponAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, itemsWeapon);
        textViewWeapon = v.findViewById(R.id.autoCompleteTextViewWeapon);
        textViewWeapon.setAdapter(weaponAdapter);
        textViewWeapon.setThreshold(0);

        checkBoxWeapon = v.findViewById(R.id.checkBoxNeedWeapon);
        checkBoxWeapon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                weapon = b;
                checkIfShowAutoCompleteTextView(weapon, textViewWeapon);
            }
        });

        ArrayAdapter<String> ammoAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, itemsAmmo);
        textViewAmmo = v.findViewById(R.id.autoCompleteTextViewAmmo);
        textViewAmmo.setAdapter(ammoAdapter);
        textViewAmmo.setThreshold(0);

        checkBoxAmmo = v.findViewById(R.id.checkBoxNeedAmmo);
        checkBoxAmmo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ammo = b;
                checkIfShowAutoCompleteTextView(ammo, textViewAmmo);
            }
        });

        ArrayAdapter<String> keyAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, itemsKey);
        textViewKey = v.findViewById(R.id.autoCompleteTextViewKey);
        textViewKey.setAdapter(keyAdapter);
        textViewKey.setThreshold(0);

        checkBoxKey = v.findViewById(R.id.checkBoxNeedKey);
        checkBoxKey.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                key = b;
                checkIfShowAutoCompleteTextView(key, textViewKey);
            }
        });

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, locations);
        textViewLocation = v.findViewById(R.id.autoCompleteTextViewLocation);
        textViewLocation.setAdapter(locationAdapter);
        textViewLocation.setThreshold(0);

        buttonSubmit = v.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonCancel = v.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return v;
    }

    private void checkIfShowAutoCompleteTextView(boolean variable, AutoCompleteTextView textView) {
        if (variable) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}
