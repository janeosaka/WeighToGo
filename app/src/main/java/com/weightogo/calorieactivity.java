package com.weightogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class calorieactivity extends AppCompatActivity {

    private Button cancelButton;
    private ListView foodListView;
    private EditText searchBox;
    private ArrayAdapter<FoodItem> arrayAdapter;
    private TextView totalCalorieBox;
    private int totalCalories = 0;


    private void initCancelButton(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(calorieactivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }


    private void initFoodListView(){
        String name;
        String calories;
        FileReader fileReader;
        ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
        FoodItem[] foodArray = null;

        try{
            InputStream iStream = getAssets().open("foodTxtFile.txt");
            //
            //DataInputStream dataIO = new DataInputStream(fis);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream, "UTF-8"));

            while((name = bReader.readLine()) != null){
                calories = bReader.readLine();
                FoodItem food = new FoodItem(name, Integer.parseInt(calories));
                foodItems.add(food);
            }

            foodArray = new FoodItem[foodItems.size()];
            for(int i = 0; i < foodItems.size();i++){
                foodArray[i] = foodItems.get(i);
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }


        //ListAdapter listAdapter = new ArrayAdapter<ArrayList>(this, android.R.layout.simple_list_item_1, foodItems);
        //foodListView.setAdapter(listAdapter);
        arrayAdapter = new ArrayAdapter<FoodItem>(this, android.R.layout.simple_list_item_1, foodArray);
        foodListView.setAdapter(arrayAdapter);

        final FoodItem[] finalFoodArray = foodArray;
        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                totalCalories += finalFoodArray[position].getFoodCalories();
                totalCalorieBox.setText(String.valueOf(totalCalories) + " caloreis");

            }
        });
    }

    private  void initSeachBox(){
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (calorieactivity.this).arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorieactivity);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        foodListView = (ListView)findViewById(R.id.foodListView);
        searchBox = (EditText)findViewById(R.id.searchBox);
        totalCalorieBox = (TextView)findViewById(R.id.totalCalorieBox);
        totalCalorieBox.setText(String.valueOf(totalCalories + " calories"));
        initCancelButton();
        initFoodListView();
        initSeachBox();
    }



    public class FoodItem {

        private String name;
        private int calories;

        public FoodItem(String s, int c){
            name = s;
            calories = c;
        }

        public String getFoodName(){
            return name;
        }

        public double getFoodCalories(){
            return calories;
        }

        @Override
        public String toString(){
            return this.name + " " + this.calories;
        }
    }


}
