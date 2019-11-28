package com.dommyg.reoutbreakteamcommunicator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<TaskItem> taskItemList;
    private ControlPanelFragment controlPanelFragment;

    private final int CHECK_BOX_PLAN = 0;
    private final int CHECK_BOX_IN_PROGRESS = 1;
    private final int CHECK_BOX_COMPLETE = 2;

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxPlan;
        CheckBox checkBoxInProgress;
        CheckBox checkBoxComplete;
        TextView textViewTaskName;
        TextView textViewTaskStatus;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkBoxPlan = itemView.findViewById(R.id.checkBoxPlan);
            this.checkBoxInProgress = itemView.findViewById(R.id.checkBoxInProgress);
            this.checkBoxComplete = itemView.findViewById(R.id.checkBoxComplete);
            this.textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
            this.textViewTaskStatus = itemView.findViewById(R.id.textViewTaskStatus);
        }
    }

    TaskAdapter(ControlPanelFragment controlPanelFragment, ArrayList<TaskItem> taskItemList) {
        this.controlPanelFragment = controlPanelFragment;
        this.taskItemList = taskItemList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, final int position) {
        TaskItem currentItem = taskItemList.get(position);

        holder.textViewTaskName.setText(currentItem.getTaskName());
        holder.textViewTaskStatus.setText(updateTaskStatus(position));
        holder.checkBoxPlan.setChecked(updateCheckBoxFromPlayerTaskProgress(position, CHECK_BOX_PLAN));
        holder.checkBoxInProgress.setChecked(updateCheckBoxFromPlayerTaskProgress(position, CHECK_BOX_IN_PROGRESS));
        holder.checkBoxComplete.setChecked(updateCheckBoxFromPlayerTaskProgress(position, CHECK_BOX_COMPLETE));

        holder.checkBoxPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    holder.checkBoxInProgress.setChecked(false);
                    updatePlayerTaskProgress(position, CHECK_BOX_IN_PROGRESS, false);
                    holder.checkBoxComplete.setChecked(false);
                    updatePlayerTaskProgress(position, CHECK_BOX_COMPLETE, false);
                }
                updatePlayerTaskProgress(position, CHECK_BOX_PLAN, b);
                holder.textViewTaskStatus.setText(updateTaskStatus(position));
            }
        });
        holder.checkBoxInProgress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    holder.checkBoxPlan.setChecked(false);
                    updatePlayerTaskProgress(position, CHECK_BOX_PLAN, false);
                    holder.checkBoxComplete.setChecked(false);
                    updatePlayerTaskProgress(position, CHECK_BOX_COMPLETE, false);
                }
                updatePlayerTaskProgress(position, CHECK_BOX_IN_PROGRESS, b);
                holder.textViewTaskStatus.setText(updateTaskStatus(position));
            }
        });
        holder.checkBoxComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    holder.checkBoxPlan.setChecked(false);
                    updatePlayerTaskProgress(position, CHECK_BOX_PLAN, false);
                    holder.checkBoxInProgress.setChecked(false);
                    updatePlayerTaskProgress(position, CHECK_BOX_IN_PROGRESS, false);
                }
                updatePlayerTaskProgress(position, CHECK_BOX_COMPLETE, b);
                holder.textViewTaskStatus.setText(updateTaskStatus(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskItemList.size();
    }

    private boolean updateCheckBoxFromPlayerTaskProgress(int position, int checkBox) {
        return controlPanelFragment.getRoom()
                .getPlayer1()
                .getTaskProgress()
                [controlPanelFragment.getCurrentTaskSetToDisplay()]
                [position]
                [checkBox];
    }

    private void updatePlayerTaskProgress(int position, int checkBox, boolean isChecked) {
        controlPanelFragment.getRoom()
                .getPlayer1()
                .setTaskProgress(
                        controlPanelFragment.getCurrentTaskSetToDisplay(),
                        position,
                        checkBox,
                        isChecked);
    }

    private String updateTaskStatus(int position) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean flag = false;
        boolean[] teamTaskProgress = new boolean[4];
        String[] teamNames = new String[4];

        teamNames[0] = controlPanelFragment.getResources()
                .getString(controlPanelFragment.getRoom()
                        .getPlayer1()
                        .getCharacterName());
        if (controlPanelFragment.getRoom().getPlayer2() != null) {
            teamNames[1] = controlPanelFragment.getResources()
                    .getString(controlPanelFragment.getRoom()
                            .getPlayer2()
                            .getCharacterName());
        }

        if (controlPanelFragment.getRoom().getPlayer3() != null) {
            teamNames[2] = controlPanelFragment.getResources()
                    .getString(controlPanelFragment.getRoom()
                            .getPlayer3()
                            .getCharacterName());
        }

        if (controlPanelFragment.getRoom().getPlayer4() != null) {
            teamNames[3] = controlPanelFragment.getResources()
                    .getString(controlPanelFragment.getRoom()
                            .getPlayer4()
                            .getCharacterName());
        }

        teamTaskProgress[0] = controlPanelFragment.getRoom()
                .getPlayer1()
                .getTaskProgress()
                [controlPanelFragment.getCurrentTaskSetToDisplay()]
                [position]
                [CHECK_BOX_COMPLETE];

        if (controlPanelFragment.getRoom().getPlayer2() != null) {
            teamTaskProgress[1] = controlPanelFragment.getRoom()
                    .getPlayer2()
                    .getTaskProgress()
                    [controlPanelFragment.getCurrentTaskSetToDisplay()]
                    [position]
                    [CHECK_BOX_COMPLETE];
        }

        if (controlPanelFragment.getRoom().getPlayer3() != null) {
            teamTaskProgress[2] = controlPanelFragment.getRoom()
                    .getPlayer3()
                    .getTaskProgress()
                    [controlPanelFragment.getCurrentTaskSetToDisplay()]
                    [position]
                    [CHECK_BOX_COMPLETE];
        }

        if (controlPanelFragment.getRoom().getPlayer4() != null) {
            teamTaskProgress[3] = controlPanelFragment.getRoom()
                    .getPlayer4()
                    .getTaskProgress()
                    [controlPanelFragment.getCurrentTaskSetToDisplay()]
                    [position]
                    [CHECK_BOX_COMPLETE];
        }

        for (int i = 0; i < teamTaskProgress.length; i++) {
            if (teamTaskProgress[i] && !flag) {
                stringBuilder.append(controlPanelFragment.getResources()
                        .getString(R.string.task_status_complete))
                        .append(" ")
                        .append(teamNames[i]);
                flag = true;
            } else if (teamTaskProgress[i]) {
                stringBuilder.append(" ")
                        .append(teamNames[i]);
            }
        }

        if (flag) {
            return stringBuilder.toString();
        }

        teamTaskProgress[0] = controlPanelFragment.getRoom()
                .getPlayer1()
                .getTaskProgress()
                [controlPanelFragment.getCurrentTaskSetToDisplay()]
                [position]
                [CHECK_BOX_IN_PROGRESS];

        if (controlPanelFragment.getRoom().getPlayer2() != null) {
            teamTaskProgress[1] = controlPanelFragment.getRoom()
                    .getPlayer2()
                    .getTaskProgress()
                    [controlPanelFragment.getCurrentTaskSetToDisplay()]
                    [position]
                    [CHECK_BOX_IN_PROGRESS];
        }

        if (controlPanelFragment.getRoom().getPlayer3() != null) {
            teamTaskProgress[2] = controlPanelFragment.getRoom()
                    .getPlayer3()
                    .getTaskProgress()
                    [controlPanelFragment.getCurrentTaskSetToDisplay()]
                    [position]
                    [CHECK_BOX_IN_PROGRESS];
        }

        if (controlPanelFragment.getRoom().getPlayer4() != null) {
            teamTaskProgress[3] = controlPanelFragment.getRoom()
                    .getPlayer4()
                    .getTaskProgress()
                    [controlPanelFragment.getCurrentTaskSetToDisplay()]
                    [position]
                    [CHECK_BOX_IN_PROGRESS];
        }

        for (int i = 0; i < teamTaskProgress.length; i++) {
            if (teamTaskProgress[i] && !flag) {
                stringBuilder.append(controlPanelFragment.getResources()
                        .getString(R.string.task_status_in_progress))
                        .append(" ")
                        .append(teamNames[i]);
                flag = true;
            } else if (teamTaskProgress[i]) {
                stringBuilder.append(" ")
                        .append(teamNames[i]);
            }
        }

        if (flag) {
            return stringBuilder.toString();
        }

        teamTaskProgress[0] = controlPanelFragment.getRoom()
                .getPlayer1()
                .getTaskProgress()
                [controlPanelFragment.getCurrentTaskSetToDisplay()]
                [position]
                [CHECK_BOX_PLAN];

        if (controlPanelFragment.getRoom().getPlayer2() != null) {
            teamTaskProgress[1] = controlPanelFragment.getRoom()
                    .getPlayer2()
                    .getTaskProgress()
                    [controlPanelFragment.getCurrentTaskSetToDisplay()]
                    [position]
                    [CHECK_BOX_PLAN];
        }

        if (controlPanelFragment.getRoom().getPlayer3() != null) {
            teamTaskProgress[2] = controlPanelFragment.getRoom()
                    .getPlayer3()
                    .getTaskProgress()
                    [controlPanelFragment.getCurrentTaskSetToDisplay()]
                    [position]
                    [CHECK_BOX_PLAN];
        }

        if (controlPanelFragment.getRoom().getPlayer4() != null) {
            teamTaskProgress[3] = controlPanelFragment.getRoom()
                    .getPlayer4()
                    .getTaskProgress()
                    [controlPanelFragment.getCurrentTaskSetToDisplay()]
                    [position]
                    [CHECK_BOX_PLAN];
        }

        for (int i = 0; i < teamTaskProgress.length; i++) {
            if (teamTaskProgress[i] && !flag) {
                stringBuilder.append(controlPanelFragment.getResources()
                        .getString(R.string.task_status_plan))
                        .append(" ")
                        .append(teamNames[i]);
                flag = true;
            } else if (teamTaskProgress[i]) {
                stringBuilder.append(" ")
                        .append(teamNames[i]);
            }
        }

        if (flag) {
            return stringBuilder.toString();
        }

        return stringBuilder.append(controlPanelFragment.getResources()
                .getString(R.string.task_status_none)).toString();
    }
}
