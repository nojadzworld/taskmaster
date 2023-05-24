package com.dajone.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    public static final String TASK_NAME_EXTRAS_TAG = "taskName";
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tasks);

        setUpSettingsButton();
        setupTaskButtons();
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

    public void setupTaskButtons() {
        Button taskOneButton = findViewById(R.id.button_main_activity_task_one);
        setupTaskButton(taskOneButton);
        Button taskTwoButton = findViewById(R.id.button_main_activity_task_two);
        setupTaskButton(taskTwoButton);
        Button taskThreeButton = findViewById(R.id.button_main_activity_task_three);
        setupTaskButton(taskThreeButton);

    }

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




