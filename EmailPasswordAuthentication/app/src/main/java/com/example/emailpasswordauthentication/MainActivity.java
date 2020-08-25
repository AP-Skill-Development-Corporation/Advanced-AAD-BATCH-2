package com.example.emailpasswordauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
    }

    public void openaForegetActivity(View view) {
        Intent i = new Intent(this,ForgetPasswordActivity.class);
        startActivity(i);

    }

    public void openaRegistrationActivity(View view) {
        Intent i = new Intent(this,RegistrationActivity.class);
        startActivity(i);
    }
}