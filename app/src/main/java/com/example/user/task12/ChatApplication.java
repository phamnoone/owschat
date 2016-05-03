package com.example.user.task12;

import com.firebase.client.Firebase;

/**
 * Created by USER on 5/16/2015.
 */
public class ChatApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}