package com.example.ahujaclinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Patient_MessageAdapter extends RecyclerView.Adapter<Patient_MessageAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Chat> mChat;
    private String imageurl, emailID;

    public Patient_MessageAdapter(Context mContext, List<Chat> mChat, String imageurl, String emailID) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.imageurl = imageurl;
        this.emailID = emailID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new ViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = mChat.get(position);
        if(chat.getType().equals("text")) {
            holder.show_message.setText(chat.getMessage());
            holder.show_time.setText(chat.getTime());
            holder.show_image.setVisibility(View.GONE);
            holder.show_message.setVisibility(View.VISIBLE);
        }
        else{
            Picasso.with(mContext).load(chat.getMessage()).into(holder.show_image);
            holder.show_time.setText(chat.getTime());
            holder.show_message.setVisibility(View.GONE);
            holder.show_image.setVisibility(View.VISIBLE);
        }

        if (!(imageurl.equals("default"))){
            Picasso.with(mContext).load(imageurl).into(holder.profile_image);
        }

        if(position == mChat.size()-1){
            if(chat.isSeen()){
                holder.txt_seen.setText("Seen");
            }
            else {
                holder.txt_seen.setText("Delivered");
            }
        }
        else{
            holder.txt_seen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message, show_time;
        public ImageView profile_image;
        public ImageView show_image;
        public  TextView txt_seen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            show_time = itemView.findViewById(R.id.timeSender);
            profile_image = itemView.findViewById(R.id.profileImage);
            show_image = itemView.findViewById(R.id.messageImage);
            txt_seen=itemView.findViewById(R.id.seenSender);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Patient_Session_Management session = new Patient_Session_Management(mContext);
        String phone = session.getSession();
        if(mChat.get(position).getSender().equals(phone)){
            return MSG_TYPE_RIGHT;
        }
        else{
            return  MSG_TYPE_LEFT;
        }
    }
}
