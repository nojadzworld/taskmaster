package com.dajone.taskmaster.models;

import androidx.annotation.NonNull;

public enum TaskStatus {
    NEW("New"),
    ASSIGNED("Assigned"),
    IN_PROGRESS("In Progress"),
    COMPLETE("Complete");

    private final String taskStatus;

    TaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public static TaskStatus fromString(String string) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.taskStatus.equals(string))
                return status;
        }
        return null;
    }
    @NonNull
    @Override
    public String toString() {
        return taskStatus == null ? "" : taskStatus;
    }
}
