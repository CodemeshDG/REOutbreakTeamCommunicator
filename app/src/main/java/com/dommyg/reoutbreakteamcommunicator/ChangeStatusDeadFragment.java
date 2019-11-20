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

public class ChangeStatusDeadFragment extends Fragment {
    private OnDataPass dataPasser;

    private final int NUMBER_ITEM_SLOTS = 8;

    private CheckBox[] checkBoxes = new CheckBox[NUMBER_ITEM_SLOTS];
    private AutoCompleteTextView[] autoCompleteTextViews = new AutoCompleteTextView[NUMBER_ITEM_SLOTS];
    private AutoCompleteTextView textViewLocation;

    private String[] locations;
    private String[] itemsAll;

    private boolean isYoko;

    public interface OnDataPass {
        void onDataPass(boolean[] data, String[] items, String location, int resultCode);
    }

    static ChangeStatusDeadFragment newInstance(String[] locations, String[] itemsHealing,
                                                       String[] itemsWeapon, String[] itemsAmmo,
                                                       String[] itemsKey, boolean isYoko) {
        return new ChangeStatusDeadFragment(locations, itemsHealing, itemsWeapon, itemsAmmo,
                itemsKey, isYoko);
    }

    private ChangeStatusDeadFragment(String[] locations, String[] itemsHealing,  String[] itemsWeapon,
                                    String[] itemsAmmo, String[] itemsKey, boolean isYoko) {
        this.locations = locations;
        this.itemsAll = compileMasterItemList(itemsHealing, itemsWeapon, itemsAmmo, itemsKey);
        this.isYoko = isYoko;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof  OnDataPass) {
            dataPasser = (OnDataPass) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_status_dead, container, false);

        populateCheckBoxes(v);
        populateAutoCompleteTextViews(v);
        setUpCheckBoxes();
        setUpLocations(v);
        setUpButtons(v);

        return v;
    }

    private void passData(boolean[] data, String[] selectedItems, String selectedLocation) {
        dataPasser.onDataPass(data, selectedItems, selectedLocation, StatusType.DEAD.getType());
    }

    private void populateCheckBoxes(View v) {
        checkBoxes[0] = v.findViewById(R.id.checkBoxDeadItem1);
        checkBoxes[1] = v.findViewById(R.id.checkBoxDeadItem2);
        checkBoxes[2] = v.findViewById(R.id.checkBoxDeadItem3);
        checkBoxes[3] = v.findViewById(R.id.checkBoxDeadItem4);
        checkBoxes[4] = v.findViewById(R.id.checkBoxDeadItem5);
        checkBoxes[5] = v.findViewById(R.id.checkBoxDeadItem6);
        checkBoxes[6] = v.findViewById(R.id.checkBoxDeadItem7);
        checkBoxes[7] = v.findViewById(R.id.checkBoxDeadItem8);
    }

    private void populateAutoCompleteTextViews(View v) {
        autoCompleteTextViews[0] = v.findViewById(R.id.autoCompleteTextViewItem1);
        autoCompleteTextViews[1] = v.findViewById(R.id.autoCompleteTextViewItem2);
        autoCompleteTextViews[2] = v.findViewById(R.id.autoCompleteTextViewItem3);
        autoCompleteTextViews[3] = v.findViewById(R.id.autoCompleteTextViewItem4);
        autoCompleteTextViews[4] = v.findViewById(R.id.autoCompleteTextViewItem5);
        autoCompleteTextViews[5] = v.findViewById(R.id.autoCompleteTextViewItem6);
        autoCompleteTextViews[6] = v.findViewById(R.id.autoCompleteTextViewItem7);
        autoCompleteTextViews[7] = v.findViewById(R.id.autoCompleteTextViewItem8);
    }


    /**
     * Sets up the check boxes' listeners and the adapters for their autoCompleteTextViews. If the
     * player's character is Yoko, the final four check boxes are set to visible.
     */
    private void setUpCheckBoxes() {
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_dropdown_item_1line, itemsAll);

        for (int i = 0; i < NUMBER_ITEM_SLOTS; i++) {
            autoCompleteTextViews[i].setAdapter(itemAdapter);
            autoCompleteTextViews[i].setThreshold(0);

            if (i > 3 && isYoko) {
                checkBoxes[i].setVisibility(View.VISIBLE);
            }

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_dropdown_item_1line, locations);

        textViewLocation = v.findViewById(R.id.autoCompleteTextViewLocation);
        textViewLocation.setAdapter(adapter);
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
                boolean[] data = new boolean[8];
                String[] selectedItems = new String[8];
                for (int i = 0; i < NUMBER_ITEM_SLOTS; i++) {
                    data[i] = checkBoxes[i].isChecked();
                    selectedItems[i] = autoCompleteTextViews[i].getText().toString();
                }

                String selectedLocation = textViewLocation.getText().toString();
                passData(data, selectedItems, selectedLocation);
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

    private String[] compileMasterItemList(String[] itemsHealing,  String[] itemsWeapon,
                                           String[] itemsAmmo, String[] itemsKey) {
        itemsAll = new String[itemsHealing.length + itemsWeapon.length
                + itemsAmmo.length + itemsKey.length];

        int counter = 0;

        for (String s : itemsHealing) {
            itemsAll[counter] = s;
            counter++;
        }

        for (String s : itemsWeapon) {
            itemsAll[counter] = s;
            counter++;
        }

        for (String s : itemsAmmo) {
            itemsAll[counter] = s;
            counter++;
        }

        for (String s : itemsKey) {
            itemsAll[counter] = s;
            counter++;
        }

        return itemsAll;
    }

    /**
     * Controls an autoCompleteTextView's visibility based upon its associated check box's status.
     * @param isChecked If the check box is checked.
     * @param textView The autoCompleteTextView to be modified.
     */
    private void checkIfShowAutoCompleteTextView(boolean isChecked, AutoCompleteTextView textView) {
        if (isChecked) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}
