package com.dajone.taskmaster.activities;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.dajone.taskmaster.MainActivity;
import com.dajone.taskmaster.R;
import com.dajone.taskmaster.models.TaskStatus;

import java.io.File;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);


        Intent callingIntent = getIntent();
        String taskNameString = null;
        String taskAttachmentKeyString = null;
        String taskDescriptionString = null;
        String taskStatusString = null;
        TaskStatus status = null;

        if (callingIntent != null) {
            taskNameString = callingIntent.getStringExtra(MainActivity.TASK_NAME_EXTRAS_TAG);

            TextView taskNameTextView = findViewById(R.id.task_details_activity_title);

            if (taskNameString != null) {
                taskNameTextView.setText(taskNameString);
            }

            taskAttachmentKeyString = callingIntent.getStringExtra(MainActivity.TASK_ATTACHMENT_EXTRA_TAG);

            if (taskAttachmentKeyString != null && !taskAttachmentKeyString.isEmpty()) {
                Amplify.Storage.downloadFile(
                        taskAttachmentKeyString,
                        new File(getApplication().getFilesDir(), taskAttachmentKeyString),
                        success -> {
                            ImageView taskImageView = findViewById(R.id.image_view_task_detail_attachment);
                            taskImageView.setImageBitmap(BitmapFactory.decodeFile(success.getFile().getPath()));
                        },
                        failure -> {
                            Log.e(TAG, "Unable to get image with S3 key because " + failure.getMessage());
                        }
                );
            }

            taskStatusString = callingIntent.getStringExtra(MainActivity.TASK_STATUS_EXTRAS_TAG);

            TextView taskStatusTextView = findViewById(R.id.taskStatusTextView);

            if (taskStatusString != null) {
                taskStatusTextView.setText(taskStatusString);
            }

            taskDescriptionString = callingIntent.getStringExtra(MainActivity.TASK_DESCRIPTION_EXTRAS_TAG);

            TextView taskDetailsTextView = findViewById(R.id.taskDetailTextView);

            if (taskDescriptionString != null) {
                taskDetailsTextView.setText(taskDescriptionString);
            }

        }
    }
}
