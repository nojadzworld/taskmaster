package com.dajone.taskmaster.activities;



import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.dajone.taskmaster.R;
import com.dajone.taskmaster.models.Task;
import com.dajone.taskmaster.models.TaskStatus;
import com.dajone.taskmaster.utils.TaskStatusUtility;

import java.util.List;


public class AddTaskActivity extends AppCompatActivity {
    public static final String TAG = "add_task_tag";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tasks);

        List<String> statusList = TaskStatusUtility.getTaskStatusList();

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

            TaskStatus newStatus = TaskStatusUtility.taskStatusFromString(taskStatusSpinner.getSelectedItem().toString());

            Task newTask = Task.builder()
                    .title(titleEditText.getText().toString())
                    .body(descriptionEditText.getText().toString())
                    .status(newStatus)
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    success -> Log.i(TAG, "AddTaskActivity.onCreate(): added a task"),
                    failure -> Log.i(TAG, "AddTaskActivity.onCreate(): failed to add a task")
            );

            ((EditText)findViewById(R.id.addTaskTitleEditText)).setText("");
            ((EditText)findViewById(R.id.addTaskDescriptionEditText)).setText("");
            taskStatusSpinner.setSelection(0);

            tittleEditText.requestFocus();
            Toast.makeText(AddTaskActivity.this, "Task saved!", Toast.LENGTH_SHORT).show();

            ((TextView) findViewById(R.id.submittedLabel)).setText(R.string.submitted_confirmation);

        });

    }
}
