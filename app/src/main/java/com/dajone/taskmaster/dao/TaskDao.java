package com.dajone.taskmaster.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dajone.taskmaster.models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    public void insertTask(Task task);

    @Query("SELECT * FROM Task")
    public List<Task> findAll();

    @Query("SELECT COUNT(*) FROM Task")
    public int getTaskCount();


}
