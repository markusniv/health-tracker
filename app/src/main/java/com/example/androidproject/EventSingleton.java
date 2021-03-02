package com.example.androidproject;

import android.os.Build;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventSingleton {
    private static final EventSingleton eventInstance = new EventSingleton();
    private ArrayList<AddVice> viceEventList;

    private ArrayList<AddMovement> movementEventList;

    private CharSequence[] vices = {MyApplication.getAppContext().getResources().getString(R.string.alcohol),
                                    MyApplication.getAppContext().getResources().getString(R.string.tobacco),
                                    MyApplication.getAppContext().getResources().getString(R.string.snuff)};

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

    public AddVice getViceEvent(int index) {
        return viceEventList.get(index);
    }

    public ArrayList<AddMovement> getMovementEventList() {
        return movementEventList;
    }

    public AddMovement getMovementEvent(int index) {
        return movementEventList.get(index);
    }

    public CharSequence[] getVices() {
        return vices;
    }

    public double getPrice(String vice, String timeframe) {
        double price = 0;
        int currentTime = 0;
        int eventTime = 0;
        Calendar eventCalendar = Calendar.getInstance();

        for (int i = 0; i < viceEventList.size(); i++) {
            eventCalendar.setTime(viceEventList.get(i).getCurrentTime());
            switch(timeframe) {
                case "Week":
                    currentTime = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
                    eventTime = eventCalendar.get(Calendar.WEEK_OF_YEAR);
                    break;
                case "Month":
                    currentTime = Calendar.getInstance().get(Calendar.MONTH);
                    eventTime = eventCalendar.get(Calendar.MONTH);
                    break;
            }
            if (currentTime == eventTime) {
                switch(vice) {
                    case "Tobacco":
                        if (viceEventList.get(i) instanceof AddTobacco) {
                            price += ((AddTobacco) viceEventList.get(i)).getPrice();
                        }
                        break;
                    case "Alcohol":
                        if (viceEventList.get(i) instanceof AddAlcohol) {
                            price += ((AddAlcohol) viceEventList.get(i)).getAlcohol().getPrice();
                        }
                        break;
                }
            }
        }
        return Double.parseDouble(df.format(price));
    }

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

    public String getAlcoholConsumption(String timeframe) {
        String alcoholConsumptionString = "";
        ArrayList<AddVice> alcoholEvents = getSpecificViceEvents("Alcohol", timeframe);
        switch(timeframe) {
            case "Week":
                alcoholConsumptionString = alcoholEvents.size() + "/5 annosta";
                break;
            case "Month":
                alcoholConsumptionString = alcoholEvents.size() + "/20 annosta";
                break;
        }
        return alcoholConsumptionString;
    }

    public ArrayList<AddVice> getSpecificViceEvents(String vice, String timeframe) {
        ArrayList<AddVice> viceEvents = new ArrayList<>();
        int currentTime = 0;
        int eventTime = 0;
        Calendar eventCalendar = Calendar.getInstance();

        for (int i = 0; i < viceEventList.size(); i++) {
            eventCalendar.setTime(viceEventList.get(i).getCurrentTime());
            switch(timeframe) {
                case "Week":
                    currentTime = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
                    eventTime = eventCalendar.get(Calendar.WEEK_OF_YEAR);
                    break;
                case "Month":
                    currentTime = Calendar.getInstance().get(Calendar.MONTH);
                    eventTime = eventCalendar.get(Calendar.MONTH);
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
}
