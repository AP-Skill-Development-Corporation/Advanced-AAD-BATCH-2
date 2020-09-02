package com.example.realtimedatabasetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context ct;
    List<Student> myList;
    public MyAdapter(DataActivity dataActivity, List<Student> list) {
        ct = dataActivity;
        myList = list;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.tv_roll.setText(myList.get(position).getRollno());
        holder.tv_name.setText(myList.get(position).getName());
        holder.tv_mobile.setText(myList.get(position).getMobile());
        holder.tv_email.setText(myList.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_roll,tv_name,tv_mobile,tv_email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_roll = itemView.findViewById(R.id.rollnoTextview);
            tv_name = itemView.findViewById(R.id.nameTextView);
            tv_mobile = itemView.findViewById(R.id.mobileTextView);
            tv_email = itemView.findViewById(R.id.emailTextView);
        }
    }
}
