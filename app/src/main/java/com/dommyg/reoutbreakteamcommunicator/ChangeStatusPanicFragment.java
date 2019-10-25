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

public class ChangeStatusPanicFragment extends Fragment {
    private OnDataPass dataPasser;

    private CheckBox checkBoxDowned;
    private CheckBox checkBoxDanger;
    private CheckBox checkBoxViral;
    private CheckBox checkBoxTrapped;

    private AutoCompleteTextView textViewLocation;

    private Button buttonCancel;
    private Button buttonSubmit;

    private String[] locations;

    private boolean downed;
    private boolean danger;
    private boolean viral;
    private boolean trapped;

    public interface OnDataPass {
        void onDataPass(boolean[] data, String location, int resultCode);
    }

    public static ChangeStatusPanicFragment newInstance(String[] locations) {
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

        setUpCheckBoxes(v);
        setUpLocations(v);
        setUpButtons(v);

        return v;
    }

    private void passData(boolean[] data, String selectedLocation) {
        dataPasser.onDataPass(data, selectedLocation, StatusType.PANIC.getType());
    }

    /**
     * Finds views for the check boxes and sets them and their listeners.
     */
    private void setUpCheckBoxes(View v) {
        checkBoxDowned = v.findViewById(R.id.checkBoxPanicDowned);
        checkBoxDowned.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                downed = b;
            }
        });

        checkBoxDanger = v.findViewById(R.id.checkBoxPanicDanger);
        checkBoxDanger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                danger = b;
            }
        });

        checkBoxViral = v.findViewById(R.id.checkBoxPanicViralLoad);
        checkBoxViral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                viral = b;
            }
        });

        checkBoxTrapped = v.findViewById(R.id.checkBoxPanicTrapped);
        checkBoxTrapped.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                trapped = b;
            }
        });
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
                boolean[] data = {downed, danger, viral, trapped};
                String selectedLocation = textViewLocation.getText().toString();
                passData(data, selectedLocation);
            }
        });

        buttonCancel = v.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
}
