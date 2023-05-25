package com.dajone.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.dajone.taskmaster.R;



public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tasks);


        Button addTaskButtonOnAddTaskPage = findViewById(R.id.addTaskToAllTasks);
        addTaskButtonOnAddTaskPage.setText("ADD TASK");

        final TextView submittedLabel = findViewById(R.id.submittedLabel);
        submittedLabel.setVisibility(View.INVISIBLE);


        addTaskButtonOnAddTaskPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submittedLabel.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        submittedLabel.setVisibility(View.INVISIBLE);
                    }
                }, 2000); // 2000 milliseconds = 2 seconds

                Intent intent = new Intent(AddTaskActivity.this, AllTasksActivity.class);
                startActivity(intent);

            }
        });





    }
}
