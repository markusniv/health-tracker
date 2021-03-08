package com.example.androidproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.dataclasses.EventSingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity, also known as the ViceActivity, allows the user to add and remove vices from their
 * view and shows these vices in a ScrollView.
 */

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA = "com.example.androidproject.EXTRA";
    public static final String PREFS_NAME = "PreferencesFile";
    public static final String btnType = "ButtonType";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_vice_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_vice_activity:
                        break;

                    case R.id.menu_movement_activity:
                        Intent movementActivity = new Intent(MainActivity.this, MovementActivity.class);
                        startActivity(movementActivity);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case R.id.menu_statistics_activity:
                        Intent statisticsActivity = new Intent(MainActivity.this, StatisticsActivity.class);
                        startActivity(statisticsActivity);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                }
                return false;

            }
        });

        updateUI();

    }

    /**
     * Handles all the button clicks in MainActivity
     * @param v The id of the button being clicked
     */
    public void btnClick(View v) {

        // Alcohol buttons
        if (v == findViewById(R.id.btnAddAlcohol)) {
            Log.i("Note", "Adding alcohol portion");

            Intent alcoholIntent = new Intent(MainActivity.this, AlcoholActivity.class);
            startActivity(alcoholIntent);

        }
        if (v == findViewById(R.id.btnDrivingAbility)) {
            Log.i("Note", "Going to info about alcohol risks");

            Intent alcoholRisks = new Intent(MainActivity.this, RisksActivity.class);
            alcoholRisks.putExtra(btnType,"Alcohol");
            startActivity(alcoholRisks);
        }

        // Tobacco buttons
        if (v == findViewById(R.id.btnAddTobacco)) {
            Intent addTobacco = new Intent(MainActivity.this, AddTobaccoActivity.class);
            startActivity(addTobacco);
        }

        if (v == findViewById(R.id.btnTobaccoCounter)) {
            Log.i("Note", "Going to info about tobacco risks");

            Intent tobaccoRisks = new Intent(MainActivity.this, RisksActivity.class);
            tobaccoRisks.putExtra(btnType, "Tobacco");
            startActivity(tobaccoRisks);
        }

        // Add and remove vice
        if (v == findViewById(R.id.btnAddVice)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            SharedPreferences prefGet = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

            List<CharSequence> vices = getViceList(prefGet, "Add");
            Log.i("Vices", vices.toString());
            CharSequence[] viceCharSequences = vices.toArray(new CharSequence[vices.size()]);
            ArrayList<Integer> selectedItems = new ArrayList();

            builder.setTitle(R.string.viceAddDialogTitle)
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
                    .setPositiveButton(R.string.viceAddDialogOkButton, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor prefEdit = prefGet.edit();
                            for (int item : selectedItems) {
                                if (viceCharSequences[item].toString() == getResources().getString(R.string.alcohol)) {
                                    prefEdit.putBoolean("VICE_ALCOHOL_ADDED", true);
                                }
                                if (viceCharSequences[item].toString() == getResources().getString(R.string.tobacco)) {
                                    prefEdit.putBoolean("VICE_TOBACCO_ADDED", true);
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
        if (v == findViewById(R.id.btnRemoveVice)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            SharedPreferences prefGet = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

            List<CharSequence> vices = getViceList(prefGet, "Remove");
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

    /**
     * Updates the UI, removes and adds layouts as necessary depending on which vices the user has
     * chosen to be shown.
     */
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
        // If all vices have been added, set addVice button's alpha to half and disable them
        Button removeVice = findViewById(R.id.btnRemoveVice);
        Button addVice = findViewById(R.id.btnAddVice);
        Log.i("Vicelist size", String.valueOf(getViceList(prefGet, "Add").size()));
        Log.i("Vicelist length", String.valueOf(EventSingleton.getEventInstance().getVices().length));
        if (getViceList(prefGet, "Add").size() == 0) {
            addVice.setAlpha(.5f);
            addVice.setClickable(false);
        } else {
            addVice.setAlpha(1f);
            addVice.setClickable(true);
        }
        // If no vices are visible, set removeVice button's alpha to half and disable them
        if (getViceList(prefGet, "Remove").size() == 0) {
            removeVice.setAlpha(.5f);
            removeVice.setClickable(false);
        } else {
            removeVice.setAlpha(1f);
            removeVice.setClickable(true);
        }
    }

    /**
     * Get a CharSequence list of all the vices currently active to determine which ones can be added
     * and removed still
     * @return CharSequence list of all currently active vices
     */
    private List<CharSequence> getViceList(SharedPreferences prefGet, String addOrRemove) {
        List<CharSequence> vices = new ArrayList<>();

        switch(addOrRemove) {
            case "Add":
                if (!prefGet.getBoolean("VICE_ALCOHOL_ADDED", false)) {
                    vices.add(getResources().getString(R.string.alcohol));
                }
                if (!prefGet.getBoolean("VICE_TOBACCO_ADDED", false)) {
                    vices.add(getResources().getString(R.string.tobacco));
                }
                break;
            case "Remove":
                if (prefGet.getBoolean("VICE_ALCOHOL_ADDED", true)) {
                    vices.add(getResources().getString(R.string.alcohol));
                }
                if (prefGet.getBoolean("VICE_TOBACCO_ADDED", true)) {
                    vices.add(getResources().getString(R.string.tobacco));
                }
                break;
        }

        return vices;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}