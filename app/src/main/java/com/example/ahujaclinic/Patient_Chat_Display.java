package com.example.ahujaclinic;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Patient_Chat_Display extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Patient_UserAdapter patientUserAdapter;
    private List<Users> mUsers;
    private DatabaseReference reference,ref_doctor;
    private List<String> usersList;
    private FirebaseUser fuser;
    private String phone;
    private TextView chatview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_display_chats);
        chatview = (TextView) findViewById(R.id.textChat);
        recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Patient_Session_Management session = new Patient_Session_Management(Patient_Chat_Display.this);
        phone = session.getSession();

    }

    private void readChats() {
        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){

                    Users details = snapshot1.getValue(Users.class);

                    for (String id : usersList){

                        String val = details.getEmail().replace(".",",");
                        if(val.equals(id)){
                            if(mUsers.size()!=0){
                                int flag = 0;
                                for (int i=0; i<mUsers.size(); i++){
                                    if(details.getEmail().equals(mUsers.get(i).getEmail())){
                                        flag = 1;
                                        break;
                                    }
                                }
                                if(flag == 0){
                                    mUsers.add(details);
                                }

                            }
                            else{
                                mUsers.add(details);
                            }
                        }
                    }
                }

                patientUserAdapter = new Patient_UserAdapter(Patient_Chat_Display.this, mUsers, true);
                recyclerView.setAdapter(patientUserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void status(String status){
        reference = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Details").child(phone);
        reference.child("status").setValue(status);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                int unread = 0;
                if(snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Chat chat = snapshot1.getValue(Chat.class);
                        if (chat.getSender().equals(phone)) {
                            usersList.add(chat.getReceiver());
                        }
                        if (chat.getReceiver().equals(phone)) {
                            usersList.add(chat.getSender());
                        }
                        if(chat.getReceiver().equals(phone) && !chat.isSeen()){
                            unread++;
                        }
                    }
                    if(unread != 0) {
                        chatview.setText("Your Chats (" + unread + ")");
                    }

                    readChats();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}
