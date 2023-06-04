package com.dajone.taskmaster.utils;

import com.dajone.taskmaster.models.TaskStatus;

import java.util.ArrayList;

public class TaskStatusUtility {
    public static TaskStatus taskStatusFromString(String taskString) {
        switch (taskString) {
            case "Complete":
                return TaskStatus.COMPLETE;
            case "Assigned":
                return TaskStatus.ASSIGNED;
            case "In Progress":
                return TaskStatus.IN_PROGRESS;
            default:
                return TaskStatus.NEW;
        }
    }

    public static String taskStatusToString(TaskStatus task) {
        switch (task) {
            case COMPLETE:
                return "Complete";
            case ASSIGNED:
                return "Assigned";
            case IN_PROGRESS:
                return "In Progress";
            default:
                return "New";
        }
    }

    public static ArrayList<String> getTaskStatusList() {
        ArrayList<String> list = new ArrayList<>();
        for (TaskStatus task : TaskStatus.values()) {
            list.add(taskStatusToString(task));
        }
        return list;
    }
}
