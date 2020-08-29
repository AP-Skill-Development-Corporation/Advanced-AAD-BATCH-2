package com.example.emailpasswordauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class WecomeActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wecome);
        mAuth = FirebaseAuth.getInstance();
    }

    public void logout(View view) {
        mAuth.signOut();
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);

    }
}