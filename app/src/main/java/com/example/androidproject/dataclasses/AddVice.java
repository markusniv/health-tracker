package com.example.androidproject.dataclasses;

import android.os.Build;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Parent class for all vice addition classes, contains the timestamps when they were added. Timestamp
 * is stored both in Date form as well as a String form from the LocalDateTime.now() timestamp.
 */
public class AddVice {

    private String date;
    private double price;

    public AddVice() {
        Calendar currentCalendar = Calendar.getInstance();
        LocalDateTime now = LocalDateTime.now();
        this.date = now.toString();
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return this.date;
    }
}
