package com.example.androidproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.dataclasses.AddMovementTimerTask;
import com.example.androidproject.dataclasses.TrackMovement;
import com.example.androidproject.dataclasses.AddMovement;
import com.example.androidproject.dataclasses.EventSingleton;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * MovementActivity class
 *
 */
public class MovementActivity extends AppCompatActivity {

    private TextView xAcc, yAcc, zAcc, xRot, yRot, zRot;

    //For formatting raw sensor data.
    DecimalFormat df = new DecimalFormat("#.##");

    //Array for raw sensor data.
    private float[] dynData;

    TrackMovement movTrack = TrackMovement.getMovementInstance();

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);

        movTrack.setListener(new TrackMovement.Listener() {
            /**
             *Triggers data fetch and realtime UI update when new sensor data is available from TrackMovement.
             */
            @Override
            public void onEvent() {
                updateDynamicUI();
                movTrack.collectData();
            }

        });

        xAcc = findViewById(R.id.xAccText);
        yAcc = findViewById(R.id.yAccText);
        zAcc = findViewById(R.id.zAccText);
        xRot = findViewById(R.id.xRotText);
        yRot = findViewById(R.id.yRotText);
        zRot = findViewById(R.id.zRotText);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_movement_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_vice_activity:
                        Intent viceActivity = new Intent(MovementActivity.this, MainActivity.class);
                        startActivity(viceActivity);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case R.id.menu_movement_activity:
                        break;

                    case R.id.menu_statistics_activity:
                        Intent statisticsActivity = new Intent(MovementActivity.this, StatisticsActivity.class);
                        startActivity(statisticsActivity);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;
                }
                return false;

            }
        });

        RadioGroup timeframeRadioGroup = findViewById(R.id.radioGroupMovement);
        RadioButton chosenTimeframe = findViewById(timeframeRadioGroup.getCheckedRadioButtonId());

        timeframeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateUI();
            }
        });

        updateUI();
    }

    /**
     * Activating movement tracking on button press.
     * @param v
     */
    public void onTrackBtnClick(View v) {
        movTrack.track();
        findViewById(R.id.buttonStartTrackingMovement).setEnabled(false);
    }

    /**
     * Get sensor data from TrackMovement and update display
     */
    public void updateDynamicUI() {
        dynData = movTrack.getData();
        xAcc.setText("X: " + df.format(dynData[0]) + " m/s²");
        yAcc.setText("Y: " + df.format(dynData[1]) + " m/s²");
        zAcc.setText("Z: " + df.format(dynData[2]) + " m/s²");
        xRot.setText("X: " + df.format(dynData[3]) + " rad/s");
        yRot.setText("Y: " + df.format(dynData[4]) + " rad/s");
        zRot.setText("Z: " + df.format(dynData[5]) + " rad/s");
        updateUI();
    }

    public void updateUI() {
        RadioGroup timeframeRadioGroup = findViewById(R.id.radioGroupMovement);
        RadioButton chosenTimeframe = findViewById(timeframeRadioGroup.getCheckedRadioButtonId());

        if (chosenTimeframe == findViewById(R.id.radioButtonDay)) {
            createChart("Day");
        }
        if (chosenTimeframe == findViewById(R.id.radioButtonWeek)) {
            createChart("Week");
        }
    }

    /**
     * Call to unregister sensor listeners.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        movTrack.unregisterSensorListeners();
    }

    private void createChart(String timeframe) {

        // Creating a list with all the months and a list with all the weekdays to be used in the value formatter for the bar chart to show
        // strings instead of numbers on the X-axis
        List<String> days = Arrays.asList("Ma", "Ti", "Ke", "To", "Pe", "La", "Su");

        List<BarEntry> entries = new ArrayList<>();
        int amount = 0;
        double activity = 0;

        LocalDateTime eventDateTime;
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        ArrayList<AddMovement> movementList = EventSingleton.getEventInstance().getMovementEventList();
        ArrayList<AddMovement> copiedMovements = new ArrayList<>(movementList);

        // Checking all the events from movementArrayList that correspond with the filtered timeframe
        switch(timeframe) {
            case "Week":
                amount = days.size();
                for (int i = 1; i < amount+1; i++) {
                    int movementCount = 0;
                    activity = 0;
                    for (AddMovement addMovement : copiedMovements) {
                        eventDateTime = LocalDateTime.parse(addMovement.getDate());
                        int eventWeekNumber = eventDateTime.get(weekFields.ISO.weekOfMonth());
                        int currentWeekNumber = LocalDateTime.now().get(weekFields.ISO.weekOfMonth());
                        if (eventDateTime.getMonth() == LocalDateTime.now().getMonth() && eventWeekNumber == currentWeekNumber && eventDateTime.getDayOfWeek().getValue() == i) {
                            activity += addMovement.getData();
                        }
                    }
                    entries.add(new BarEntry(i-1, (float) activity));
                }
                break;
            case "Day":
                amount = 23;
                for (int i = 0; i < amount; i++) {
                    activity = 0;
                    if (!AddMovementTimerTask.running) {
                        for (AddMovement addMovement : copiedMovements) {
                            eventDateTime = LocalDateTime.parse(addMovement.getDate());
                            if (eventDateTime.getHour() == i) {
                                activity += addMovement.getData();
                            }
                        }
                    }
                    entries.add(new BarEntry(i, (float) activity));
                }
                break;
        }

        BarDataSet dataSet = new BarDataSet(entries, MyApplication.getAppContext().getString(R.string.activity));
        dataSet.setValueFormatter(new DefaultValueFormatter(3));

        BarData barData = new BarData(dataSet);
        // Formatting the values of the chart so that only bars with values in them have numbers
        // representing them. This is to prevent "0.0" at every spot without a bar.

        barData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value > 0){
                    return super.getFormattedValue(value);
                }else{
                    return "";
                }
            }
        });
        BarChart chart = findViewById(R.id.chart);
        chart.setData(barData);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        if (timeframe.equals("Week")) {
            xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        } else {
            xAxis.setValueFormatter(new DefaultValueFormatter(0));
        }

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setGranularity(1.0f);
        yAxis.setGranularityEnabled(true);
        yAxis.setValueFormatter(new DefaultValueFormatter(0));
        yAxis.removeAllLimitLines();

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
    }

}