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

/**
 * MovementActivity class
 *
 */
public class MovementActivity extends AppCompatActivity {


    private TextView xAcc, yAcc, zAcc, xRot, yRot, zRot;


    private String xAccRound;
    private String yAccRound;
    private String zAccRound;
    private String xRotRound;
    private String yRotRound;
    private String zRotRound;

    DecimalFormat df = new DecimalFormat("#.##");

    private float[] dynData;

    TrackMovement movTrack = new TrackMovement();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);

        movTrack.setListener(new TrackMovement.Listener() {
            @Override
            public void onEvent() {
                updateDynamicUI();
            }

        });

        xAcc = findViewById(R.id.xAccText);
        yAcc = findViewById(R.id.yAccText);
        zAcc = findViewById(R.id.zAccText);
        xRot = findViewById(R.id.xRotText);
        yRot = findViewById(R.id.yRotText);
        zRot = findViewById(R.id.zRotText);

    }

    public void updateDynamicUI() {
        dynData = movTrack.getData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}