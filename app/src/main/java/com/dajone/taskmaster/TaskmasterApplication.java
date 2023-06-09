package com.dajone.taskmaster;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;

public class TaskmasterApplication extends Application {
    public static final String TAG = "taskmaster_application_tag";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
        }   catch (AmplifyException e) {
            Log.e(TAG, "Error during initialization of Amplify: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
