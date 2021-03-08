package com.example.androidproject.dataclasses;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class AddMovement {

    private String date;
    private double data;

    public AddMovement(double data) {
        date = LocalDateTime.now().toString();
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public double getData() {
        return data;
    }
}
