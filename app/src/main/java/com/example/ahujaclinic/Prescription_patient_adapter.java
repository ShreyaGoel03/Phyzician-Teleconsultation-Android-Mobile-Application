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
import java.util.List;

public class Prescription_patient_adapter extends RecyclerView.Adapter<Prescription_patient_adapter.ViewHolder> {

    private ArrayList<Get_Prescription_Details> get_prescription_details;
    private Context mContext;

    public Prescription_patient_adapter(ArrayList<Get_Prescription_Details> get_prescription_details, Context mContext) {
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
                Get_Prescription_Details obj=get_prescription_details.get(viewHolder.getAdapterPosition());
                if(obj.getFlag() == 0){
                    Toast.makeText(view.getContext(), "Please Submit feedback First!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(view.getContext(), Feedback.class);
                    intent1.putExtra("email",obj.getText());
                    intent1.putExtra("date",obj.getDate());
                    intent1.putExtra("time",obj.getTime());
                    intent1.putExtra("phone",obj.getPhone());
                    view.getContext().startActivity(intent1);

                }
                else {
                    Intent intent = new Intent(view.getContext(), Presciption_PatientSide.class);
                    intent.putExtra("email", obj.getText());
                    intent.putExtra("date", obj.getDate());
                    intent.putExtra("time", obj.getTime());
                    view.getContext().startActivity(intent);
                }
            }
        });
        return  viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Get_Prescription_Details ld=get_prescription_details.get(position);
        holder.pres.setText("Name: "+ ld.getDr()+"\nDate: "+ld.getDate()+"\nTime "+ld.getTime());
    }

    @Override
    public int getItemCount() {
        return get_prescription_details.size();
    }

    public void filterList(ArrayList<Get_Prescription_Details> filterdNames) {
        this.get_prescription_details=filterdNames;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pres;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pres=itemView.findViewById(R.id.prescriptionTextView);
        }
    }
}
