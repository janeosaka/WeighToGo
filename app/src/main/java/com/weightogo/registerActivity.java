package com.weightogo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerActivity extends AppCompatActivity {

    private EditText userNameText, passwordText;
    private Button registerButton, cancelButton;


    public void initRegisterButton(){
        registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String un = userNameText.getText().toString();
                String pw = passwordText.getText().toString();

                if(!un.equals(" ") && !pw.equals(" ")){
                    SharedPreferences sPref = getSharedPreferences("LoginInfo", 0);
                    //SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor mEditor = sPref.edit();
                    String userString = "userN"+un;
                    String passwordString = "userP"+pw;

                    //String userNameToCheck = sPref.getString(userString, "default");

                    if(!sPref.contains(userString)){
                        mEditor.putString(userString, un);
                        mEditor.commit();
                        mEditor.putString(passwordString, pw);
                        mEditor.commit();
                        mEditor.putInt("numberOfUsers", 1);
                        mEditor.commit();

                        Intent intent = new Intent(registerActivity.this, loginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        Toast toast2 = Toast.makeText(getApplicationContext(), "That user already exists.",
                                Toast.LENGTH_LONG);
                        toast2.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter a username and password.",
                            Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    public void initCancelButton(){
        cancelButton = (Button)findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerActivity.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userNameText = (EditText)findViewById(R.id.userNameInput);
        passwordText = (EditText)findViewById(R.id.passwordInput);
        initRegisterButton();
        initCancelButton();
    }
}
