package com.weightogo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    private EditText userNameField;
    private EditText passwordField;
    private Button loginButton;
    private Button registerButton;
    private CheckBox rememberCheckBox;

    //private SharedPreferences sharedPreference;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        userNameField = (EditText) findViewById(R.id.userNameInput);
        passwordField = (EditText) findViewById(R.id.passwordInput);
        registerButton = (Button) findViewById(R.id.newUserButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        rememberCheckBox = (CheckBox) findViewById(R.id.rememberCheckBox);
        SharedPreferences loginPref = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);

        String unString = loginPref.getString("RememberedUsername", "default");
        String pwString = loginPref.getString("RememberedPassword", "default");

        if(!unString.equals("default") && !pwString.equals("default")){
            userNameField.setText(unString);
            passwordField.setText(pwString);
        }
        initRegisterButton();
        loginButton();


    }

    public void initRegisterButton() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });
    }


    public void loginButton() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences loginPref = getSharedPreferences("LoginInfo", 0);
                //final SharedPreferences loginPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = loginPref.edit();

                String un = userNameField.getText().toString();
                String pw = passwordField.getText().toString();

                String unString = "userN" + un;
                String pwString = "userP" + pw;

                String unCheck = loginPref.getString(unString, "default");

                if(!unCheck.equals("default")){
                    String pwCheck = loginPref.getString(pwString, "default");
                    if(pwCheck.equals(pw)){
                        if(rememberCheckBox.isChecked()){
                            editor.putString("RememberedUsername", un);
                            editor.commit();
                            editor.putString("RememberedPassword", pw);
                            editor.commit();
                        }
                        else{
                            editor.putString("RememberedUsername", " ");
                            editor.commit();
                            editor.putString("RememberedPassword", " ");
                            editor.commit();
                        }
                        //Intent intent2 = new Intent(loginActivity.this, MainActivity.class);
                        Intent intent2 = new Intent(loginActivity.this, MainActivity.class);
                        startActivity(intent2);
                        finish();

                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Incorrect credentials", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } else{
                    Toast toast = Toast.makeText(getApplicationContext(), "That user does not exist.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}


/*
* if(loginPref.getString(null, null) == null){
                    //if no username and password found, store it
                    editor.putString("user", String.valueOf(username));
                    editor.putString("pass", String.valueOf(password));
                    //save
                    editor.commit();
                    //new user
                    Toast.makeText(loginActivity.this, "New user, welcome",
                            Toast.LENGTH_SHORT).show();
                    //after go to main activity
                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (username.getText().toString().equals(loginPref.getString("user", "no user found")) &&
                       password.getText().toString().equals(loginPref.getString("pass", null))) {
                    Toast.makeText(loginActivity.this, "Username and password is correct",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(loginActivity.this, "Username and password is NOT correct",
                            Toast.LENGTH_SHORT).show();
                }
* */