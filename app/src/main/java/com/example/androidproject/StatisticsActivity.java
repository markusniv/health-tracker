package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_statistics_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_vice_activity:
                        Intent statisticsActivity = new Intent(StatisticsActivity.this, MainActivity.class);
                        startActivity(statisticsActivity);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case R.id.menu_movement_activity:
                        Intent movementActivity = new Intent(StatisticsActivity.this, MovementActivity.class);
                        startActivity(movementActivity);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                    case R.id.menu_statistics_activity:
                        break;
                }
                return false;

            }
        });

        RadioGroup viceRadioGroup = findViewById(R.id.radioGroupVices);
        RadioGroup timeframeRadioGroup = findViewById(R.id.radioGroupTimeframe);

        viceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateUI();
            }
        });
        timeframeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateUI();
            }
        });

        updateUI();
    }

    private void updateUI() {
        RadioGroup viceRadioGroup = findViewById(R.id.radioGroupVices);
        RadioButton chosenVice = findViewById(viceRadioGroup.getCheckedRadioButtonId());
        RadioGroup timeframeRadioGroup = findViewById(R.id.radioGroupTimeframe);
        RadioButton chosenTimeframe = findViewById(timeframeRadioGroup.getCheckedRadioButtonId());

        ArrayList<AddVice> addTobaccos = EventSingleton.getEventInstance().getSpecificViceEvents("Tobacco");
        ArrayList<AddVice> addAlcohols = EventSingleton.getEventInstance().getSpecificViceEvents("Alcohol");

        if (chosenVice == findViewById(R.id.radioAlcohol)) {
            if (chosenTimeframe == findViewById(R.id.radioWeekly)) {
                createChart(addAlcohols, "Week", "Alcohol");
            }
            if (chosenTimeframe == findViewById(R.id.radioYearly)) {
                createChart(addAlcohols, "Year", "Alcohol");
            }
        }
        if (chosenVice == findViewById(R.id.radioTobacco)) {
            if (chosenTimeframe == findViewById(R.id.radioWeekly)) {
                createChart(addTobaccos, "Week", "Tobacco");
            }
            if (chosenTimeframe == findViewById(R.id.radioYearly)) {
                createChart(addTobaccos, "Year", "Tobacco");
            }
        }
    }

    private void createChart(ArrayList<AddVice> viceArrayList, String timeframe, String type) {

        List<String> months = Arrays.asList("Tammi", "Helmi", "Maalis", "Huhti", "Touko", "Kesä", "Heinä", "Elo", "Syys", "Loka", "Marras", "Joulu");
        List<String> days = Arrays.asList("Ma", "Ti", "Ke", "To", "Pe", "La", "Su");

        List<BarEntry> entries = new ArrayList<>();
        int amount = 0;
        Calendar eventCalendar = Calendar.getInstance();
        switch(timeframe) {
            case "Week":
                amount = days.size();
                for (int i = 0; i < 12; i++) {
                    for (int p = 0; p < amount; p++) {
                        int viceCount = 0;
                        for (AddVice addVice : viceArrayList) {
                            eventCalendar.setTime(addVice.getCurrentTime());
                            if (eventCalendar.get(Calendar.MONTH) == i && eventCalendar.get(Calendar.DAY_OF_WEEK) == p) {
                                viceCount++;
                            }
                        }
                        entries.add(new BarEntry(p, viceCount));
                    }
                }

                break;
            case "Year":
                amount = days.size();
                for (int i = 0; i < amount; i++) {
                    int viceCount = 0;
                    for (AddVice addVice : viceArrayList) {
                        eventCalendar.setTime(addVice.getCurrentTime());
                        if (eventCalendar.get(Calendar.MONTH) == i) {
                            viceCount++;
                        }
                    }
                    entries.add(new BarEntry(i, viceCount));
                }
                break;
        }

        BarDataSet dataSet;
        switch(type) {
            case "Alcohol":
                dataSet = new BarDataSet(entries, "Annoksia juotu");
                break;
            case "Tobacco":
                dataSet = new BarDataSet(entries, "Tupakoita poltettu");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        dataSet.setValueFormatter(new DefaultValueFormatter(0));
        BarData barData = new BarData(dataSet);
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
        } if (timeframe.equals("Year")){
            xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        }

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setGranularity(1.0f);
        yAxis.setGranularityEnabled(true);
        yAxis.setValueFormatter(new DefaultValueFormatter(0));
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
    }

}