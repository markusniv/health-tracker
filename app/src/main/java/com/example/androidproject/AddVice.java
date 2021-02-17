package com.example.androidproject;

import java.util.Calendar;
import java.util.Date;

public class AddVice {

    private Date currentTime;

    public AddVice() {
         currentTime = Calendar.getInstance().getTime();
    }

    public Date getCurrentTime() {
        return currentTime;
    }
}
