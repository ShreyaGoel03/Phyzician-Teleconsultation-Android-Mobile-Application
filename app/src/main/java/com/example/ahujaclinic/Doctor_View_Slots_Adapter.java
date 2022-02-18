package com.example.ahujaclinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class Doctor_View_Slots_Adapter extends RecyclerView.Adapter<Doctor_View_Slots_Adapter.ViewHolder>{
    private List<DataSnapshot> slots_list;
    private Context mContext;
    private List<String> date_slot;
    private List<Integer> count;

    public Doctor_View_Slots_Adapter(Context mContext, List<DataSnapshot> slots_list, List<String> date_slot, List<Integer> count) {
        this.slots_list = slots_list;
        this.mContext = mContext;
        this.date_slot = date_slot;
        this.count = count;
    }

    @NonNull
    @Override
    public Doctor_View_Slots_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_view_slot_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Doctor_View_Slots_Adapter.ViewHolder holder, int position) {
        String slots_time = slots_list.get(position).getKey();
        Doctor_Slots_RV doctor_slots_rv = new Doctor_Slots_RV(date_slot.get(position), slots_time, count.get(position));
        holder.date.setText("Date: " + doctor_slots_rv.getDate());
        holder.time.setText("Time: " + doctor_slots_rv.getTime());
        holder.slots.setText("Slots Booked: "+doctor_slots_rv.getSlots_booked());
    }

    @Override
    public int getItemCount() {
        return slots_list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView date,time,slots;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateTextView);
            time = itemView.findViewById(R.id.timeTextView);
            slots = itemView.findViewById(R.id.slots_view);
        }
    }
}

