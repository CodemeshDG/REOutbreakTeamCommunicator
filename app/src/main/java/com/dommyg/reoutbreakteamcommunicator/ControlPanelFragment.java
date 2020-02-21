package com.dommyg.reoutbreakteamcommunicator;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ControlPanelFragment extends Fragment {

    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private RecyclerView.LayoutManager recyclerViewTasksLayoutManager;
    private ArrayList<TaskItem> recyclerViewTaskNames = new ArrayList<>();

    private StatusAdapter statusAdapter;

    private Room room;
    private Player myCharacter;

    private TextView textViewRoomName;
    private TextView textViewCharacterName;
    private TextView textViewScenarioName;
    private TextView textViewTaskSetName;
    private int currentTaskSetToDisplay = 0;

    private Button buttonStatusPanic;
    private Button buttonStatusNeed;
    private Button buttonStatusDead;
    private ImageButton buttonNextTaskSet;
    private ImageButton buttonPreviousTaskSet;

    private CollectionReference playersReference;

    static ControlPanelFragment newInstance(int selectedPlayer, int selectedScenario, String roomName,
                                            String password, Resources resources) {
        return new ControlPanelFragment(selectedPlayer, selectedScenario, roomName, password, resources);
    }

    private ControlPanelFragment(int selectedPlayer, int selectedScenario, String roomName,
                                 String password, Resources resources) {
        Scenario scenario = initializeScenario(selectedScenario, resources);
        this.room = new Room(initializeCharacter(selectedPlayer, scenario), scenario, roomName, password);
        this.myCharacter = room.getPlayer1();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.playersReference = db.collection("players");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_control_panel, container, false);

        setUpRoom(v);
        setUpStatusButtons(v);
        setUpStatusRecyclerView(v);
        setUpTaskElements(v);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        statusAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        statusAdapter.stopListening();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == StatusType.PANIC.getType()) {
            updateStatus(StatusType.PANIC);
        }
        if (resultCode == StatusType.NEED.getType()) {
            updateStatus(StatusType.NEED);
        }
        if (resultCode == StatusType.DEAD.getType()) {
            updateStatus(StatusType.DEAD);
        }
    }

    private void updateStatus(StatusType statusType) {
        myCharacter.getStatus().setStatusType(statusType);
    }

    /**
     * Creates a new Player, which will be the user's, for the room.
     * @param selectedPlayer Selection made by the user with CreateRoomFragment's character radio
     *                       group.
     */
    private Player initializeCharacter(int selectedPlayer, Scenario scenario) {
        switch (selectedPlayer) {
            case R.id.radioButtonCharacter1:
                return new Player(Character.ALYSSA, scenario.getTaskMaster());
            case R.id.radioButtonCharacter2:
                return new Player(Character.CINDY, scenario.getTaskMaster());
            case R.id.radioButtonCharacter3:
                return new Player(Character.DAVID, scenario.getTaskMaster());
            case R.id.radioButtonCharacter4:
                return new Player(Character.GEORGE, scenario.getTaskMaster());
            case R.id.radioButtonCharacter5:
                return new Player(Character.JIM, scenario.getTaskMaster());
            case R.id.radioButtonCharacter6:
                return new Player(Character.KEVIN, scenario.getTaskMaster());
            case R.id.radioButtonCharacter7:
                return new Player(Character.MARK, scenario.getTaskMaster());
            case R.id.radioButtonCharacter8:
                return new Player(Character.YOKO, scenario.getTaskMaster());
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
     * Sets up the recyclerView for the team's statuses.
     */
    private void setUpStatusRecyclerView(View v) {
        Query query = playersReference;

        FirestoreRecyclerOptions<StatusItem> options =
                new FirestoreRecyclerOptions.Builder<StatusItem>()
                .setQuery(query, StatusItem.class)
                .build();

        statusAdapter = new StatusAdapter(options, getContext());

        RecyclerView recyclerViewTeamStatus = v.findViewById(R.id.recyclerViewTeamStatus);
        recyclerViewTeamStatus.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager recyclerViewStatusesLayoutManager = new LinearLayoutManager(getContext());

        recyclerViewTeamStatus.setLayoutManager(recyclerViewStatusesLayoutManager);
        recyclerViewTeamStatus.setAdapter(statusAdapter);
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
     * Sets up all elements related to displaying the scenario's tasks, including the buttons and
     * recyclerView.
     */
    private void setUpTaskElements(View v) {
        setUpTaskSetName(v);
        setUpTaskSetButtons(v);
        setUpTaskSetRecyclerView(v);
    }

    /**
     * Finds views for the two task set buttons and sets them and their listeners.
     */
    private void setUpTaskSetButtons(View v) {
        buttonNextTaskSet = v.findViewById(R.id.buttonNextTaskSet);
        buttonNextTaskSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTaskSet(true);
            }
        });

        buttonPreviousTaskSet = v.findViewById(R.id.buttonPreviousTaskSet);
        buttonPreviousTaskSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTaskSet(false);
            }
        });
    }

    /**
     * Finds and sets the view for the task set name and updates its contents.
     */
    private void setUpTaskSetName(View v) {
        textViewTaskSetName = v.findViewById(R.id.textViewTaskSetName);
        textViewTaskSetName.setText(room.getScenario()
                .getTaskMaster()
                .getTaskSets()
                [0]
                .getName());
    }

    /**
     * Changes the currently displayed TaskSet.
     * @param isNavigatingForward True if the user is going to the next TaskSet (false if going to
     *                            previous one).
     */
    private void updateTaskSet(boolean isNavigatingForward) {
        if (isNavigatingForward) {
            if (currentTaskSetToDisplay == room.getScenario()
                    .getTaskMaster()
                    .getTaskSetsSize() - 1) {
                currentTaskSetToDisplay = 0;
            } else {
                currentTaskSetToDisplay++;
            }
        } else {
            if (currentTaskSetToDisplay == 0) {
                currentTaskSetToDisplay = room.getScenario()
                        .getTaskMaster()
                        .getTaskSetsSize() - 1;
            } else {
                currentTaskSetToDisplay--;
            }
        }

        textViewTaskSetName.setText(room.getScenario()
                .getTaskMaster()
                .getTaskSets()
                [currentTaskSetToDisplay]
                .getName());

        for (int i = recyclerViewTaskNames.size() - 1; recyclerViewTaskNames.size() != 0; i--) {
            recyclerViewTaskNames.remove(i);
        }

        addToTaskSet();
        taskAdapter.notifyDataSetChanged();
    }

    /**
     * Adds TaskItems to recyclerViewTaskNames from Room's TaskMaster based upon currentTaskSetToDisplay.
     */
    private void addToTaskSet() {
        TaskSet temp = room.getScenario()
                .getTaskMaster()
                .getTaskSets()
                [currentTaskSetToDisplay];
        for (int i = 0; i < temp.getTasksSize(); i++) {
            recyclerViewTaskNames.add(new TaskItem(temp.getTasks()[i], null));
        }
    }

    private void setUpTaskSetRecyclerView(View v) {
        addToTaskSet();

        recyclerViewTasks = v.findViewById(R.id.recyclerViewTasks);
        recyclerViewTasks.setNestedScrollingEnabled(false);
        recyclerViewTasksLayoutManager = new LinearLayoutManager(getContext());
        taskAdapter = new TaskAdapter(this, recyclerViewTaskNames);

        recyclerViewTasks.setLayoutManager(recyclerViewTasksLayoutManager);
        recyclerViewTasks.setAdapter(taskAdapter);
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

    private Intent createIntentForChangeStatusActivity(int statusType) {
        String[] locations = room.getScenario().getLocations();
        String[] itemsHealing = room.getScenario().getItemsHealing();
        String[] itemsWeapon = room.getScenario().getItemsWeapon();
        String[] itemsAmmo = room.getScenario().getItemsAmmo();
        String[] itemsKey = room.getScenario().getItemsKey();
        boolean isYoko = (myCharacter.getCharacterName() == Character.YOKO.getName());

        return ChangeStatusActivity.newIntent(getContext(), "dom", getResources().getString(myCharacter.getCharacterName()), statusType, locations,
                itemsHealing, itemsWeapon, itemsAmmo, itemsKey, isYoko);
    }

    Room getRoom() {
        return room;
    }

    int getCurrentTaskSetToDisplay() {
        return currentTaskSetToDisplay;
    }
}
