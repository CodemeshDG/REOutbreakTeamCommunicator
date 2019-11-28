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
        holder.textViewTaskStatus.setText(currentItem.getTaskStatus());
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
}
