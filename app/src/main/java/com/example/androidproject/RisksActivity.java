package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class RisksActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risks_activity);

        Intent intent = getIntent();

        String infoAlco = intent.getStringExtra(MainActivity.EXTRA_ALCOHOL);
        String infoTobacco = intent.getStringExtra(MainActivity.EXTRA_TOBACCO);


        TextView riskText = findViewById(R.id.infoField);
        riskText.setText(infoAlco);

    }

    public void onClick(View v) {


        if (v == findViewById(R.id.btnBack)) {
            Log.d("Note", "Back to main");

            Intent backToMain = new Intent(RisksActivity.this, MainActivity.class);
            startActivity(backToMain);

        }
        if (v == findViewById(R.id.btnToStats)) {
            Log.d("Note", "Going to statistics");

            Intent goToStats = new Intent(RisksActivity.this, StatisticsActivity.class);
            startActivity(goToStats);
        }
    }
}