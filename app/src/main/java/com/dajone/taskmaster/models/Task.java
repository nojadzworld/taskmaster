package com.dajone.taskmaster.models;



import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
     public long id;
     private String title;
     private String body;

    private TaskStatus status;
  
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





