package com.example.androidproject.dataclasses;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject.activities.MyApplication;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is responsible for acquiring the sensor data from the OS and preparing it for display and saving.
 */
public class TrackMovement extends AppCompatActivity implements SensorEventListener {

    private static final TrackMovement movementInstance = new TrackMovement(MyApplication.getAppContext());

    public static TrackMovement getMovementInstance() {
        return movementInstance;
    }

    private Context mContext;

    /**
     * Association to MovementActivity.
     * @param mContext activity context
     */
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
        this.dataToStore = 0;
    }

    /**
     * Listener for getting data to updateUI method in MovementActivity.
     * @param mListener listening for sensor events.
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

    private float[] currentData;
    private double dataToStore;

    private Timer timer = new Timer();

    /**
     * Called from MovementActivity to start sensor service and to start collecting and saving data.
     */
    public void track() {
        this.startSensors();
        this.saveData();
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
     * Collect raw sensor data in preparation for periodic saving and format it to make it smaller.
     */
    public void collectData() {
        currentData = getData();
        double data = 0;
        for (float f : currentData) {

            data += (double)Math.abs(f) / 100;
            setDataToStore(data);
            //Log.d("COLLECTED", String.valueOf(getDataToStore()));
        }
    }

    public void setDataToStore(double amount) {
        this.dataToStore = amount;
    }

    public double getDataToStore() {
        Log.i("Saved data", String.valueOf(this.dataToStore));
        return this.dataToStore;
    }

    /**
     * Periodically save collected activity data to EventSingleton with timestamps.
     */
    private void saveData() {
        TimerTask movementEvent = new AddMovementTimerTask();
        timer.scheduleAtFixedRate(movementEvent, 10000, 10000);
    }

    /**
     * Get current sensor data.
     * @return current sensor data in a float array.
     */
    public float[] getData() {
        float[] array = {xAcceleration, yAcceleration, zAcceleration, xRotation, yRotation, zRotation};
        return array;
    }

    /**
     * Sensor data acquisition.
     * @param event Invoking event listener when new sensor data is available.
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
