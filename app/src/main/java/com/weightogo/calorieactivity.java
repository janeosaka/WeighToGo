package com.weightogo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class calorieactivity extends AppCompatActivity {

    ListView foodList = (ListView)findViewById(R.id.foodList);
    ArrayList<Integer> Values = new ArrayList<>();
    ArrayAdapter<String> adapter;

    public void buildList(){
        ArrayList<String> arrayFood = new ArrayList<>();
        arrayFood.addAll(Arrays.asList(getResources().getStringArray(R.array.array_food)));
        adapter = new ArrayAdapter<>(calorieactivity.this, android.R.layout.simple_list_item_1, arrayFood);
        foodList.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorieactivity);

    }
}
