package com.example.realtimedatabasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {
    RecyclerView rv;
    FirebaseDatabase database;
    DatabaseReference reference;
    List<Student> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        rv = findViewById(R.id.recycler);
        list = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        reference.child("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Student s = dataSnapshot.getValue(Student.class);;
                    Log.i("DATA",s.getName());
                    Log.i("DATA",s.getRollno());
                    list.add(s);
                }
                Log.i("SIZE",""+list.size());
                rv.setLayoutManager(new LinearLayoutManager(DataActivity.this));;
                MyAdapter adapter = new MyAdapter(DataActivity.this,list);
                rv.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}