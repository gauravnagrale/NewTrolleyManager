package com.example.newtrolleymanager;

import android.app.Application;

import com.firebase.client.Firebase;

public class NewTrolleyManager extends Application{


    public void onCreate()
    {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}

