package com.example.androidproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.DialogInterface.BUTTON1;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA = "com.example.androidproject.EXTRA";
    final String PREFS_NAME = "PreferencesFile";

    private TrackMovement movementTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateUI();

        //Create instance of TrackMovement.
        movementTracker = new TrackMovement();
        //Start tracking user activity.
        movementTracker.track();
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

        // Add and remove vice
        if (v == findViewById(R.id.btnAddVice)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            SharedPreferences prefGet = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

            builder.setTitle(R.string.viceAddDialogTitle)
                    .setItems(EventSingleton.getEventInstance().getVices(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SharedPreferences.Editor prefEdit = prefGet.edit();
                            switch(which) {
                                case 0: // Alcohol
                                    prefEdit.putBoolean("VICE_ALCOHOL_ADDED", true);
                                    break;
                                case 1: // Tobacco
                                    prefEdit.putBoolean("VICE_TOBACCO_ADDED", true);
                                    break;
                                case 2: //Snuff
                                    prefEdit.putBoolean("VICE_SNUFF_ADDED", true);
                                    break;
                            }
                            prefEdit.commit();
                            updateUI();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if (v == findViewById(R.id.btnRemoveVice)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            SharedPreferences prefGet = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

            List<CharSequence> vices = getViceList(prefGet);
            CharSequence[] viceCharSequences = vices.toArray(new CharSequence[vices.size()]);
            ArrayList<Integer> selectedItems = new ArrayList();

            builder.setTitle(R.string.viceRemoveDialogTitle)
                    .setMultiChoiceItems(viceCharSequences, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                selectedItems.add(which);
                            } else if (selectedItems.contains(which)) {
                                selectedItems.remove(Integer.valueOf(which));
                            }
                        }
                    })
                    .setPositiveButton(R.string.viceRemoveDialogOkButton, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor prefEdit = prefGet.edit();
                            for (int item : selectedItems) {
                                if (viceCharSequences[item].toString() == getResources().getString(R.string.alcohol)) {
                                    prefEdit.putBoolean("VICE_ALCOHOL_ADDED", false);
                                }
                                if (viceCharSequences[item].toString() == getResources().getString(R.string.tobacco)) {
                                    prefEdit.putBoolean("VICE_TOBACCO_ADDED", false);
                                }
                                if (viceCharSequences[item].toString() == getResources().getString(R.string.snuff)) {
                                    prefEdit.putBoolean("VICE_SNUFF_ADDED", false);
                                }
                            }
                            prefEdit.commit();
                            updateUI();
                        }
                    })
                    .setNegativeButton(R.string.viceRemoveDialogNegativeButton, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void updateUI() {
        SharedPreferences prefGet = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        // If launching for the first time, move to GenderChooseActivity to determine user gender
        if (prefGet.getBoolean("FIRST_USER_LAUNCH", true)) {
            Log.d("Note", "First time launched");

            Intent getGender = new Intent(MainActivity.this, GenderChooseActivity.class);
            getGender.putExtra(EXTRA, PREFS_NAME);
            startActivity(getGender);
        }

        if (prefGet.getBoolean("VICE_ALCOHOL_ADDED", true)) {
            if (findViewById(R.id.alcoholLayout) == null) {
                LayoutInflater.from(MainActivity.this).inflate(R.layout.alcohol_box_layout, findViewById(R.id.scrollViewChildLayout));
                TextView weekPrice, monthPrice, consumptionWeek, consumptionMonth;
                weekPrice = findViewById(R.id.textAlcoholPriceWeek);
                monthPrice = findViewById(R.id.textAlcoholPriceMonth);
                consumptionWeek = findViewById(R.id.textAlcoholWeeklyConsumption);
                consumptionMonth = findViewById(R.id.textAlcoholMonthlyConsumption);

                weekPrice.setText(EventSingleton.getEventInstance().getPrice("Alcohol", "Week") + " €");
                monthPrice.setText(EventSingleton.getEventInstance().getPrice("Alcohol", "Month") + " €");
                consumptionWeek.setText(EventSingleton.getEventInstance().getAlcoholConsumption("Week"));
                consumptionMonth.setText(EventSingleton.getEventInstance().getAlcoholConsumption("Month"));
            }
        } else {
            if (findViewById(R.id.alcoholLayout) != null) {
                View alcohol = findViewById(R.id.alcoholLayout);
                ((ViewGroup) alcohol.getParent()).removeView(alcohol);
            }
        }
        if (prefGet.getBoolean("VICE_TOBACCO_ADDED", true)) {
            if (findViewById(R.id.tobaccoLayout) == null) {
                LayoutInflater.from(MainActivity.this).inflate(R.layout.tobacco_box_layout, findViewById(R.id.scrollViewChildLayout));
                TextView weekPrice, monthPrice, lostTimeWeek, lostTimeMonth;
                weekPrice = findViewById(R.id.textTobaccoPriceWeek);
                monthPrice = findViewById(R.id.textTobaccoPriceMonth);
                lostTimeWeek = findViewById(R.id.textTobaccoLostTimeWeek);
                lostTimeMonth = findViewById(R.id.textTobaccoLostTimeMonth);

                weekPrice.setText(EventSingleton.getEventInstance().getPrice("Tobacco", "Week") + " €");
                monthPrice.setText(EventSingleton.getEventInstance().getPrice("Tobacco", "Month") + " €");
                lostTimeWeek.setText(EventSingleton.getEventInstance().getTobaccoTime("Week"));
                lostTimeMonth.setText(EventSingleton.getEventInstance().getTobaccoTime("Month"));

            }
        } else {
            if (findViewById(R.id.tobaccoLayout) != null) {
                View tobacco = findViewById(R.id.tobaccoLayout);
                ((ViewGroup) tobacco.getParent()).removeView(tobacco);
            }
        }
        if (prefGet.getBoolean("VICE_SNUFF_ADDED", true)) {
            if (findViewById(R.id.snuffLayout) == null) {
                LayoutInflater.from(MainActivity.this).inflate(R.layout.snuff_box_layout, findViewById(R.id.scrollViewChildLayout));
            }
        } else {
            if (findViewById(R.id.snuffLayout) != null) {
                View snuff = findViewById(R.id.snuffLayout);
                ((ViewGroup) snuff.getParent()).removeView(snuff);
            }
        }
        Button removeVice = findViewById(R.id.btnRemoveVice);
        if (getViceList(prefGet).size() == 0) {
            removeVice.setAlpha(.5f);
            removeVice.setClickable(false);
        } else {
            removeVice.setAlpha(1f);
            removeVice.setClickable(true);
        }
    }

    private List<CharSequence> getViceList(SharedPreferences prefGet) {
        List<CharSequence> vices = new ArrayList<>();
        if (prefGet.getBoolean("VICE_ALCOHOL_ADDED", true)) {
            vices.add(getResources().getString(R.string.alcohol));
        }
        if (prefGet.getBoolean("VICE_TOBACCO_ADDED", true)) {
            vices.add(getResources().getString(R.string.tobacco));
        }
        if (prefGet.getBoolean("VICE_SNUFF_ADDED", true)) {
            vices.add(getResources().getString(R.string.snuff));
        }
        return vices;
    }
}