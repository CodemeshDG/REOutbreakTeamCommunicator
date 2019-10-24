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

public class ChangeStatusDeadFragment extends Fragment {
    private CheckBox checkBoxItem1;
    private CheckBox checkBoxItem2;
    private CheckBox checkBoxItem3;
    private CheckBox checkBoxItem4;
    private CheckBox checkBoxItem5;
    private CheckBox checkBoxItem6;
    private CheckBox checkBoxItem7;
    private CheckBox checkBoxItem8;

    private AutoCompleteTextView textViewItem1;
    private AutoCompleteTextView textViewItem2;
    private AutoCompleteTextView textViewItem3;
    private AutoCompleteTextView textViewItem4;
    private AutoCompleteTextView textViewItem5;
    private AutoCompleteTextView textViewItem6;
    private AutoCompleteTextView textViewItem7;
    private AutoCompleteTextView textViewItem8;
    private AutoCompleteTextView textViewLocation;

    private Button buttonCancel;
    private Button buttonSubmit;

    private String[] locations;
    private String[] itemsHealing;
    private String[] itemsWeapon;
    private String[] itemsAmmo;
    private String[] itemsKey;

    private boolean item1;
    private boolean item2;
    private boolean item3;
    private boolean item4;
    private boolean item5;
    private boolean item6;
    private boolean item7;
    private boolean item8;

    private boolean isYoko;

    public static ChangeStatusDeadFragment newInstance(String[] locations, String[] itemsHealing,
                                                       String[] itemsWeapon, String[] itemsAmmo,
                                                       String[] itemsKey, boolean isYoko) {
        return new ChangeStatusDeadFragment(locations, itemsHealing, itemsWeapon, itemsAmmo,
                itemsKey, isYoko);
    }

    public ChangeStatusDeadFragment(String[] locations, String[] itemsHealing,  String[] itemsWeapon,
                                    String[] itemsAmmo, String[] itemsKey, boolean isYoko) {
        this.locations = locations;
        this.itemsHealing = itemsHealing;
        this.itemsWeapon = itemsWeapon;
        this.itemsAmmo = itemsAmmo;
        this.itemsKey = itemsKey;
        this.isYoko = isYoko;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_status_dead, container, false);

        setUpCheckBoxes(v);
        setUpLocations(v);
        setUpButtons(v);

        return v;
    }

    /**
     * Finds views for the check boxes, sets up their listeners and the adapters for their
     * autoCompleteTextViews.
     */
    private void setUpCheckBoxes(View v) {
        textViewItem1 = v.findViewById(R.id.autoCompleteTextViewItem1);
        checkBoxItem1 = v.findViewById(R.id.checkBoxDeadItem1);
        checkBoxItem1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item1 = b;
                checkIfShowAutoCompleteTextView(item1, textViewItem1);
            }
        });

        textViewItem2 = v.findViewById(R.id.autoCompleteTextViewItem2);
        checkBoxItem2 = v.findViewById(R.id.checkBoxDeadItem2);
        checkBoxItem2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item2 = b;
                checkIfShowAutoCompleteTextView(item2, textViewItem2);
            }
        });

        textViewItem3 = v.findViewById(R.id.autoCompleteTextViewItem3);
        checkBoxItem3 = v.findViewById(R.id.checkBoxDeadItem3);
        checkBoxItem3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item3 = b;
                checkIfShowAutoCompleteTextView(item3, textViewItem3);
            }
        });

        textViewItem4 = v.findViewById(R.id.autoCompleteTextViewItem4);
        checkBoxItem4 = v.findViewById(R.id.checkBoxDeadItem4);
        checkBoxItem4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item4 = b;
                checkIfShowAutoCompleteTextView(item4, textViewItem4);
            }
        });

        if (isYoko) {
            textViewItem5 = v.findViewById(R.id.autoCompleteTextViewItem5);
            checkBoxItem5 = v.findViewById(R.id.checkBoxDeadItem5);
            checkBoxItem5.setVisibility(View.VISIBLE);
            checkBoxItem5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    item5 = b;
                    checkIfShowAutoCompleteTextView(item5, textViewItem5);
                }
            });

            textViewItem6 = v.findViewById(R.id.autoCompleteTextViewItem6);
            checkBoxItem6 = v.findViewById(R.id.checkBoxDeadItem6);
            checkBoxItem6.setVisibility(View.VISIBLE);
            checkBoxItem6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    item6 = b;
                    checkIfShowAutoCompleteTextView(item6, textViewItem6);
                }
            });

            textViewItem7 = v.findViewById(R.id.autoCompleteTextViewItem7);
            checkBoxItem7 = v.findViewById(R.id.checkBoxDeadItem7);
            checkBoxItem7.setVisibility(View.VISIBLE);
            checkBoxItem7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    item7 = b;
                    checkIfShowAutoCompleteTextView(item7, textViewItem7);
                }
            });

            textViewItem8 = v.findViewById(R.id.autoCompleteTextViewItem8);
            checkBoxItem8 = v.findViewById(R.id.checkBoxDeadItem8);
            checkBoxItem8.setVisibility(View.VISIBLE);
            checkBoxItem8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    item8 = b;
                    checkIfShowAutoCompleteTextView(item8, textViewItem8);
                }
            });
        }
    }

    /**
     * Finds the view for the location field and sets up the adapter for the autoCompleteTextView.
     */
    private void setUpLocations(View v) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, locations);

        textViewLocation = v.findViewById(R.id.autoCompleteTextViewLocation);
        textViewLocation.setAdapter(adapter);
        textViewLocation.setThreshold(0);
    }

    /**
     * Finds views for the buttons and sets them and their listeners.
     */
    private void setUpButtons(View v) {
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
    }

    /**
     * Controls an autoCompleteTextView's visibility based upon its associated check box's status.
     * @param variable If the check box is checked.
     * @param textView The autoCompleteTextView to be modified.
     */
    private void checkIfShowAutoCompleteTextView(boolean variable, AutoCompleteTextView textView) {
        if (variable) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}
