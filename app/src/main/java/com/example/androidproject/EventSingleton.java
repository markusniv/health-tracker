package com.example.androidproject;

import android.os.Build;

import java.text.DecimalFormat;
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

    private EventSingleton() {
        viceEventList = new ArrayList<>();
        movementEventList = new ArrayList<>();
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

    public double getWeeklyTobaccoPrice() {
        double price = 0;
        int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        for (int i = 0; i < viceEventList.size(); i++) {
            if (viceEventList.get(i) instanceof AddTobacco) {
                Calendar eventWeek = Calendar.getInstance();
                eventWeek.setTime(viceEventList.get(i).getCurrentTime());
                if (week == eventWeek.get(Calendar.WEEK_OF_YEAR)) {
                    price += ((AddTobacco) viceEventList.get(i)).getPrice();
                }
            }
        }
        return Double.parseDouble(df.format(price));
    }
}
