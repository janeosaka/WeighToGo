package com.weightogo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    private static EditText username;
    private static EditText password;
    private static Button loginButton;
    private SharedPreferences sharedPreference;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        loginButton();
    }


    public void loginButton() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        final SharedPreferences.Editor editor = pref.edit();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getString(null, null) == null){
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
                else if (username.getText().toString().equals(pref.getString("user", "no user found")) &&
                       password.getText().toString().equals(pref.getString("pass", null))) {
                    Toast.makeText(loginActivity.this, "Username and password is correct",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(loginActivity.this, "Username and password is NOT correct",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
