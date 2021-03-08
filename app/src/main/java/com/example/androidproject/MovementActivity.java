package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * MovementActivity class
 *
 */
public class MovementActivity extends AppCompatActivity {

    private TextView xAcc, yAcc, zAcc, xRot, yRot, zRot;

    //For formatting raw sensor data.
    DecimalFormat df = new DecimalFormat("#.##");

    //Array for raw sensor data.
    private float[] dynData;

    TrackMovement movTrack = TrackMovement.getMovementInstance();

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);

        movTrack.setListener(new TrackMovement.Listener() {
            /**
             *Triggers data fetch and realtime UI update when new sensor data is available from TrackMovement.
             */
            @Override
            public void onEvent() {
                updateDynamicUI();
                movTrack.collectData();
            }

        });

        xAcc = findViewById(R.id.xAccText);
        yAcc = findViewById(R.id.yAccText);
        zAcc = findViewById(R.id.zAccText);
        xRot = findViewById(R.id.xRotText);
        yRot = findViewById(R.id.yRotText);
        zRot = findViewById(R.id.zRotText);

    }

    /**
     * Activating movement tracking on button press.
     * @param v
     */
    public void onTrackBtnClick(View v) {
        movTrack.track();
        findViewById(R.id.buttonStartTrackingMovement).setEnabled(false);
    }

    /**
     * Get sensor data from TrackMovement and update display
     */
    public void updateDynamicUI() {
        dynData = movTrack.getData();
        xAcc.setText("X: " + df.format(dynData[0]) + " m/s²");
        yAcc.setText("Y: " + df.format(dynData[1]) + " m/s²");
        zAcc.setText("Z: " + df.format(dynData[2]) + " m/s²");
        xRot.setText("X: " + df.format(dynData[3]) + " rad/s");
        yRot.setText("Y: " + df.format(dynData[4]) + " rad/s");
        zRot.setText("Z: " + df.format(dynData[5]) + " rad/s");
    }

    /**
     * Method call to TrackMovement to unregister sensor listeners on destroy.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        movTrack.unregisterSensorListeners();
    }

}