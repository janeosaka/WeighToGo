package com.weightogo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.util.ArrayList;

import userdatabase.MyDatabaseHelper;
import userdatabase.tables.userWeightTables;

public class MainActivity extends AppCompatActivity {

    public Button weightButton;
    public Button ccButton;
    public TextView displayWeight;
    public TextView displayAverage;


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

    public Double getAverage() {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Double> HiProfWang = userWeightTables.fetchWeight(db);
        DecimalFormat df2 = new DecimalFormat("#.00");
        double sum = 0.0;
        for (int i = 0; i < HiProfWang.size(); i++) {
            sum += HiProfWang.get(i);
        }
        Double avg = sum / HiProfWang.size();
        df2.format(avg);
        return avg;
    }


    public void displayUserWeight() {
        displayWeight = (TextView) findViewById(R.id.displayWeight);
        displayWeight.setText(getIntent().getStringExtra("weight"));

    }

    public void displayWeightAverage() {

        displayAverage = (TextView) findViewById(R.id.displayAverage);
        displayAverage.setText(getAverage().toString());
        displayAverage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("DROP TABLE " + userWeightTables.TABLE_NAME);

//                db.execSQL("DROP TABLE IF EXISTS "+userWeightTables.TABLE_NAME);

                dbHelper.onCreate(db);
                return true;
            }
        });
    }

    public void buildGraph() {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Double> HiProfWang = userWeightTables.fetchWeight(db);
        GraphView graph = (GraphView) findViewById(R.id.sexbot);
        DataPoint[] dpvals = new DataPoint[HiProfWang.size()];
        for (int i = 0; i < HiProfWang.size(); i++) {
            dpvals[i] = new DataPoint(i + 1, HiProfWang.get(i));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dpvals);

        graph.getViewport().setYAxisBoundsManual(true);

        graph.getViewport().setXAxisBoundsManual(true);

        // enable scrolling
        graph.getViewport().setScrollable(true); // horizontal scrolling

        graph.addSeries(series);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        displayWeight = (TextView) findViewById(R.id.displayWeight);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWeight();
        initCalorieCounter();
        displayUserWeight();
        displayWeightAverage();
        buildGraph();

    }


}