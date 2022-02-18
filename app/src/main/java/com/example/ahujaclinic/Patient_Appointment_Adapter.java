package com.example.ahujaclinic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Patient_Appointment_Adapter extends RecyclerView.Adapter<Patient_Appointment_Adapter.ViewHolder>{

    private List<Admin_Payment_Class> payments;

    public Patient_Appointment_Adapter(List<Admin_Payment_Class> payments) {
        this.payments = payments;
    }

    @NonNull
    @Override
    public Patient_Appointment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_appointments_rv,parent,false);
        Patient_Appointment_Adapter.ViewHolder viewHolder=new Patient_Appointment_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Patient_Appointment_Adapter.ViewHolder holder, int position) {
        Admin_Payment_Class admin_payment_class = payments.get(position);
        holder.name.setText("Booked For: "+ admin_payment_class.getDname());
        holder.transaction.setText("Transaction ID: "+ admin_payment_class.getTransaction());
        holder.date.setText("Date: "+ admin_payment_class.getDate());
        holder.time.setText("Time: "+admin_payment_class.getTime());
        if(admin_payment_class.getPayment() == 0){
            holder.payment.setText("Payment On Progress");
        }
        else if(admin_payment_class.getPayment() == 1){
            holder.payment.setText("Payment Done!");
            holder.payment.setTextColor(Color.GREEN);
        }
        else{
            holder.payment.setText("Incorrect ID. Payment Declined!");
        }
        if(admin_payment_class.getStatus() == 2){
            holder.cancel.setText("Appointment Cancelled by Doctor!");
            holder.cancel.setTextColor(Color.RED);
        }
        else if(admin_payment_class.getStatus() == 1){
            holder.cancel.setText("Appointment Completed!");
        }
        else{
            holder.cancel.setText("Upcoming Appointment!");
        }
        if(admin_payment_class.getPayment()==1 && admin_payment_class.getStatus()==0) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String date_val = admin_payment_class.getDate();
                    String chosen_time = admin_payment_class.getTime();

                    Calendar beginTime = Calendar.getInstance();
                    String[] monthName = {"Jan", "Feb",
                            "Mar", "Apr", "May", "Jun", "Jul",
                            "Aug", "Sep", "Oct", "Nov",
                            "Dec"};
                    String[] date = date_val.split(" ", 5);
                    String[] time = chosen_time.split(" - ", 5);
                    String[] time1 = time[0].split(":", 5);
                    String[] time2 = time[1].split(":", 5);
                    long startMillis = 0;
                    long endMillis = 0;
                    int index = Arrays.binarySearch(monthName, date[1]);
                    beginTime.set(Integer.parseInt(date[2]), index + 1, Integer.parseInt(date[0]), Integer.parseInt(time1[0]), Integer.parseInt(time1[1]));
                    startMillis = beginTime.getTimeInMillis();
                    Calendar endTime = Calendar.getInstance();
                    endTime.set(Integer.parseInt(date[2]), index + 1, Integer.parseInt(date[0]), Integer.parseInt(time2[0]), Integer.parseInt(time2[1]));
                    endMillis = endTime.getTimeInMillis();
                    Toast.makeText(holder.itemView.getContext(), "Slot Booked! :)", Toast.LENGTH_SHORT).show();
                    Calendar calendarEvent = Calendar.getInstance();
                    Intent i = new Intent(Intent.ACTION_EDIT);
                    i.setType("vnd.android.cursor.item/event");
                    i.putExtra("beginTime", startMillis);
                    i.putExtra("allDay", false);
                    i.putExtra("endTime", endMillis);
                    //    i.putExtra("description", question_data);
                    i.putExtra("hasalarm", "true");
                    i.putExtra("location", "Online Consultation");
                    i.putExtra("title", "Phyzician Teleconsultation Appointment");
                    holder.itemView.getContext().startActivity(i);

                }
            });
        }
        DatabaseReference reference = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Data");
        String email = admin_payment_class.getEmail().replace(".", ",");
        reference.child(email).child("type").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.speciality.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public void filterList(ArrayList<Admin_Payment_Class> filterdNames) {
        this.payments = filterdNames;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, speciality, transaction, cancel, date, time, payment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            speciality = (TextView) itemView.findViewById(R.id.speciality);
            transaction = (TextView) itemView.findViewById(R.id.transaction);
            payment = (TextView) itemView.findViewById(R.id.payment);
            cancel = (TextView) itemView.findViewById(R.id.cancel);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
