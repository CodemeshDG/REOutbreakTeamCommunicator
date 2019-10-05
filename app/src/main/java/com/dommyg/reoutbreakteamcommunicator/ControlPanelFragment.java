package com.dommyg.reoutbreakteamcommunicator;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;

public class ControlPanelFragment extends Fragment {
    private static final String TAG = "ControlPanelFragment";

    private Room room;
    private Player myCharacter;

    private TextView textViewRoomName;
    private TextView textViewCharacterName;
    private TextView textViewScenarioName;

    private TextView textViewPlayer3Status;
    private ImageView imageViewHeadshotPlayer1;
    private ImageView imageViewHeadshotPlayer2;
    private ImageView imageViewHeadshotPlayer3;

    private Button buttonStatusPanic;
    private Button buttonStatusNeed;
    private Button buttonStatusDead;

    public static ControlPanelFragment newInstance(int selectedPlayer, int selectedScenario, String roomName, String password) {
        return new ControlPanelFragment(selectedPlayer, selectedScenario, roomName, password);
    }

    private ControlPanelFragment(int selectedPlayer, int selectedScenario, String roomName, String password) {
        this.room = new Room(initializeCharacter(selectedPlayer), initializeScenario(selectedScenario), roomName, password);
        this.myCharacter = room.getPlayer1();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_control_panel, container, false);

        textViewRoomName = v.findViewById(R.id.textViewRoomName);
        textViewCharacterName = v.findViewById(R.id.textViewPlayerCharacterName);
        textViewScenarioName = v.findViewById(R.id.textViewScenarioName);

        buttonStatusPanic = v.findViewById(R.id.buttonStatusPanic);
        buttonStatusPanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCharacter.getStatus().setStatusType(StatusType.PANIC);
                String updateStatus = getString(R.string.test_status);
                String characterName = getString(myCharacter.getCharacterName()).toUpperCase();
                String status = getString(myCharacter.getStatus().getStatusType());
                textViewPlayer3Status.setText(String.format(updateStatus, characterName, status));
            }
        });

        buttonStatusNeed = v.findViewById(R.id.buttonStatusNeed);
        buttonStatusNeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCharacter.getStatus().setStatusType(StatusType.NEED);
                String updateStatus = getString(R.string.test_status);
                String characterName = getString(myCharacter.getCharacterName()).toUpperCase();
                String status = getString(myCharacter.getStatus().getStatusType());
                textViewPlayer3Status.setText(String.format(updateStatus, characterName, status));
            }
        });

        buttonStatusDead = v.findViewById(R.id.buttonStatusDead);
        buttonStatusDead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCharacter.getStatus().setStatusType(StatusType.DEAD);
                String updateStatus = getString(R.string.test_status);
                String characterName = getString(myCharacter.getCharacterName()).toUpperCase();
                String status = getString(myCharacter.getStatus().getStatusType());
                textViewPlayer3Status.setText(String.format(updateStatus, characterName, status));
            }
        });

        textViewPlayer3Status = v.findViewById(R.id.textViewPlayer3Status);
        imageViewHeadshotPlayer1 = v.findViewById(R.id.imageViewHeadshotPlayer1);
        imageViewHeadshotPlayer2 = v.findViewById(R.id.imageViewHeadshotPlayer2);
        imageViewHeadshotPlayer3 = v.findViewById(R.id.imageViewHeadshotPlayer3);

        setUpViews();

        return v;
    }

    private Player initializeCharacter(int selectedPlayer) {
        switch (selectedPlayer) {
            case R.id.radioButtonCharacter1:
                return new Player(Character.ALYSSA);
            case R.id.radioButtonCharacter2:
                return new Player(Character.CINDY);
            case R.id.radioButtonCharacter3:
                return new Player(Character.DAVID);
            case R.id.radioButtonCharacter4:
                return new Player(Character.GEORGE);
            case R.id.radioButtonCharacter5:
                return new Player(Character.JIM);
            case R.id.radioButtonCharacter6:
                return new Player(Character.KEVIN);
            case R.id.radioButtonCharacter7:
                return new Player(Character.MARK);
            case R.id.radioButtonCharacter8:
                return new Player(Character.YOKO);
            default:
                return null;
        }
    }

    private Scenario initializeScenario(int selectedScenario) {
        switch (selectedScenario) {
            case R.id.radioButtonScenario1:
                return new Scenario(ScenarioName.OUTBREAK);
            case R.id.radioButtonScenario2:
                return new Scenario(ScenarioName.BELOW_FREEZING_POINT);
            case R.id.radioButtonScenario3:
                return new Scenario(ScenarioName.THE_HIVE);
            case R.id.radioButtonScenario4:
                return new Scenario(ScenarioName.HELLFIRE);
            case R.id.radioButtonScenario5:
                return new Scenario(ScenarioName.DECISIONS_DECISIONS);
            default:
                return null;
        }
    }

    private void setUpViews() {
        textViewRoomName.setText("ROOM: " + room.getName() + " | PASSWORD: " + room.getPassword());
        textViewCharacterName.setText(myCharacter.getCharacterName());
        textViewScenarioName.setText(room.getScenario().getScenarioName());
        AssetManager assetManager = getContext().getAssets();
        try {
            InputStream inputStream = assetManager.open(myCharacter.getHeadshot());
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageViewHeadshotPlayer3.setImageBitmap(bitmap);
            inputStream = assetManager.open(Character.CINDY.getHeadshotPath());
            bitmap = BitmapFactory.decodeStream(inputStream);
            imageViewHeadshotPlayer1.setImageBitmap(bitmap);
            inputStream = assetManager.open(Character.JIM.getHeadshotPath());
            bitmap = BitmapFactory.decodeStream(inputStream);
            imageViewHeadshotPlayer2.setImageBitmap(bitmap);

        } catch (IOException e) {
            Log.e(TAG, "setUpViews: ", e);
        }
    }
}
