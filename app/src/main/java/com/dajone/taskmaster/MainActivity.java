package com.dajone.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.amplifyframework.datastore.generated.model.Task;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.dajone.taskmaster.activities.AddTaskActivity;
import com.dajone.taskmaster.activities.AllTasksActivity;
import com.dajone.taskmaster.activities.TaskSettings;
import com.dajone.taskmaster.adapters.TaskListRecyclerViewAdapter;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "main_activity_tag";
    public static final String TASK_NAME_EXTRAS_TAG = "taskName";
    public static final String TASK_STATUS_EXTRAS_TAG = "taskStatus";
    public static final String TASK_DESCRIPTION_EXTRAS_TAG = "taskDescription";

    List<Task> tasks = new ArrayList<>();
    TaskListRecyclerViewAdapter taskListRecyclerViewAdapter;
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
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String currentTeam = preferences.getString(TaskSettings.TEAM_TAG, "All");

        tasks.clear();
        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class),
                success -> {
                    Log.i(TAG, "Read products successfully");
                    for (Task task : success.getData()) {
                        if (currentTeam.equals("All") || task.getTeam().getName().equals(currentTeam)) {
                         tasks.add(task);
                        }
                    }
                    runOnUiThread(() -> taskListRecyclerViewAdapter.notifyDataSetChanged());
                },
                failure -> Log.i(TAG, "Failed to read products")
        );

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

            taskListRecyclerViewAdapter = new TaskListRecyclerViewAdapter(new ArrayList<>(), this);
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
        Button allTaskButtonOnAddTaskPage = findViewById(R.id.allTaskToAllTasks);


        allTaskButtonOnAddTaskPage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllTasksActivity.class);
            startActivity(intent);
        });
    }
    }




