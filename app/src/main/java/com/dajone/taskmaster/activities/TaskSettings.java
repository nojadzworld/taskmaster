package com.dajone.taskmaster.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dajone.taskmaster.R;

public class TaskSettings extends AppCompatActivity {
    public static final String USERNAME_TAG = "username";

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        addUsernameEditText(preferences);
        setupSaveButton(preferences);
    }

    public void addUsernameEditText(SharedPreferences preferences) {
        String username = preferences.getString(USERNAME_TAG, "");
        ((EditText) findViewById(R.id.usernameEditText)).setText(username);
    }

    public void setupSaveButton(SharedPreferences preferences) {
        Button saveButton = findViewById(R.id.button_settings_activity_save);
        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor preferenceEditor = preferences.edit();

            EditText usernameEditText = findViewById(R.id.usernameEditText);
            String usernameString = usernameEditText.getText().toString();

            preferenceEditor.putString(USERNAME_TAG, usernameString);
            preferenceEditor.apply();

            Toast.makeText(TaskSettings.this, "Settings saved!", Toast.LENGTH_SHORT).show();
        });
    }
}
