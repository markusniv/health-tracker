package com.example.androidproject;

import android.app.Application;
import android.content.Context;

/**
 * An activity that is created to get access to a static Context that can be used in all classes
 */
public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
