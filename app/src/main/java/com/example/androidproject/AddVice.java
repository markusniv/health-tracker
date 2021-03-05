package com.example.androidproject;

import android.os.Build;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class AddVice {

    private Date currentTime;
    private String date;

    public AddVice() {
        Calendar currentCalendar = Calendar.getInstance();
        currentTime = currentCalendar.getTime();
        LocalDateTime now = LocalDateTime.now();
        this.date = now.toString();
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public String getDate() {
        return this.date;
    }
}
