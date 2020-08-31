package com.example.phonenumberauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    EditText et_mobile,et_otp;
    String verficationId;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_mobile = findViewById(R.id.mobilenoEditText);
        et_otp = findViewById(R.id.otpEditText);
        mAuth = FirebaseAuth.getInstance();
    }

    public void submit(View view) {
        String mobile = et_mobile.getText().toString();
        if(mobile.isEmpty() || mobile.length()<10){
            et_mobile.setError("Enter valid mobileno");
            return;
        }
        
        sendVerificationCode(mobile);



        //verifyVerificationCode(otp);

    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+mobile,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String otpCode = phoneAuthCredential.getSmsCode();
                    if(otpCode!=null){
                        et_otp.setText(otpCode);
                        verifyVerificationCode(otpCode);

                    }

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verficationId = s;

                }
            };

    private void verifyVerificationCode(String otpCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verficationId,otpCode);
        mAuth.signInWithCredential(credential).addOnCompleteListener(MainActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,
                                    "Success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,
                                    "Invalid Otp", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}