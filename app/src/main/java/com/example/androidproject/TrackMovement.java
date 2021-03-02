package com.example.androidproject;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.appcompat.app.AppCompatActivity;

public class TrackMovement extends AppCompatActivity implements SensorEventListener {

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

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private boolean accelerometerAvailable;
    private boolean gyroscopeAvailable;

    private float xAcceleration;
    private float yAcceleration;
    private float zAcceleration;
    private float xRotation;
    private float yRotation;
    private float zRotation;



    public void track() {
        this.startSensors();
    }


    //Initialize sensors and their listeners
    private void startSensors() {
        //Instantiate Android SensorManager.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Check if sensors are available.
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED) !=null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
            accelerometerAvailable = true;
        } else {
            //accelerometerError = "No accelerometer data";
            accelerometerAvailable = false;
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) !=null) {
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
            gyroscopeAvailable = true;
        } else {
            //gyroscopeError = "No gyroscope data";
            gyroscopeAvailable = false;
        }

        //Activate sensor event listeners if sensors are available.
        if (accelerometerAvailable && gyroscopeAvailable) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor activeSensor = event.sensor;

        if (activeSensor.getType() == Sensor.TYPE_ACCELEROMETER_UNCALIBRATED) {
            xAcceleration = event.values[0];
            yAcceleration = event.values[1];
            zAcceleration = event.values[2];
        }
        if (activeSensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
            xRotation = event.values[0];
            yRotation = event.values[1];
            zRotation = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void unregisterSensorListerers() {

    }
}
