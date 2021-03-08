package com.example.androidproject;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
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
import java.sql.Time;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static com.example.androidproject.MainActivity.PREFS_NAME;

/**
 * An activity that shows the user their vice usage history. User can filter between a weekly and
 * a yearly view. The data is made into bar charts using MPAndroidChart.
 */
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

    /**
     * Change the chart view depending on the filters
     */
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

    /**
     * Creates the actual charts via MPAndroidChart
     * @param viceArrayList The specific vice events we want to get, gotten from the EventSingleton. Either tobacco or alcohol.
     * @param timeframe The timeframe we want to get the event from, entered as a String and it's either "Week" or "Year"
     * @param type The type of the vice, entered as a String and it's either "Alcohol" or "Tobacco"
     */
    private void createChart(ArrayList<AddVice> viceArrayList, String timeframe, String type) {

        // Creating a list with all the months and a list with all the weekdays to be used in the value formatter for the bar chart to show
        // strings instead of numbers on the X-axis
        List<String> months = Arrays.asList("Tammi", "Helmi", "Maalis", "Huhti", "Touko", "Kesä", "Heinä", "Elo", "Syys", "Loka", "Marras", "Joulu");
        List<String> days = Arrays.asList("Ma", "Ti", "Ke", "To", "Pe", "La", "Su");

        List<BarEntry> entries = new ArrayList<>();
        int amount = 0;

        LocalDateTime eventDateTime;
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        // Checking all the events from viceArrayList that correspond with the filtered timeframe
        switch(timeframe) {
            case "Week":
                amount = days.size();
                for (int i = 1; i < amount+1; i++) {
                    int viceCount = 0;
                    for (AddVice addVice : viceArrayList) {
                        eventDateTime = LocalDateTime.parse(addVice.getDate());
                        int eventWeekNumber = eventDateTime.get(weekFields.ISO.weekOfMonth());
                        int currentWeekNumber = LocalDateTime.now().get(weekFields.ISO.weekOfMonth());
                        if (eventDateTime.getMonth() == LocalDateTime.now().getMonth() && eventWeekNumber == currentWeekNumber && eventDateTime.getDayOfWeek().getValue() == i) {
                            viceCount++;
                        }
                    }
                    entries.add(new BarEntry(i-1, viceCount));
                }
                break;
            case "Year":
                amount = months.size();
                for (int i = 1; i < amount+1; i++) {
                    int viceCount = 0;
                    for (AddVice addVice : viceArrayList) {
                        eventDateTime = LocalDateTime.parse(addVice.getDate());
                        if (eventDateTime.getMonth().getValue() == i) {
                            viceCount++;
                        }
                    }
                    entries.add(new BarEntry(i-1, viceCount));
                }
                break;
        }

        BarDataSet dataSet;
        // Using the given vice type to determine the label for the bar chart
        switch(type) {
            case "Alcohol":
                dataSet = new BarDataSet(entries, MyApplication.getAppContext().getString(R.string.alcoholDrunk));
                break;
            case "Tobacco":
                dataSet = new BarDataSet(entries, MyApplication.getAppContext().getString(R.string.smokedCigarettes));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        dataSet.setValueFormatter(new DefaultValueFormatter(0));
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
        } if (timeframe.equals("Year")){
            xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        }

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setAxisMaximum(60);
        yAxis.setGranularity(1.0f);
        yAxis.setGranularityEnabled(true);
        yAxis.setValueFormatter(new DefaultValueFormatter(0));
        yAxis.removeAllLimitLines();

        if (type == "Alcohol") {
            LimitLine limitLine;
            if (timeframe.equals("Week")) {
                int weeklyDoses = EventSingleton.getEventInstance().getDoses("Week");
                limitLine = new LimitLine(weeklyDoses, MyApplication.getAppContext().getString(R.string.dangerZone));
            }
            else {
                int monthlyDoses = EventSingleton.getEventInstance().getDoses("Month");
                limitLine = new LimitLine(monthlyDoses, MyApplication.getAppContext().getString(R.string.dangerZone));
            }
            limitLine.setLineColor(Color.RED);
            limitLine.setLineWidth(1f);
            limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);


            yAxis.addLimitLine(limitLine);
        }
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.invalidate();
    }

}