package com.example.ahujaclinic;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Admin_UserAdapter extends RecyclerView.Adapter<Admin_UserAdapter.ViewHolder> {
    private Context mContext;
    private List<Patient_Details> mUsers;
    private String email;


    public Admin_UserAdapter(Context mContext, List<Patient_Details> mUsers, String email) {
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.email = email;
    }

    @NonNull
    @Override
    public Admin_UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new Admin_UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_UserAdapter.ViewHolder holder, int position) {
        Patient_Details detail = mUsers.get(position);
        String info = detail.getName() + " ("+detail.getPhone()+")";
        holder.username.setText(info);
        holder.profile_image.setImageResource(R.drawable.patient_dp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Admin_MessageActivity.class);
                intent.putExtra("phone", detail.getPhone());
                intent.putExtra("email", email);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView profile_image;
        private ImageView img_on;
        private ImageView img_off;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.userName);
            profile_image = itemView.findViewById(R.id.profileImage);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
        }
    }
}
