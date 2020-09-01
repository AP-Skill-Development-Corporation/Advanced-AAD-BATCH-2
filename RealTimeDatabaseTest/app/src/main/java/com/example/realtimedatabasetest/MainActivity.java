package com.example.realtimedatabasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    EditText et_rollno,et_name,et_mobile,et_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_rollno = findViewById(R.id.rollno);
        et_name = findViewById(R.id.name);
        et_mobile = findViewById(R.id.mobileno);
        et_email = findViewById(R.id.email);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    public void save(View view) {
        String rollno = et_rollno.getText().toString();
        String name = et_name.getText().toString();
        String mobile = et_mobile.getText().toString();
        String email = et_email.getText().toString();
        Student s = new Student();
        s.setRollno(rollno);
        s.setName(name);
        s.setMobile(mobile);
        s.setEmail(email);
        reference.child("Student").child(rollno).setValue(s)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,
                                    "Data inserted successfullyy", Toast.LENGTH_SHORT).show();
                            et_rollno.setText("");
                            et_name.setText("");
                            et_mobile.setText("");
                            et_email.setText("");
                        }
                    }
                });



    }

    public void read(View view) {
    }
}