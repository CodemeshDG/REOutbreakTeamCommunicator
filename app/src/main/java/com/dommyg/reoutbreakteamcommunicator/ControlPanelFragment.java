package com.dommyg.reoutbreakteamcommunicator;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
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
    private TextView textViewPlayer3SubStatus;
    private ImageView imageViewHeadshotPlayer1;
    private ImageView imageViewHeadshotPlayer2;
    private ImageView imageViewHeadshotPlayer3;

    private Button buttonStatusPanic;
    private Button buttonStatusNeed;
    private Button buttonStatusDead;

    static ControlPanelFragment newInstance(int selectedPlayer, int selectedScenario, String roomName,
                                            String password, Resources resources) {
        return new ControlPanelFragment(selectedPlayer, selectedScenario, roomName, password, resources);
    }

    private ControlPanelFragment(int selectedPlayer, int selectedScenario, String roomName,
                                 String password, Resources resources) {
        this.room = new Room(initializeCharacter(selectedPlayer), initializeScenario(selectedScenario,
                resources), roomName, password);
        this.myCharacter = room.getPlayer1();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_control_panel, container, false);

        setUpRoom(v);
        setUpTeammates(v);
        setUpStatusButtons(v);

        // TODO: Remove in final version.
        setUpTestingCharacter(v);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == StatusType.PANIC.getType()) {
            processResult(data, StatusType.PANIC);
        }
        if (resultCode == StatusType.NEED.getType()) {
            processResult(data, StatusType.NEED);
        }
        if (resultCode == StatusType.DEAD.getType()) {
            processResult(data, StatusType.DEAD);
        }
    }

    private void processResult(Intent data, StatusType statusType) {
        boolean[] checkBoxData = data.getBooleanArrayExtra(ChangeStatusActivity.EXTRA_SELECTED_CHECKBOXES);
        String[] items = null;
        if (!statusType.equals(StatusType.PANIC)) {
            items = data.getStringArrayExtra(ChangeStatusActivity.EXTRA_SELECTED_ITEMS);
        }
        String location = data.getStringExtra(ChangeStatusActivity.EXTRA_SELECTED_LOCATION);
        updateStatus(statusType);
        updateSubStatus(checkBoxData, items, location, statusType);
    }

    private void updateStatus(StatusType statusType) {
        myCharacter.getStatus().setStatusType(statusType);

        // TODO: For testing purposes. Remove when complete.
        String updateStatus = getString(R.string.test_status);
        String characterName = getString(myCharacter.getCharacterName()).toUpperCase();
        String status = getString(myCharacter.getStatus().getStatusType());
        textViewPlayer3Status.setText(String.format(updateStatus, characterName, status));
    }

    private void updateSubStatus(boolean[] data, String[] items, String location, StatusType statusType) {
        StringBuilder stringBuilder = new StringBuilder();
        switch (statusType) {
            case PANIC:
                    if (data[0]) {
                        stringBuilder.append(" downed");
                    }
                    if (data[1]) {
                        stringBuilder.append(" in danger status");
                    }
                    if (data[2]) {
                        stringBuilderCheckComma(stringBuilder);
                        stringBuilder.append(" having a high viral load");
                    }
                    if (data[3]) {
                        stringBuilderCheckComma(stringBuilder);
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append("and");
                        }
                        stringBuilder.append(" trapped");
                    }

                    stringBuilderLocation(stringBuilder, location);

                    if (stringBuilder.toString().length() > 0) {
                        stringBuilder.insert(0, "I'm");
                        stringBuilder.append("!");
                        textViewPlayer3SubStatus.setText(stringBuilder.toString().toUpperCase());
                    } else {
                        textViewPlayer3SubStatus.setText("");
                    }
                    break;

            case NEED:
                boolean requestItem = false;

                for (int i = 0; data.length > i; i++) {
                    if (data[i]) {
                        requestItem = true;
                        if (items[i].length() > 0) {
                            stringBuilderCheckComma(stringBuilder);
                            stringBuilder.append(items[i]);
                        } else {
                            stringBuilderCheckComma(stringBuilder);
                            switch (i) {
                                case 0:
                                    stringBuilder.append("a healing item");
                                    break;

                                case 1:
                                    stringBuilder.append("a weapon");
                                    break;

                                case 2:
                                    stringBuilder.append("ammo");
                                    break;

                                case 3:
                                    stringBuilder.append("a key item");
                                    break;
                            }
                        }
                    }
                }

                stringBuilderLocation(stringBuilder, location);

                if (stringBuilder.toString().length() > 0) {
                    if (requestItem) {
                        stringBuilder.insert(0, "I need ");
                    } else {
                        stringBuilder.insert(0, "I'm");
                    }
                    stringBuilder.append("!");
                    textViewPlayer3SubStatus.setText(stringBuilder.toString().toUpperCase());
                } else {
                    textViewPlayer3SubStatus.setText("");
                }
                break;

            case DEAD:
                boolean hadItem = false;
                int undeclaredItems = 0;

                for (int i = 0; data.length > i; i++) {
                    if (data[i]) {
                        hadItem = true;
                        if (items[i].length() > 0) {
                            stringBuilderCheckComma(stringBuilder);
                            stringBuilder.append(items[i]);
                        } else {
                            undeclaredItems++;
                        }
                    }
                }

                if (stringBuilder.toString().length() > 0 && undeclaredItems > 0) {
                    stringBuilder.append(", and ").append(undeclaredItems).append(" other items");
                } else {
                    stringBuilder.append(undeclaredItems).append(" items");
                }

                stringBuilderLocation(stringBuilder, location);

                if (stringBuilder.toString().length() > 0) {
                    if (hadItem) {
                        stringBuilder.insert(0, "I was carrying ");
                    } else {
                        stringBuilder.insert(0, "I'm");
                    }
                    stringBuilder.append("!");
                    textViewPlayer3SubStatus.setText(stringBuilder.toString().toUpperCase());
                } else {
                    textViewPlayer3SubStatus.setText("");
                }
        }
    }

    private void stringBuilderCheckComma(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 0) {
            stringBuilder.append(", ");
        }
    }

    private void stringBuilderLocation(StringBuilder stringBuilder, String location) {
        if (location.length() > 0) {
            stringBuilder.append(" @ ").append(location);
        }
    }

    /**
     * Creates a new Player, which will be the user's, for the room.
     * @param selectedPlayer Selection made by the user with CreateRoomFragment's character radio
     *                       group.
     */
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

    /**
     * Creates a new Scenario for the room.
     * @param selectedScenario Selection made by the user with CreateRoomFragment's scenario radio
     *                         group.
     */
    private Scenario initializeScenario(int selectedScenario, Resources resources) {
        switch (selectedScenario) {
            case R.id.radioButtonScenario1:
                return new Scenario(ScenarioName.OUTBREAK, resources);
            case R.id.radioButtonScenario2:
                return new Scenario(ScenarioName.BELOW_FREEZING_POINT, resources);
            case R.id.radioButtonScenario3:
                return new Scenario(ScenarioName.THE_HIVE, resources);
            case R.id.radioButtonScenario4:
                return new Scenario(ScenarioName.HELLFIRE, resources);
            case R.id.radioButtonScenario5:
                return new Scenario(ScenarioName.DECISIONS_DECISIONS, resources);
            default:
                return null;
        }
    }

    /**
     * Finds views related to the three status buttons and sets them and their listeners.
     */
    private void setUpStatusButtons(View v) {
        buttonStatusPanic = v.findViewById(R.id.buttonStatusPanic);
        buttonStatusPanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int statusType = StatusType.PANIC.getType();
                Intent intent = createIntentForChangeStatusActivity(statusType);

                startActivityForResult(intent, statusType);
            }
        });

        buttonStatusNeed = v.findViewById(R.id.buttonStatusNeed);
        buttonStatusNeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int statusType = StatusType.NEED.getType();
                Intent intent = createIntentForChangeStatusActivity(statusType);

                startActivityForResult(intent, statusType);
            }
        });

        buttonStatusDead = v.findViewById(R.id.buttonStatusDead);
        buttonStatusDead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int statusType = StatusType.DEAD.getType();
                Intent intent = createIntentForChangeStatusActivity(statusType);

                startActivityForResult(intent, statusType);
            }
        });
    }

    /**
     * Finds views related to room name, character name, and scenario name and sets them.
     */
    private void setUpRoom(View v) {
        textViewRoomName = v.findViewById(R.id.textViewRoomName);
        textViewRoomName.setText("ROOM: " + room.getName() + " | PASSWORD: " + room.getPassword());

        textViewCharacterName = v.findViewById(R.id.textViewPlayerCharacterName);
        textViewCharacterName.setText(myCharacter.getCharacterName());

        textViewScenarioName = v.findViewById(R.id.textViewScenarioName);
        textViewScenarioName.setText(room.getScenario().getScenarioName());
    }

    /**
     * Finds views related to teammates' info displayed in the Team Status section and sets them.
     */
    private void setUpTeammates(View v) {
        imageViewHeadshotPlayer1 = v.findViewById(R.id.imageViewHeadshotPlayer1);
        imageViewHeadshotPlayer2 = v.findViewById(R.id.imageViewHeadshotPlayer2);
        imageViewHeadshotPlayer3 = v.findViewById(R.id.imageViewHeadshotPlayer3);

        AssetManager assetManager = getContext().getAssets();
        try {
            // TODO: These are for testing purposes and must be changed later.
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

    /**
     * Used for testing. Displays user's character and status in the third teammate's spot on the
     * Team Status section. Not for use in final version.
     */
    private void setUpTestingCharacter(View v) {
        textViewPlayer3Status = v.findViewById(R.id.textViewPlayer3Status);
        textViewPlayer3SubStatus = v.findViewById(R.id.textViewPlayer3SubStatus);
        String name = getString(myCharacter.getCharacterName()).toUpperCase();
        textViewPlayer3Status.setText(name);
    }

    private Intent createIntentForChangeStatusActivity(int statusType) {
        String[] locations = room.getScenario().getLocations();
        String[] itemsHealing = room.getScenario().getItemsHealing();
        String[] itemsWeapon = room.getScenario().getItemsWeapon();
        String[] itemsAmmo = room.getScenario().getItemsAmmo();
        String[] itemsKey = room.getScenario().getItemsKey();
        boolean isYoko = (myCharacter.getCharacterName() == Character.YOKO.getName());

        return ChangeStatusActivity.newIntent(getContext(), statusType, locations,
                itemsHealing, itemsWeapon, itemsAmmo, itemsKey, isYoko);
    }
}
