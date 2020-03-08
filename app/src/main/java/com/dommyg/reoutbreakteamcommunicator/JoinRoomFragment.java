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

public class JoinRoomFragment extends Fragment {
    private String username;
    private int selectedCharacter;
    private boolean playerSelected;

    public static JoinRoomFragment newInstance(String username) {
        return new JoinRoomFragment(username);
    }

    private JoinRoomFragment(String username) {
        this.username = username;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // This fragment uses the fragment_create_room layout because they use the same elements,
        // but just the create room button needs to be relabeled.
        View v = inflater.inflate(R.layout.fragment_join_room, container, false);

        Button buttonJoin = v.findViewById(R.id.buttonJoinRoom);

        buttonJoin.setText(R.string.heading_join_room);

        setUpElements(v);

        return v;
    }

    /**
     * Sets up all the elements for the join room process.
     */
    private void setUpElements(View v) {
        final EditText editTextRoomName = v.findViewById(R.id.editTextGiveRoomName);
        final EditText editTextPassword = v.findViewById(R.id.editTextGivePassword);

        RadioGroup radioGroupCharacter = v.findViewById(R.id.radioGroupCharacter);
        radioGroupCharacter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectedCharacter = radioGroup.getCheckedRadioButtonId();
                playerSelected = true;
            }
        });

        Button buttonJoinRoom = v.findViewById(R.id.buttonJoinRoom);
        buttonJoinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextRoomName.length() == 0 ||
                        editTextPassword.length() == 0) {
                    // User did not fill out all required fields.
                    Toast.makeText(getContext(), "Fill out all fields.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!playerSelected) {
                    // User did not select a character.
                    Toast.makeText(getContext(), "Select a character.", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                String roomName = editTextRoomName.getText().toString();
                String password = editTextPassword.getText().toString();
                new FirestoreRoomController(getContext()).joinRoom(getResources(),
                        getCharacter(), password, username, roomName);
            }
        });
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
}
