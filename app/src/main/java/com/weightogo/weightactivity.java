package com.weightogo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import userdatabase.MyDatabaseHelper;
import userdatabase.tables.userWeightTables;

public class weightactivity extends AppCompatActivity {

    public Button cancelButton; //cancelButton
    public Button saveButton;
    public EditText inputWeight; //inputWeight
    public SQLiteDatabase db;

    public void initCancel(){
        cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weightactivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initSave(){
        saveButton = (Button)findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(weightactivity.this);
                db = dbHelper.getWritableDatabase();

                inputWeight = (EditText)findViewById(R.id.inputWeight);
                String weight = inputWeight.getText().toString();
                double weightNumber = Double.parseDouble(weight);
                userWeightTables.addWeight(db, weightNumber);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("weight",inputWeight.getText().toString());
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weightactivity);
        initCancel();
        initSave();
    }
}
