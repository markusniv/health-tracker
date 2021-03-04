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

public class MovementActivity extends AppCompatActivity {

    private TextView xAcc, yAcc, zAcc, xRot, yRot, zRot;


    private String xAccRound;
    private String yAccRound;
    private String zAccRound;
    private String xRotRound;
    private String yRotRound;
    private String zRotRound;

    DecimalFormat df = new DecimalFormat("#.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);

        xAcc = findViewById(R.id.xAccText);
        yAcc = findViewById(R.id.yAccText);
        zAcc = findViewById(R.id.zAccText);
        xRot = findViewById(R.id.xRotText);
        yRot = findViewById(R.id.yRotText);
        zRot = findViewById(R.id.zRotText);

    }

    public void updateUI() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}