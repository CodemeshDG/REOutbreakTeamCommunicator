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
    private RadioGroup radioGroupCharacter;

    private EditText editTextRoomName;
    private EditText editTextPassword;

    private Player player;
    private Scenario scenario;
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
//                Intent intent = ControlPanelActivity.newIntent(getContext());
                startActivity(new Intent(getContext(), ControlPanelActivity.class));
            }
        });

        return v;
    }

    private void createRoom() {
    }

}
