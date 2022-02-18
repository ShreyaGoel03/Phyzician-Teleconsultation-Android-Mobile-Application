package com.example.ahujaclinic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Appointments_Adapter extends RecyclerView.Adapter<Appointments_Adapter.ViewHolder> {

    private List<Appointment_notif> appointments_notif;
    private Context mContext;

    public Appointments_Adapter(List<Appointment_notif> appointments_notif, Context mContext) {
        this.appointments_notif= appointments_notif;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_doc,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Appointment_notif obj=appointments_notif.get(viewHolder.getAdapterPosition());
                String date=obj.getDate();
                String time=obj.getTime();
                String name=obj.getPname();
                String Questions=obj.getQuestions();
                String phone=obj.getPhone();

                Intent intent=new Intent(view.getContext(),Patient_Details_Doctors_Side.class);
                intent.putExtra("date",date);
                intent.putExtra("time",time);
                intent.putExtra("name",name);
                intent.putExtra("Questions",Questions);
                intent.putExtra("phone",phone);

                view.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment_notif ld=appointments_notif.get(position);
        holder.appointment.setText(String.format("Patient's Name: %s\nDate: %s\nTime: %s\nContact Number: %s\n", ld.getPname(), ld.getDate(), ld.getTime(), ld.getPhone()));
    }

    @Override
    public int getItemCount() {
        return appointments_notif.size();
    }

    public void filterList(ArrayList<Appointment_notif> filterdNames) {
        this.appointments_notif=filterdNames;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView appointment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appointment=(TextView) itemView.findViewById(R.id.appointmentTextView);

        }
    }
}
