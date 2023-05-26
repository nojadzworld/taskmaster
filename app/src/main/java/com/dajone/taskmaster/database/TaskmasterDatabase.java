package com.dajone.taskmaster.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dajone.taskmaster.dao.TaskDao;
import com.dajone.taskmaster.models.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskmasterDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
