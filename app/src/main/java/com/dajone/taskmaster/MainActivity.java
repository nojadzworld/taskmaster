package com.dajone.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import com.dajone.taskmaster.activities.AddTaskActivity;
import com.dajone.taskmaster.activities.AddTaskToAllTasks;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tasks);
        // Steps for adding functionality to JS UI elements
        // 1: Get UI element by ID
        // 2. Add event listener to that element
        // 3. Attach a callback function with an onClick() method
        // 4. Do stuff in the callback (onClick())

        //Adding functionality to a UI element in java (android)
        // 1: grab element using findByID()
        // 2. grabbing a view, assigning to a variable and setting the value in 2 lines

        Button submitButton = findViewById(R.id.addTaskRouteButton);
        submitButton.setText(R.string.add_task);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);

            }
        });

        Button allTaskButtonOnAddTaskPage = findViewById(R.id.allTasksToAllTasks);
        allTaskButtonOnAddTaskPage.setText("ALL TASKS");

        allTaskButtonOnAddTaskPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskToAllTasks.class);
                startActivity(intent);
            }

        });
        // Lets create and trigger the Intent


    }
}