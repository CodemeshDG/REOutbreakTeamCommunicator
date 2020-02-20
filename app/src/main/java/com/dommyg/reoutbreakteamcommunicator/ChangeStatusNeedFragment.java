package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
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

import java.util.Objects;

public class ChangeStatusNeedFragment extends Fragment {
//    private OnDataPass dataPasser;

    private final int NUMBER_ITEM_TYPES = 4;

    private CheckBox[] checkBoxes = new CheckBox[NUMBER_ITEM_TYPES];
    private AutoCompleteTextView[] autoCompleteTextViews = new AutoCompleteTextView[NUMBER_ITEM_TYPES];
    private ArrayAdapter[] arrayAdapters = new ArrayAdapter[NUMBER_ITEM_TYPES];

    private AutoCompleteTextView textViewLocation;

    private String[][] items = new String[4][];
    private String[] locations;

    private ChangeStatusActivity changeStatusActivity;

//    public interface OnDataPass {
//        void onDataPass(boolean[] data, String[] items, String location, int resultCode);
//    }

    static ChangeStatusNeedFragment newInstance(ChangeStatusActivity changeStatusActivity,
                                                String[] locations, String[] itemsHealing,
                                                String[] itemsWeapon, String[] itemsAmmo,
                                                String[] itemsKey) {
        return new ChangeStatusNeedFragment(changeStatusActivity, locations, itemsHealing,
                itemsWeapon, itemsAmmo, itemsKey);
    }

    private ChangeStatusNeedFragment(ChangeStatusActivity changeStatusActivity, String[] locations,
                                     String[] itemsHealing,  String[] itemsWeapon,
                                     String[] itemsAmmo, String[] itemsKey) {
        this.changeStatusActivity = changeStatusActivity;
        this.locations = locations;
        this.items[0] = itemsHealing;
        this.items[1] = itemsWeapon;
        this.items[2] = itemsAmmo;
        this.items[3] = itemsKey;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnDataPass) {
//            dataPasser = (OnDataPass) context;
//        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_status_need, container, false);

        populateCheckBoxes(v);
        populateAutoCompleteTextViews(v);
        populateArrayAdapters();
        setUpCheckBoxes();
        setUpLocations(v);
        setUpButtons(v);

        return v;
    }

//    private void passData(boolean[] data, String[] selectedItems, String selectedLocation) {
//        dataPasser.onDataPass(data, selectedItems, selectedLocation, StatusType.NEED.getType());
//    }

    private void populateCheckBoxes(View v) {
        checkBoxes[0] = v.findViewById(R.id.checkBoxNeedHealing);
        checkBoxes[1] = v.findViewById(R.id.checkBoxNeedWeapon);
        checkBoxes[2] = v.findViewById(R.id.checkBoxNeedAmmo);
        checkBoxes[3] = v.findViewById(R.id.checkBoxNeedKey);
    }

    private void populateAutoCompleteTextViews(View v) {
        autoCompleteTextViews[0] = v.findViewById(R.id.autoCompleteTextViewHealing);
        autoCompleteTextViews[1] = v.findViewById(R.id.autoCompleteTextViewWeapon);
        autoCompleteTextViews[2] = v.findViewById(R.id.autoCompleteTextViewAmmo);
        autoCompleteTextViews[3] = v.findViewById(R.id.autoCompleteTextViewKey);
    }

    private void populateArrayAdapters() {
        arrayAdapters[0] = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_dropdown_item_1line,
                items[0]);
        arrayAdapters[1] = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                items[1]);
        arrayAdapters[2] = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                items[2]);
        arrayAdapters[3] = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                items[3]);
    }

    /**
     * Sets up the check boxes' listeners and the adapters for their autoCompleteTextViews.
     */
    private void setUpCheckBoxes() {
        for (int i = 0; i < NUMBER_ITEM_TYPES; i++) {
            autoCompleteTextViews[i].setAdapter(arrayAdapters[i]);
            autoCompleteTextViews[i].setThreshold(0);

            final int elementIndex = i;
            checkBoxes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    checkIfShowAutoCompleteTextView(b, autoCompleteTextViews[elementIndex]);
                }
            });
        }
    }

    /**
     * Finds the view for the location field and sets up the adapter for the autoCompleteTextView.
     */
    private void setUpLocations(View v) {
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_dropdown_item_1line, locations);

        textViewLocation = v.findViewById(R.id.autoCompleteTextViewLocation);
        textViewLocation.setAdapter(locationAdapter);
        textViewLocation.setThreshold(0);
    }

    /**
     * Finds views for the buttons and sets them and their listeners.
     */
    private void setUpButtons(View v) {
        Button buttonSubmit = v.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] data = new boolean[4];
                String[] selectedItems = new String[4];
                for (int i = 0; i < NUMBER_ITEM_TYPES; i++) {
                    data[i] = checkBoxes[i].isChecked();
                    selectedItems[i] = autoCompleteTextViews[i].getText().toString();
                }

                String selectedLocation = textViewLocation.getText().toString();
//                passData(data, selectedItems, selectedLocation);
                changeStatusActivity.updateStatus(StatusType.NEED, data, selectedItems,
                        selectedLocation);
            }
        });

        Button buttonCancel = v.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getActivity()).finish();
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
