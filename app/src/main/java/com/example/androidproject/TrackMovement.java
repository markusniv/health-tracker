package com.example.androidproject;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

/**
 *
 */
public class TrackMovement extends AppCompatActivity implements SensorEventListener {

    Context mContext;
    public TrackMovement(Context mContext) {
        this.mContext = mContext;
    }

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;

    //Listener interface to allow calling the updateUI method in MovementActivity
    public interface Listener {
        public void onEvent();
    }

    private Listener mListener;

    public TrackMovement() {
        this.mListener = null;
    }

    /**
     * Listener to get access to updateUI method in MovementActivity
     * @param mListener
     */
    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    private boolean accelerometerAvailable;
    private boolean gyroscopeAvailable;

    private float xAcceleration;
    private float yAcceleration;
    private float zAcceleration;
    private float xRotation;
    private float yRotation;
    private float zRotation;

    /**
     *
     */
    public void track() {
        this.startSensors();
        this.saveData(); //TODO call periodically
    }

    /**
     * Starting sensor services.
     * Checking sensor availability.
     * Initializing relevant sensors.
     * Registering sensor listeners.
     */
    //Initialize sensors and their listeners
    private void startSensors() {
        //Instantiate Android SensorManager.
        sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);

        //Check if sensors are available.
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) !=null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            accelerometerAvailable = true;
        } else {
            Log.d("SENSOR","No accelerometer data");
            accelerometerAvailable = false;
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) !=null) {
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            gyroscopeAvailable = true;
        } else {
            Log.d("SENSOR","No gyroscope data");
            gyroscopeAvailable = false;
        }

        //Activate sensor event listeners if sensors are available.
        if (accelerometerAvailable) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (gyroscopeAvailable) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    /**
     *
     */
    private void saveData() {

    }

    /**
     * Return current sensor data in a float array.
     * @return
     */
    public float[] getData() {
        float[] array = {xAcceleration, yAcceleration, zAcceleration, xRotation, yRotation, zRotation};
        return array;
    }

    /**
     * Sensor data acquisition.
     * Invoking event listener when new sensor data is available.
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            xAcceleration = event.values[0];
            yAcceleration = event.values[1];
            zAcceleration = event.values[2];
        }
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            xRotation = event.values[0];
            yRotation = event.values[1];
            zRotation = event.values[2];
        }

        if (mListener != null) {
            mListener.onEvent();
        }

    }

    /**
     * Default sensor service method. Not used.
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    /**
     * Unregistering sensor listeners when onDestroy is called in MovementActivity
     */
    public void unregisterSensorListeners() {
        if (accelerometerAvailable) {
            sensorManager.unregisterListener(this, accelerometer);
        }
        if (gyroscopeAvailable) {
            sensorManager.unregisterListener(this, gyroscope);
        }
    }
}
