package com.example.androidproject;

import java.util.Calendar;
import java.util.Date;

public class AddMovement {

    private Date currentTime;
    // TODO: Add Movement-object as parameter once it's finished

    public AddMovement() {
        currentTime = Calendar.getInstance().getTime();
    }

    public Date getCurrentTime() {
        return currentTime;
    }

}
