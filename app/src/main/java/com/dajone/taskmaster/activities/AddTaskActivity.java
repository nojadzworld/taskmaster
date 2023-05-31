package com.dajone.taskmaster.activities;

import static com.dajone.taskmaster.MainActivity.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.dajone.taskmaster.R;
import com.dajone.taskmaster.database.TaskmasterDatabase;
import com.dajone.taskmaster.models.Task;
import com.dajone.taskmaster.models.TaskStatus;


public class AddTaskActivity extends AppCompatActivity {
    TaskmasterDatabase taskmasterDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tasks);

        taskmasterDatabase = Room.databaseBuilder(
                getApplicationContext(),
                TaskmasterDatabase.class,
                DATABASE_NAME)
                .allowMainThreadQueries()
                .build();

        Spinner taskStatusSpinner = findViewById(R.id.spinner_add_task_status);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(
                this,
                        android.R.layout.simple_spinner_item,
                TaskStatus.values()
        ));


        Button addTaskButtonOnAddTaskPage = findViewById(R.id.addTaskRouteButton);
        addTaskButtonOnAddTaskPage.setText("ADD TASK");

        final TextView submittedLabel = findViewById(R.id.submittedLabel);
        submittedLabel.setVisibility(View.INVISIBLE);


        addTaskButtonOnAddTaskPage.setOnClickListener(v -> {
            EditText tittleEditText = findViewById(R.id.addTaskTitleEditText);
            EditText descriptionEditText = findViewById(R.id.addTaskDescriptionEditText);

            Task newTask = new Task(
                 tittleEditText.getText().toString(),
                 descriptionEditText.getText().toString(),
                 TaskStatus.fromString(taskStatusSpinner.getSelectedItem().toString())
            );

              taskmasterDatabase.taskDao().insertTask(newTask);
            ((EditText)findViewById(R.id.addTaskTitleEditText)).setText("");
            ((EditText)findViewById(R.id.addTaskDescriptionEditText)).setText("");
            taskStatusSpinner.setSelection(0);

            tittleEditText.requestFocus();
            Toast.makeText(AddTaskActivity.this, "Task saved!", Toast.LENGTH_SHORT).show();

            ((TextView) findViewById(R.id.submittedLabel)).setText(R.string.submitted_confirmation);

        });

    }
}
