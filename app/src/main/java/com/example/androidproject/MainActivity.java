package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA = "com.example.androidproject.EXTRA";
    final String PREFS_NAME = "PreferencesFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefGet = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        // If launching for the first time, move to GenderChooseActivity to determine user gender
        if (prefGet.getBoolean("FIRST_USER_LAUNCH", true)) {
            Log.d("Note", "First time launched");

            Intent getGender = new Intent(MainActivity.this, GenderChooseActivity.class);
            getGender.putExtra(EXTRA, PREFS_NAME);
            startActivity(getGender);
        }

    }
}