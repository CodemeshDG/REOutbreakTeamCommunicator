package com.dommyg.reoutbreakteamcommunicator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<TaskItem> taskItemList;

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

    public TaskAdapter(ArrayList<TaskItem> taskItemList) {
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
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskItem currentItem = taskItemList.get(position);

        holder.textViewTaskName.setText(currentItem.getTaskName());
        holder.textViewTaskStatus.setText(currentItem.getTaskStatus());
    }

    @Override
    public int getItemCount() {
        return taskItemList.size();
    }
}
