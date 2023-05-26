package com.dajone.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dajone.taskmaster.activities.AddTaskActivity;
import com.dajone.taskmaster.activities.AllTasksActivity;
import com.dajone.taskmaster.activities.TaskSettings;
import com.dajone.taskmaster.adapters.TaskListRecyclerViewAdapter;
import com.dajone.taskmaster.database.TaskmasterDatabase;
import com.dajone.taskmaster.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TASK_NAME_EXTRAS_TAG = "taskName";
    public static final String TASK_STATUS_EXTRAS_TAG = "taskStatus";
    public static final String TASK_DESCRIPTION_EXTRAS_TAG = "taskDescription";

    public static final String DATABASE_NAME = "dajone-taskmaster";
    List<Task> tasks = new ArrayList<>();
    TaskListRecyclerViewAdapter taskListRecyclerViewAdapter;
    TaskmasterDatabase taskmasterDatabase;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tasks);

        setupTasksFromDatabase();
        setUpSettingsButton();
        setUpRecyclerView();
        setupAddTaskButton();
        setupAllTasksButton();

    }
    @Override
    protected void onResume() {
        super.onResume();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString(TaskSettings.USERNAME_TAG, "");

        if (!username.isEmpty()) {
            String myTasksTitleTextView = username + "'s Tasks";
            ((TextView) findViewById(R.id.my_tasks_title)).setText(myTasksTitleTextView);
        }

        setupTasksFromDatabase();
        taskListRecyclerViewAdapter.updateTasksData(tasks);
    }

    public void setupTasksFromDatabase() {
        taskmasterDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        TaskmasterDatabase.class,
                        DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
        tasks = taskmasterDatabase.taskDao().findAll();

    }
        public void setUpSettingsButton() {
            ImageView settingsImageView = findViewById(R.id.settingsButton);
            settingsImageView.setOnClickListener(v -> {
                Intent goToUserProfileSettings = new Intent(MainActivity.this, TaskSettings.class);
                startActivity(goToUserProfileSettings);
            });
        }

        public void setUpRecyclerView() {
            RecyclerView taskListRecyclerView = findViewById(R.id.mainActivityTaskListRecyclerView);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            taskListRecyclerView.setLayoutManager(layoutManager);

            taskListRecyclerViewAdapter = new TaskListRecyclerViewAdapter(tasks, this);
            taskListRecyclerView.setAdapter(taskListRecyclerViewAdapter);
        }

       public void setupAddTaskButton() {
           Button setupAddTaskButton = findViewById(R.id.addTaskRouteButton);

        setupAddTaskButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        startActivity(intent);
        });
}

    public void setupAllTasksButton() {
        Button allTaskButtonOnAddTaskPage = findViewById(R.id.allTasksToAllTasks);

        allTaskButtonOnAddTaskPage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllTasksActivity.class);
            startActivity(intent);
        });
    }
    }




