package com.dommyg.reoutbreakteamcommunicator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChangeStatusPanicFragment extends Fragment {
    private CheckBox checkBoxDowned;
    private CheckBox checkBoxDanger;
    private CheckBox checkBoxViral;
    private CheckBox checkBoxTrapped;

    private Button buttonCancel;
    private Button buttonSubmit;

    private boolean downed;
    private boolean danger;
    private boolean viral;
    private boolean trapped;


    public static ChangeStatusPanicFragment newInstance() {
        return new ChangeStatusPanicFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_status_panic, container, false);

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
}
