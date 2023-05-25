package com.dajone.taskmaster.models;

public class Task {

     String title;
     String body;

     TaskStatus status;

     public enum TaskStatus {
         NEW,
         ASSIGNED,
         IN_PROGRESS,
         COMPLETE
     }

    public Task(String title, String body, TaskStatus status) {
        this.title = title;
        this.body = body;
        this.status = status;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

}





