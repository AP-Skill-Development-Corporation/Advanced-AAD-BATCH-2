package com.example.emailpasswordauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText et_regEmail,et_regpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        et_regEmail = findViewById(R.id.registeremaiid);
        et_regpass = findViewById(R.id.registerpassword);
        mAuth = FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String reg_u = et_regEmail.getText().toString();
        String reg_pass = et_regpass.getText().toString();
        mAuth.createUserWithEmailAndPassword(reg_u,reg_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this,
                            "Registration is done", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void openaLoginPage(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);

    }
}