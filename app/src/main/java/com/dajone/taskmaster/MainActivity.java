package com.dajone.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dajone.taskmaster.activities.AddTaskActivity;
import com.dajone.taskmaster.activities.AllTasksActivity;
import com.dajone.taskmaster.activities.TaskDetail;
import com.dajone.taskmaster.activities.TaskSettings;
import com.dajone.taskmaster.adapters.TaskListRecyclerViewAdapter;
import com.dajone.taskmaster.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TASK_NAME_EXTRAS_TAG = "taskName";
    public static final String TASK_STATUS_EXTRAS_TAG = "taskStatus";
    public static final String TASK_DESCRIPTION_EXTRAS_TAG = "taskDescription";
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tasks);

        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task("Lab: 28 - RecyclerView", "Its a lab that adds a RecyclerView", Task.TaskStatus.IN_PROGRESS));
        taskList.add(new Task("Class 28 Reading", "Reading topic for the day", Task.TaskStatus.ASSIGNED));
        taskList.add(new Task("Code Challenge 28", "Quick Sort", Task.TaskStatus.COMPLETE));


        setUpSettingsButton();
        setUpRecyclerView(taskList);
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
    }
        public void setUpSettingsButton() {
            ImageView settingsImageView = findViewById(R.id.settingsButton);
            settingsImageView.setOnClickListener(v -> {
                Intent goToUserProfileSettings = new Intent(MainActivity.this, TaskSettings.class);
                startActivity(goToUserProfileSettings);
            });
        }

        public void setUpRecyclerView(List<Task> tasks) {
            RecyclerView taskListRecyclerView = (RecyclerView) findViewById(R.id.mainActivityTaskListRecyclerView);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            taskListRecyclerView.setLayoutManager(layoutManager);

            TaskListRecyclerViewAdapter adapter = new TaskListRecyclerViewAdapter(tasks, this);
            taskListRecyclerView.setAdapter(adapter);
        }

//    public void setupTaskButtons() {
//        Button taskOneButton = findViewById(R.id.button_main_activity_task_one);
//        setupTaskButton(taskOneButton);
//        Button taskTwoButton = findViewById(R.id.button_main_activity_task_two);
//        setupTaskButton(taskTwoButton);
//        Button taskThreeButton = findViewById(R.id.button_main_activity_task_three);
//        setupTaskButton(taskThreeButton);
//
//    }

    public void setupTaskButton(Button goToTaskButton) {
        goToTaskButton.setOnClickListener(v -> {
            Intent goToTaskIntent = new Intent(MainActivity.this, TaskDetail.class);
            String taskName = goToTaskButton.getText().toString();
            goToTaskIntent.putExtra(TASK_NAME_EXTRAS_TAG, taskName);

            startActivity(goToTaskIntent);
        });
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




