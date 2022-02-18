package com.example.ahujaclinic;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Appointment_Show_Adapter extends RecyclerView.Adapter<Appointment_Show_Adapter.ViewHolder> {

    private ArrayList<Appointment_notif> appointments;

    public Appointment_Show_Adapter(ArrayList<Appointment_notif> appointments){
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_rv_fragment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment_notif notif = appointments.get(position);
        holder.name.setText("Patient's Name: " + notif.getPname());
        holder.phone.setText("Contact Number: "+ notif.getPhone());
        holder.date.setText("Date: "+ notif.getDate());
        holder.time.setText("Time: "+ notif.getTime());
        if(notif.getAppointment_text().equals("1")){
            holder.status.setTextColor(Color.GREEN);
            holder.status.setText("Payment Verified and Booked!");
        }
        else{
            holder.status.setTextColor(Color.RED);
            holder.status.setText("Cancelled Appointment by Doctor!");
        }

    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void filterList(ArrayList<Appointment_notif> filterdNames) {
        this.appointments = filterdNames;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, date, time, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
