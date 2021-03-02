package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MovementActivity extends AppCompatActivity implements SensorEventListener {

    private TextView motionDetectionX, motionDetectionY, motionDetectionZ;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean sensorAvailable;

    private String xRound;
    private String yRound;
    private String zRound;

    DecimalFormat df = new DecimalFormat("#.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);

        motionDetectionX = findViewById(R.id.motionTextX);
        motionDetectionY = findViewById(R.id.motionTextY);
        motionDetectionZ = findViewById(R.id.motionTextZ);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) !=null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            sensorAvailable = true;
        } else {
            motionDetectionX.setText("No sensor data");
            sensorAvailable = false;
        }

        if(sensorAvailable) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        xRound = df.format(event.values[0]);
        yRound = df.format(event.values[1]);
        zRound = df.format(event.values[2]);

        motionDetectionX.setText(xRound);
        motionDetectionY.setText(yRound);
        motionDetectionZ.setText(zRound);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(sensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }
}