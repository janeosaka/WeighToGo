package com.weightogo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public Button weightButton;
    public Button ccButton;
    public TextView displayWeight;


    public void initWeight() {
        weightButton = (Button) findViewById(R.id.weightButton);
        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, weightactivity.class);
                startActivity(intent);
            }
        });
    }

    public void initCalorieCounter() {
        ccButton = (Button) findViewById(R.id.ccButton);
        ccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, calorieactivity.class);
                startActivity(intent);
            }
        });

    }


    public void displayUserWeight(){
        displayWeight = (TextView)findViewById(R.id.displayWeight);
//
//        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("MYLABEL", "myStringToSave").apply();
//
//        PreferenceManager.getDefaultSharedPreferences(context).getString("MYLABEL", "defaultStringIfNothingFound");
        displayWeight.setText(getIntent().getStringExtra("weight"));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        displayWeight = (TextView)findViewById(R.id.displayWeight);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWeight();
        initCalorieCounter();
        displayUserWeight();



    }
}