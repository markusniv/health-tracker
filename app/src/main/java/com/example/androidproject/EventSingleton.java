package com.example.androidproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.EventLog;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.androidproject.MainActivity.PREFS_NAME;

public class EventSingleton {
    private static final EventSingleton eventInstance = new EventSingleton();
    private ArrayList<AddVice> viceEventList;

    private ArrayList<AddMovement> movementEventList;

    private DecimalFormat df = new DecimalFormat("0.00");
    private DecimalFormatSymbols dfs = new DecimalFormatSymbols();

    private EventSingleton() {
        viceEventList = new ArrayList<>();
        movementEventList = new ArrayList<>();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);

    }

    public static EventSingleton getEventInstance() {
        return eventInstance;
    }

    public void AddViceEvent(AddVice addVice) {
        viceEventList.add(addVice);
    }

    public void AddMovementEvent(AddMovement addMovement) {
        movementEventList.add(addMovement);
    }

    public ArrayList<AddVice> getViceEventList() {
        return viceEventList;
    }

    public ArrayList<AddMovement> getMovementEventList() {
        return movementEventList;
    }

    public AddMovement getMovementEvent(int index) {
        return movementEventList.get(index);
    }

    /**
     * Returns the total price of added events from a certain timeframe
     * @param vice The type of vice we need the prices of
     * @param timeframe The timeframe we need the prices from
     * @return The total price of the vice from the certain timeframe, formatted to two decimals
     */
    public double getPrice(String vice, String timeframe) {
        double price = 0;

        ArrayList<AddVice> vices = getSpecificViceEvents(vice, timeframe);
        for (AddVice viceEvent : vices) {
            price += viceEvent.getPrice();
        }

        Log.i("price: ", String.valueOf(price));
        return Double.parseDouble(df.format(price));
    }

    /**
     * Get the total time spent smoking tobacco from a certain timeframe
     * @param timeframe The timeframe to check
     * @return The amount of time spent in a String form, "x h, y min", where x is hours and y is minutes
     */
    public String getTobaccoTime(String timeframe) {
        String spentTimeString = "Menetetty aika ";
        int spentTimeMinutes = 0;
        int spentTimeHours = 0;
        ArrayList<AddVice> tobaccoEvents = getSpecificViceEvents("Tobacco", timeframe);
        for (int i = 0; i < tobaccoEvents.size(); i++) {
            spentTimeMinutes += 5;
        }
        if (spentTimeMinutes >= 60) {
            spentTimeHours = spentTimeMinutes / 60;
            spentTimeMinutes = spentTimeMinutes - (spentTimeHours * 60);
            spentTimeString += spentTimeHours + " h " + spentTimeMinutes + " min";
        } else {
            spentTimeString += spentTimeMinutes + " min";
        }
        return spentTimeString;
    }

    /**
     * Get the total alcohol consumption from a certain timeframe
     * @param timeframe The timeframe to check
     * @return The total alcohol consumption in a String form, "x/y annosta" or "x/y annosta",
     * where x is the total dose amount and the y is determined by the weekly and monthly limits
     * depending on your gender, check getDoses()
     */
    public String getAlcoholConsumption(String timeframe) {
        String dosesWeekly = "/" + getDoses("Week") + " annosta";
        String dosesMonthly = "/" + getDoses("Month") + " annosta";

        String alcoholConsumptionString = "";
        ArrayList<AddVice> alcoholEvents = getSpecificViceEvents("Alcohol", timeframe);
        switch(timeframe) {
            case "Week":
                alcoholConsumptionString = alcoholEvents.size() + dosesWeekly;
                break;
            case "Month":
                alcoholConsumptionString = alcoholEvents.size() + dosesMonthly;
                break;
        }
        return alcoholConsumptionString;
    }

    /**
     * Get specific vice events from a certain timeframe
     * @param vice The vices that we wish to get
     * @param timeframe The timeframe to check
     * @return An ArrayList of all the vices of the wanted type in the wanted timeframe
     */
    public ArrayList<AddVice> getSpecificViceEvents(String vice, String timeframe) {
        ArrayList<AddVice> viceEvents = new ArrayList<>();
        int currentTime = 0;
        int eventTime = 0;
        LocalDateTime eventDateTime;
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        for (int i = 0; i < viceEventList.size(); i++) {
            eventDateTime = LocalDateTime.parse(viceEventList.get(i).getDate());
            switch(timeframe) {
                case "Week":
                    currentTime = LocalDateTime.now().get(weekFields.ISO.weekOfYear());
                    eventTime = eventDateTime.get(weekFields.ISO.weekOfYear());
                    break;
                case "Month":
                    currentTime = LocalDateTime.now().getMonthValue();
                    eventTime = eventDateTime.getMonthValue();
                    break;
            }
            if (currentTime == eventTime) {
                switch(vice) {
                    case "Tobacco":
                        if (viceEventList.get(i) instanceof AddTobacco) {
                            viceEvents.add(viceEventList.get(i));
                        }
                        break;
                    case "Alcohol":
                            if (viceEventList.get(i) instanceof AddAlcohol) {
                                viceEvents.add(viceEventList.get(i));
                            }
                        break;
                }
            }
        }
        return viceEvents;
    }

    /**
     * Get all the specific vice events, not caring about the timeframe
     * @param vice The vices that we wish to get
     * @return An ArrayList of all the vices of the wanted type
     */
    public ArrayList<AddVice> getSpecificViceEvents(String vice) {
        ArrayList<AddVice> viceEvents = new ArrayList<>();

        for (int i = 0; i < viceEventList.size(); i++) {
            switch(vice) {
                case "Tobacco":
                    if (viceEventList.get(i) instanceof AddTobacco) {
                        viceEvents.add(viceEventList.get(i));
                    }
                    break;
                case "Alcohol":
                    if (viceEventList.get(i) instanceof AddAlcohol) {
                        viceEvents.add(viceEventList.get(i));
                    }
                    break;
            }
        }
        return viceEvents;
    }

    /**
     * Get the risky alcohol consumption limits of the user for a certain timeframe, depending
     * on their gender
     * @param timeframe The timeframe to get the limits for, either "Week" or "Month"
     * @return The risky dose amount for the timeframe
     */
    public int getDoses(String timeframe) {
        SharedPreferences prefGet = MyApplication.getAppContext().getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        String gender = prefGet.getString("GENDER_KEY", "Male");
        int weeklyDoses = 0;
        int monthlyDoses = 0;
        switch (gender) {
            case "Male":
                weeklyDoses = 14;
                monthlyDoses = 56;
                break;
            case "Female":
                weeklyDoses = 7;
                monthlyDoses = 28;
                break;
        }
        if (timeframe == "Week") {
            return weeklyDoses;
        } else {
            return monthlyDoses;
        }
    }
}
