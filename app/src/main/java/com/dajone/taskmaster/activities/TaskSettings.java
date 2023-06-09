package com.dajone.taskmaster.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.dajone.taskmaster.R;
import com.dajone.taskmaster.activities.authentication.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TaskSettings extends AppCompatActivity {
    public static final String USERNAME_TAG = "username";
    public static final String TEAM_TAG = "team";
    public static final String TAG = "settings_activity";
    Spinner taskTeamSpinner = null;
    AuthUser authUser;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        addUsernameEditText(preferences);
        populateTeamSpinner(preferences);
        setupSaveButton(preferences);
        setUpLoginButton();
        setUpLogoutButton();
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForAuthUser();
    }

    public void checkForAuthUser() {
        Amplify.Auth.getCurrentUser(
                success -> {
                    Log.i(TAG, "User authenticated with username: " + success.getUsername());
                    authUser = success;
                    runOnUiThread(this::renderButtons);
                },
                failure -> {
                    Log.i(TAG, "There is no current authenticated user");
                    authUser = null;
                    runOnUiThread(this::renderButtons);
                }
        );
    }

    public void renderButtons() {
        if (authUser == null) {
            Button loginButton = findViewById(R.id.userProfileActivityLoginButton);
            loginButton.setVisibility(View.VISIBLE);
            Button logoutButton = findViewById(R.id.userProfileActivityLogoutButton);
            logoutButton.setVisibility(View.INVISIBLE);
        } else {
            Button loginButton = findViewById(R.id.userProfileActivityLoginButton);
            loginButton.setVisibility(View.INVISIBLE);
            Button logoutButton = findViewById(R.id.userProfileActivityLogoutButton);
            logoutButton.setVisibility(View.VISIBLE);
        }
    }


    public void addUsernameEditText(SharedPreferences preferences) {
        String username = preferences.getString(USERNAME_TAG, "");
        ((EditText) findViewById(R.id.usernameEditText)).setText(username);
    }

    public void populateTeamSpinner(SharedPreferences preferences) {
        String teamString = preferences.getString(TEAM_TAG, "");
        CompletableFuture<List<Team>> teamsFuture = new CompletableFuture<>();
        List<Team> teamList = new ArrayList<>();
        List<String> teamListAsString = new ArrayList<>();
        taskTeamSpinner = findViewById(R.id.spinner_settings_team);
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(TAG, "Read Teams successfully");
                    for (Team team : success.getData()) {
                        teamList.add(team);
                    }
                    teamsFuture.complete(teamList);
                    runOnUiThread(() -> {
                        teamListAsString.add("All");
                        for (Team team : teamList)
                            teamListAsString.add(team.getName());
                        taskTeamSpinner.setAdapter(new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_spinner_item,
                                teamListAsString
                        ));
                        if (!teamString.isEmpty()) {
                            taskTeamSpinner.setSelection(teamListAsString.indexOf(teamString));
                        }
                    });
                },
                failure -> Log.i(TAG, "Failed to read Teams")
        );
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
    public void setUpLoginButton() {
        Button loginButton = findViewById(R.id.userProfileActivityLoginButton);
        loginButton.setOnClickListener(v -> {
            Intent goToLoginActivity = new Intent(TaskSettings.this, LoginActivity.class);
            startActivity(goToLoginActivity);
        });
    }

    public void setUpLogoutButton() {
        Button logoutButton = findViewById(R.id.userProfileActivityLogoutButton);
        logoutButton.setOnClickListener(v -> {
            // Amplify User Logout code block
            AuthSignOutOptions options = AuthSignOutOptions.builder()
                    .globalSignOut(true)
                    .build();

            Amplify.Auth.signOut(options, signOutResult -> {
                if (signOutResult instanceof AWSCognitoAuthSignOutResult.CompleteSignOut) {
                    Log.i(TAG,"Global logout successful!");
                } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.PartialSignOut) {
                    Log.i(TAG,"Partial logout successful!");
                } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.FailedSignOut) {
                    Log.i(TAG,"Logout failed: " + signOutResult.toString());
                }
            });
        });
    }

}
