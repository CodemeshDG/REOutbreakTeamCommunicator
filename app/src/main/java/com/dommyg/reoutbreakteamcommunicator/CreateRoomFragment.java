package com.dommyg.reoutbreakteamcommunicator;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateRoomFragment extends Fragment {

    private Button buttonCreateRoom;

    private RadioGroup radioGroupScenario;
    private boolean scenarioSelected;
    private RadioGroup radioGroupCharacter;
    private boolean playerSelected;

    private EditText editTextRoomName;
    private EditText editTextPassword;

    private int selectedPlayer;
    private int selectedScenario;

    private String roomName;
    private String password;

    public static CreateRoomFragment newInstance() {
        return new CreateRoomFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_room, container, false);

        buttonCreateRoom = v.findViewById(R.id.buttonCreateRoom);
        buttonCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextRoomName.getText().toString().equals("") || editTextPassword.getText().toString().equals("")) {
                    return;
                }
                if (!playerSelected || !scenarioSelected) {
                    return;
                }
                roomName = editTextRoomName.getText().toString();
                password = editTextPassword.getText().toString();
                Intent intent = ControlPanelActivity.newIntent(getContext(), selectedPlayer, selectedScenario, roomName, password);
                startActivity(intent);
            }
        });

        radioGroupCharacter = v.findViewById(R.id.radioGroupCharacter);
        radioGroupCharacter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectedPlayer = radioGroup.getCheckedRadioButtonId();
                playerSelected = true;
            }
        });

        radioGroupScenario = v.findViewById(R.id.radioGroupScenario);
        radioGroupScenario.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectedScenario = radioGroup.getCheckedRadioButtonId();
                scenarioSelected = true;
            }
        });

        editTextRoomName = v.findViewById(R.id.editTextSetRoomName);
        editTextPassword = v.findViewById(R.id.editTextSetPassword);

        return v;
    }

}
