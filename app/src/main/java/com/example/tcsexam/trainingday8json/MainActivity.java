package com.example.tcsexam.trainingday8json;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.logging.LoggingMXBean;

public class MainActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button login;
    TextView registrationPageLink;
    String username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        login = findViewById(R.id.log_in);
        registrationPageLink = findViewById(R.id.register_here);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getTextsFromEditText()){
                    //TODO: LOGIN HERE

                    String status = "login";
                    BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
                    backgroundWorker.execute(status,username,password);
                }
            }
        });

        registrationPageLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }

    private boolean getTextsFromEditText(){
        if (loginUsername.getText().toString().trim().isEmpty())
            return false;
        else
            username = loginUsername.getText().toString().trim();
        if (loginPassword.getText().toString().trim().isEmpty())
            return false;
        else
            password = loginPassword.getText().toString().trim();
        return true;
    }

}
