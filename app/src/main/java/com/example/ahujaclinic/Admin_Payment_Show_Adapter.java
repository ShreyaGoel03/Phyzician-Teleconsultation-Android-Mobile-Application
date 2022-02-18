package com.example.ahujaclinic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Admin_Payment_Show_Adapter extends RecyclerView.Adapter<Admin_Payment_Show_Adapter.ViewHolder> {

    private ArrayList<Admin_Payment_Class> payments;

    public Admin_Payment_Show_Adapter(ArrayList<Admin_Payment_Class> payments){
        this.payments = payments;
    }
    @NonNull
    @Override
    public Admin_Payment_Show_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_payment_rv, parent, false);
        Admin_Payment_Show_Adapter.ViewHolder viewHolder = new Admin_Payment_Show_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_Payment_Show_Adapter.ViewHolder holder, int position) {
        Admin_Payment_Class p_class = payments.get(position);
        holder.name.setText(p_class.getName() + ": " +p_class.getPhone());
        holder.phone.setText("Transaction ID: "+p_class.getTransaction());
        holder.email.setText("Booked for: "+p_class.getDname());
        holder.date.setText("Date: "+p_class.getDate());
        holder.time.setText("Time: "+p_class.getTime());
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public void filterList(ArrayList<Admin_Payment_Class> filterdNames) {
        this.payments=filterdNames;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, date, time, email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            email = (TextView) itemView.findViewById(R.id.email);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
