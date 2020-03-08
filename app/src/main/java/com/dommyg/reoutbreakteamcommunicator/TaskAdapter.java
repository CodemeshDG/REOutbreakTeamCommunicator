package com.dommyg.reoutbreakteamcommunicator;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class TaskAdapter extends FirestoreRecyclerAdapter<TaskItem, TaskAdapter.TaskViewHolder> {

    private Resources resources;
    private ControlPanelFragment controlPanelFragment;
    private CollectionReference tasksReference;
    private String[] characterNames;

    private final int CHECK_BOX_PLAN = 0;
    private final int CHECK_BOX_IN_PROGRESS = 1;
    private final int CHECK_BOX_COMPLETE = 2;

    // TODO: Change this so that it is generated based on a value stored in Player.
    private final String KEY_PLAYER_STATUS = "player1TaskStatus";

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox[] checkBoxes = new CheckBox[3];
        CheckBoxListener[] checkBoxListeners = new CheckBoxListener[3];
        TextView textViewTaskName;
        TextView textViewTaskStatus;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkBoxes[0] = itemView.findViewById(R.id.checkBoxPlan);
            this.checkBoxes[1] = itemView.findViewById(R.id.checkBoxInProgress);
            this.checkBoxes[2] = itemView.findViewById(R.id.checkBoxComplete);
            this.textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
            this.textViewTaskStatus = itemView.findViewById(R.id.textViewTaskStatus);
        }
    }

    TaskAdapter(@NonNull FirestoreRecyclerOptions<TaskItem> options, Resources resources,
                ControlPanelFragment controlPanelFragment, CollectionReference tasksReference,
                String[] characterNames) {
        super(options);
        this.resources = resources;
        this.controlPanelFragment = controlPanelFragment;
        this.tasksReference = tasksReference;
        this.characterNames = characterNames;
    }


    @Override
    protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position,
                                    @NonNull TaskItem model) {
        holder.textViewTaskName.setText("Test the tasks" + position);

        char charIndex = 'a';

        for (int i = 0; i < position; i++) {
            charIndex++;
        }

        String taskDocumentName = String.valueOf(
                controlPanelFragment.getCurrentTaskSetToDisplay() + 1) + charIndex;

        DocumentReference taskDocumentReference = tasksReference.document(taskDocumentName);

        int[] taskPlayerStatuses = {model.getPlayer1TaskStatus(), model.getPlayer2TaskStatus(),
                model.getPlayer3TaskStatus(), model.getPlayer4TaskStatus()};
        holder.textViewTaskStatus.setText(new TaskStatusBuilder().create(resources,
                checkTaskStatus(taskPlayerStatuses), taskPlayerStatuses, characterNames));

        if (holder.checkBoxListeners[0] != null) {
            for (CheckBoxListener checkBoxListener : holder.checkBoxListeners) {
                checkBoxListener.setIgnoreEvents(true);
            }
        }

        holder.checkBoxes[CHECK_BOX_PLAN].setChecked(
                updateCheckBoxFromPlayerTaskProgress(position, CHECK_BOX_PLAN));
        holder.checkBoxes[CHECK_BOX_IN_PROGRESS].setChecked(
                updateCheckBoxFromPlayerTaskProgress(position, CHECK_BOX_IN_PROGRESS));
        holder.checkBoxes[CHECK_BOX_COMPLETE].setChecked(
                updateCheckBoxFromPlayerTaskProgress(position, CHECK_BOX_COMPLETE));

        if (holder.checkBoxListeners[0] != null) {
            for (CheckBoxListener checkBoxListener : holder.checkBoxListeners) {
                checkBoxListener.setIgnoreEvents(false);
            }
        }

        for (int i = 0; i < holder.checkBoxes.length; i++) {
                holder.checkBoxListeners[i] =
                        new CheckBoxListener(i, position, holder.checkBoxes,
                                holder.checkBoxListeners, taskDocumentReference);
                holder.checkBoxes[i].setOnCheckedChangeListener(holder.checkBoxListeners[i]);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new TaskViewHolder(v);
    }

    private int checkTaskStatus(int[] taskPlayerStatuses) {
        int highestOrderTaskStatus = 0;
        for (int i = 0; i < 4; i++) {
            if (taskPlayerStatuses[i] > highestOrderTaskStatus) {
                highestOrderTaskStatus = taskPlayerStatuses[i];
            }
        }
        return highestOrderTaskStatus;
    }

    private boolean updateCheckBoxFromPlayerTaskProgress(int position, int checkBox) {
        return controlPanelFragment.getRoom()
                .getPlayerUser()
                .getTaskProgress()
                [controlPanelFragment.getCurrentTaskSetToDisplay()]
                [position]
                [checkBox];
    }

    private void updatePlayerTaskProgress(int position, int checkBox, boolean isChecked) {
        controlPanelFragment.getRoom()
                .getPlayerUser()
                .setTaskProgress(
                        controlPanelFragment.getCurrentTaskSetToDisplay(),
                        position,
                        checkBox,
                        isChecked);
    }

    private class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        private int checkBoxIndex;
        private int taskPosition;
        private CheckBox[] checkBoxes;
        private CheckBoxListener[] checkBoxListeners;
        private boolean ignoreEvents = false;
        private DocumentReference taskDocumentReference;

        CheckBoxListener(int checkBoxIndex, int taskPosition, CheckBox[] checkBoxes,
                         CheckBoxListener[] checkBoxListeners,
                         DocumentReference taskDocumentReference) {
            this.checkBoxIndex = checkBoxIndex;
            this.taskPosition = taskPosition;
            this.checkBoxes = checkBoxes;
            this.checkBoxListeners = checkBoxListeners;
            this.taskDocumentReference = taskDocumentReference;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!ignoreEvents) {
                if (isChecked) {
                    for (int i = 0; i < checkBoxes.length; i++) {
                        if (i != checkBoxIndex) {
                            checkBoxListeners[i].setIgnoreEvents(true);
                            checkBoxes[i].setChecked(false);
                        }
                    }

                    int updateArray = checkBoxIndex + 1;
                    Map<String, Object> updateMap = new HashMap<>();
                    updateMap.put(KEY_PLAYER_STATUS, updateArray);
                    taskDocumentReference.update(updateMap);
                } else {
                    int updateArray = 0;
                    Map<String, Object> updateMap = new HashMap<>();
                    updateMap.put(KEY_PLAYER_STATUS, updateArray);
                    taskDocumentReference.update(updateMap);
                }
                for (CheckBoxListener checkBoxListener : checkBoxListeners) {
                    checkBoxListener.setIgnoreEvents(false);
                }
            }
            updatePlayerTaskProgress(taskPosition, checkBoxIndex, isChecked);
        }

        void setIgnoreEvents(boolean ignoreEvents) {
            this.ignoreEvents = ignoreEvents;
        }
    }
}
