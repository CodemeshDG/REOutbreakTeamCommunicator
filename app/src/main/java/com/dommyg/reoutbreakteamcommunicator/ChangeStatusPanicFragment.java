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

public class ChangeStatusPanicFragment extends Fragment {
    private OnDataPass dataPasser;

    private final int NUMBER_PANIC_TYPES = 4;

    private CheckBox[] checkBoxes = new CheckBox[NUMBER_PANIC_TYPES];

    private AutoCompleteTextView textViewLocation;

    private String[] locations;

    public interface OnDataPass {
        void onDataPass(boolean[] data, String location, int resultCode);
    }

    static ChangeStatusPanicFragment newInstance(String[] locations) {
        return new ChangeStatusPanicFragment(locations);
    }

    private ChangeStatusPanicFragment(String[] locations) {
        this.locations = locations;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDataPass) {
            dataPasser = (OnDataPass) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_status_panic, container, false);

        populateCheckBoxes(v);
        setUpCheckBoxes();
        setUpLocations(v);
        setUpButtons(v);

        return v;
    }

    private void passData(boolean[] data, String selectedLocation) {
        dataPasser.onDataPass(data, selectedLocation, StatusType.PANIC.getType());
    }

    private void populateCheckBoxes(View v) {
        checkBoxes[0] = v.findViewById(R.id.checkBoxPanicDowned);
        checkBoxes[1] = v.findViewById(R.id.checkBoxPanicDanger);
        checkBoxes[2] = v.findViewById(R.id.checkBoxPanicViralLoad);
        checkBoxes[3] = v.findViewById(R.id.checkBoxPanicTrapped);
    }

    /**
     * Sets up the check boxes' listeners.
     */
    private void setUpCheckBoxes() {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

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
                boolean[] data = new boolean[4];
                for (int i = 0; i < data.length; i++) {
                    data[i] = checkBoxes[i].isChecked();
                }
                String selectedLocation = textViewLocation.getText().toString();
                passData(data, selectedLocation);
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
}
