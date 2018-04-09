package com.example.tcsexam.trainingday8json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    EditText registrationName, registrationMobile, registrationEmail, registrationUsername, registrationPassword;
    String name, mobile, email, username, password;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registrationName = findViewById(R.id.registration_name);
        registrationMobile = findViewById(R.id.registration_mobile);
        registrationEmail = findViewById(R.id.registration_email);
        registrationUsername = findViewById(R.id.registration_username);
        registrationPassword = findViewById(R.id.registration_password);

        register = findViewById(R.id.register_user_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getTextsFromEditText()){
                    //TODO:  Registration here
                    String status = "registration";
                    BackgroundWorker backgroundWorker = new BackgroundWorker(RegistrationActivity.this);
                    backgroundWorker.execute(status, name, mobile, email, username, password);
                }
            }
        });

    }

    private boolean getTextsFromEditText(){
        if (registrationName.getText().toString().trim().isEmpty()){
            registrationName.setError("Enter Name");
            return false;
        }
        else
            name = registrationName.getText().toString().trim();

        if (registrationMobile.getText().toString().trim().isEmpty()) {
            registrationMobile.setError("Enter Mobile number");
            return false;
        }
        else
            mobile = registrationMobile.getText().toString().trim();

        if (registrationEmail.getText().toString().trim().isEmpty()){
            registrationName.setError("Enter Email");
            return false;
        }
        else
            email = registrationEmail.getText().toString().trim();

        if (registrationUsername.getText().toString().trim().isEmpty()){
            registrationName.setError("Enter Username");
            return false;
        }
        else
            username = registrationUsername.getText().toString().trim();

        if (registrationPassword.getText().toString().trim().isEmpty()){
            registrationName.setError("Enter Password");
            return false;
        }
        else
            password = registrationPassword.getText().toString().trim();

        return true;
    }
}
