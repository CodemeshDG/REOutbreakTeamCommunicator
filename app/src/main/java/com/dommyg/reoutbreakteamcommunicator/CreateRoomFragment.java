package com.dommyg.reoutbreakteamcommunicator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateRoomFragment extends Fragment {

    private boolean scenarioSelected;
    private boolean playerSelected;

    private EditText editTextRoomName;
    private EditText editTextPassword;

    private int selectedCharacter;
    private int selectedScenario;

    private String username;
    private String roomName;
    private String password;

    public static CreateRoomFragment newInstance(String username) {
        return new CreateRoomFragment(username);
    }

    private CreateRoomFragment(String username) {
        this.username = username;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_room, container, false);

        setUpRadioGroups(v);
        setUpRoomNameAndPasswordFields(v);
        setUpButtonCreateRoom(v);

        return v;
    }

    private void setUpButtonCreateRoom(View v) {
        Button buttonCreateRoom = v.findViewById(R.id.buttonCreateRoom);
        buttonCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextRoomName.getText().toString().equals("") ||
                        editTextPassword.getText().toString().equals("")) {
                    // User did not fill out all required fields.
                    Toast.makeText(getContext(), "Fill out all fields.", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (!playerSelected || !scenarioSelected) {
                    // User did not select a scenario and/or a character.
                    Toast.makeText(getContext(), "Select a scenario and character.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                roomName = editTextRoomName.getText().toString();
                password = editTextPassword.getText().toString();
                new FirestoreRoomController(getContext()).createRoom(getResources(), password,
                        username, roomName, getCharacter(), getScenarioName());
            }
        });
    }

    private void setUpRadioGroups(View v) {
        RadioGroup radioGroupCharacter = v.findViewById(R.id.radioGroupCharacter);
        radioGroupCharacter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectedCharacter = radioGroup.getCheckedRadioButtonId();
                playerSelected = true;
            }
        });

        RadioGroup radioGroupScenario = v.findViewById(R.id.radioGroupScenario);
        radioGroupScenario.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectedScenario = radioGroup.getCheckedRadioButtonId();
                scenarioSelected = true;
            }
        });
    }

    private void setUpRoomNameAndPasswordFields(View v){
        editTextRoomName = v.findViewById(R.id.editTextSetRoomName);
        editTextPassword = v.findViewById(R.id.editTextSetPassword);
    }

    private Character getCharacter() {
        switch (selectedCharacter) {
            case R.id.radioButtonCharacter1:
                return Character.ALYSSA;
            case R.id.radioButtonCharacter2:
                return Character.CINDY;
            case R.id.radioButtonCharacter3:
                return Character.DAVID;
            case R.id.radioButtonCharacter4:
                return Character.GEORGE;
            case R.id.radioButtonCharacter5:
                return Character.JIM;
            case R.id.radioButtonCharacter6:
                return Character.KEVIN;
            case R.id.radioButtonCharacter7:
                return Character.MARK;
            case R.id.radioButtonCharacter8:
                return Character.YOKO;
            default:
                return null;
        }
    }

    private ScenarioName getScenarioName() {
        switch (selectedScenario) {
            case R.id.radioButtonScenario1:
                return ScenarioName.OUTBREAK;
            case R.id.radioButtonScenario2:
                return ScenarioName.BELOW_FREEZING_POINT;
            case R.id.radioButtonScenario3:
                return ScenarioName.THE_HIVE;
            case R.id.radioButtonScenario4:
                return ScenarioName.HELLFIRE;
            case R.id.radioButtonScenario5:
                return ScenarioName.DECISIONS_DECISIONS;
            default:
                return null;
        }
    }
}
