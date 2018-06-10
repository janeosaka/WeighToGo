package com.weightogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class weightactivity extends AppCompatActivity {

    public Button cancelButton;
    public Button saveButton;

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
        // Complete this save button
        saveButton = (Button)findViewById(R.id.saveButton);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weightactivity);
        initCancel();
    }
}
