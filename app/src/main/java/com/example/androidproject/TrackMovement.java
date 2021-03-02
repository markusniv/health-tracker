package com.example.androidproject;


public class TrackMovement {

    //True if there is activity above a set dead-band.
    private boolean activity;
    //True if activity is high intensity.
    private boolean highActivity;
    //True if there is acceleration above a set dead-band.
    private boolean acceleration;
    //True if there is rotation above a set dead-band.
    private boolean rotation;
    //Value for compound acceleration on all axes. Arbitrary scale.
    //Used to determine if activity is low or high intensity.
    private int accelerationRate;
    private int rotationRate;
    //Value for compound rotation rate on all axes. Arbitrary scale.
    //Used to determine if activity is low or high intensity.

}
