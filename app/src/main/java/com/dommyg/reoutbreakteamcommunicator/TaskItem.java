package com.dommyg.reoutbreakteamcommunicator;

import android.widget.CheckBox;
import android.widget.TextView;

public class TaskItem {
    private CheckBox checkBoxPlan;
    private CheckBox checkBoxInProgress;
    private CheckBox checkBoxComplete;
    private TextView textViewTaskName;
    private TextView textViewTaskStatus;

    public TaskItem(CheckBox checkBoxPlan, CheckBox checkBoxInProgress, CheckBox checkBoxComplete,
                    TextView textViewTaskName, TextView textViewTaskStatus) {
        this.checkBoxPlan = checkBoxPlan;
        this.checkBoxInProgress = checkBoxInProgress;
        this.checkBoxComplete = checkBoxComplete;
        this.textViewTaskName = textViewTaskName;
        this.textViewTaskStatus = textViewTaskStatus;
    }

    CheckBox getCheckBoxPlan() {
        return checkBoxPlan;
    }

    CheckBox getCheckBoxInProgress() {
        return checkBoxInProgress;
    }

    CheckBox getCheckBoxComplete() {
        return checkBoxComplete;
    }

    TextView getTextViewTaskName() {
        return textViewTaskName;
    }

    TextView getTextViewTaskStatus() {
        return textViewTaskStatus;
    }
}
