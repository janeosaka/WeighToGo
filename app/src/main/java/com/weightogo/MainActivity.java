package com.weightogo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import userdatabase.MyDatabaseHelper;
import userdatabase.tables.userWeightTables;

public class MainActivity extends AppCompatActivity {

    public Button weightButton;
    public Button ccButton;
    public TextView displayWeight;
    public TextView displayAverage;
    public TextView displayDifference;

    public void initWeight() {
        weightButton = (Button) findViewById(R.id.weightButton);
        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sPref = getSharedPreferences("oneDayTillNextWeight", Context.MODE_PRIVATE);
                SharedPreferences.Editor mEditor = sPref.edit();

                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                String dateToday = String.valueOf(year)+String.valueOf(month)+String.valueOf(day);
                String dateToCheck = sPref.getString("lastDate", "default");

                if(!dateToCheck.equals(dateToday)){
                    Intent intent = new Intent(MainActivity.this, weightactivity.class);
                    startActivity(intent);
                } else{
                    Toast toast = Toast.makeText(getApplicationContext(), "You have already enterd today's weight.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
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
        String averageValue = String.format("%.2f", getAverage());
        displayAverage.setText(averageValue);
        displayAverage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("DROP TABLE " + userWeightTables.TABLE_NAME);
                dbHelper.onCreate(db);
                return true;
            }
        });
    }

    public void displayWeightDifference(ArrayList<Double> weightArray){
        displayDifference = (TextView) findViewById(R.id.displayDifference);
        String weightDifference = " ";
        if(weightArray.size() > 0){
            double staringWeight = weightArray.get(0);
            double currentWeight = weightArray.get(weightArray.size() - 1);
            double difference = currentWeight - staringWeight ;
            weightDifference = String.valueOf(difference);
            if(difference > 0){
                weightDifference = "+" + weightDifference;
            }
        }
        displayDifference.setText(weightDifference);
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

        graph.getViewport().setYAxisBoundsManual(false);

        if(HiProfWang.size() < 7){
            graph.getViewport().setMinX(1);
        }
        else{
            graph.getViewport().setMinX(HiProfWang.size() - 6);
        }
        graph.getViewport().setMaxX(HiProfWang.size());
        graph.getViewport().setXAxisBoundsManual(true);
        // enable scrolling
        graph.getViewport().setScrollable(true); // horizontal scrolling
        graph.addSeries(series);
        displayWeightDifference(HiProfWang);
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