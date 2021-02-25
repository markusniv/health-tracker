package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.DialogInterface.BUTTON1;


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

    /**
     * Handles all the button clicks in MainActivity
     * @param v The id of the button being clicked
     */
    public void btnClick(View v) {

        // Upper buttons
        if (v == findViewById(R.id.btnActivityScreen)) {
            // TODO: Add moving to ActivityScreenActivity
        }
        if (v == findViewById(R.id.btnStatistics)) {
            // TODO: Add moving to StatisticsActivity
        }

        // Alcohol buttons
        if (v == findViewById(R.id.btnAddAlcohol)) {
            // TODO: Add moving to AlcoholAddActivity
        }
        if (v == findViewById(R.id.btnDrivingAbility)) {
            // TODO: Add moving to DrivingAbilityActivity
        }

        // Tobacco buttons
        if (v == findViewById(R.id.btnAddTobacco)) {
            Intent addTobacco = new Intent(MainActivity.this, AddTobaccoActivity.class);
            startActivity(addTobacco);
        }
        if (v == findViewById(R.id.btnTobaccoCounter)) {
            // TODO: Add moving to TobaccoCounterActivity
        }

        // Add vice
        if (v == findViewById(R.id.btnAddVice)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            Log.i("builder status", builder.toString());
            builder.setTitle(R.string.viceDialogTitle)
                    .setItems(EventSingleton.getEventInstance().getVices(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which) {
                                case 0: // Alcohol
                                    LayoutInflater.from(MainActivity.this).inflate(R.layout.alcohol_box_layout, findViewById(R.id.scrollViewChildLayout));
                                    break;
                                case 1: // Tobacco
                                    LayoutInflater.from(MainActivity.this).inflate(R.layout.tobacco_box_layout, findViewById(R.id.scrollViewChildLayout));
                                    break;
                                case 2: //Snuff
                                    LayoutInflater.from(MainActivity.this).inflate(R.layout.snuff_box_layout, findViewById(R.id.scrollViewChildLayout));
                                    break;
                            }
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}