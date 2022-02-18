package com.example.ahujaclinic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Doctor_Previous_Prescription_Adapter extends RecyclerView.Adapter<Doctor_Previous_Prescription_Adapter.ViewHolder> {
    private ArrayList<Get__Previous_Prescription_Details> get_prescription_details;
    private Context mContext;

    public Doctor_Previous_Prescription_Adapter(ArrayList<Get__Previous_Prescription_Details> get_prescription_details, Context mContext) {
        this.get_prescription_details = get_prescription_details;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_data_patient, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Get__Previous_Prescription_Details obj=get_prescription_details.get(viewHolder.getAdapterPosition());
                Intent intent1 = new Intent(view.getContext(), Doctors_Show_Previous_Prescription_Doctors.class);
                intent1.putExtra("phone",obj.getPhone_no());
                intent1.putExtra("date",obj.getDate());
                intent1.putExtra("time",obj.getTime());
                view.getContext().startActivity(intent1);
            }
        });
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Get__Previous_Prescription_Details obj=get_prescription_details.get(position);
        holder.p.setText("Data: "+ obj.getDate()+"\n"+"Time: "+obj.getTime());
    }

    @Override
    public int getItemCount() {
        return get_prescription_details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView p;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            p=itemView.findViewById(R.id.prescriptionTextView);
        }
    }
}
