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

    private boolean healing;
    private boolean weapon;
    private boolean ammo;
    private boolean key;

    public static ChangeStatusNeedFragment newInstance(String[] locations) {
        return new ChangeStatusNeedFragment(locations);
    }

    public ChangeStatusNeedFragment(String[] locations) {
        this.locations = locations;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_status_need, container, false);

        textViewHealing = v.findViewById(R.id.autoCompleteTextViewHealing);
        checkBoxHealing = v.findViewById(R.id.checkBoxNeedHealing);
        checkBoxHealing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                healing = b;
                checkIfShowAutoCompleteTextView(healing, textViewHealing);
            }
        });

        textViewWeapon = v.findViewById(R.id.autoCompleteTextViewWeapon);
        checkBoxWeapon = v.findViewById(R.id.checkBoxNeedWeapon);
        checkBoxWeapon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                weapon = b;
                checkIfShowAutoCompleteTextView(weapon, textViewWeapon);
            }
        });

        textViewAmmo = v.findViewById(R.id.autoCompleteTextViewAmmo);
        checkBoxAmmo = v.findViewById(R.id.checkBoxNeedAmmo);
        checkBoxAmmo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ammo = b;
                checkIfShowAutoCompleteTextView(ammo, textViewAmmo);
            }
        });

        textViewKey = v.findViewById(R.id.autoCompleteTextViewKey);
        checkBoxKey = v.findViewById(R.id.checkBoxNeedKey);
        checkBoxKey.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                key = b;
                checkIfShowAutoCompleteTextView(key, textViewKey);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, locations);
        textViewLocation = v.findViewById(R.id.autoCompleteTextViewLocation);
        textViewLocation.setAdapter(adapter);
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
