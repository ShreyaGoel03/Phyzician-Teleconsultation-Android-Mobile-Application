package com.example.ahujaclinic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Admin_ChatDisplay extends AppCompatActivity {

    private DatabaseReference reference_doc, reference;
    private ArrayList<String> doc_names,doc_id;
    private ArrayAdapter<String> doctor_adapter;
    private TextInputLayout doctor_layout;
    private AutoCompleteTextView doctor_view;
    private String doctor_data, doctor_email, email;
    private RecyclerView recyclerView;
    private List<String> usersList,status_val;
    private List<Patient_Details> mUsers;
    private ArrayList<String> phone_num;
    private Admin_UserAdapter adminUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chat);
        doctor_layout = (TextInputLayout) findViewById(R.id.docLayout);
        doctor_view = (AutoCompleteTextView) findViewById(R.id.docTextView);
        doc_names = new ArrayList<>();
        doc_id = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        reference_doc = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        reference = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Chats");
        reference_doc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doc_names = new ArrayList<>();
                doc_id = new ArrayList<>();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    if((snapshot1.child("user_type").getValue(String.class)).equals("Doctor")) {
                        doc_names.add(snapshot1.child("name").getValue(String.class));
                        doc_id.add(snapshot1.getKey());
                    }
                }
                doctor_adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_gender, doc_names);
                doctor_view.setAdapter(doctor_adapter);
                doctor_view.setThreshold(1);
                doctor_data = "";
                doctor_email = "";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        doctor_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doctor_data = doctor_adapter.getItem(position);
                email = doc_id.get(position);
                mUsers = new ArrayList<>();
                usersList = new ArrayList<>();
                reference = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Chats");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        usersList.clear();
                        if(snapshot.exists()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                Chat chat = snapshot1.getValue(Chat.class);
                                if (chat.getSender().equals(email)) {
                                    usersList.add(chat.getReceiver());
                                }
                                if (chat.getReceiver().equals(email)) {
                                    usersList.add(chat.getSender());
                                }
                            }
                            readChats(email);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
    private void readChats(String email) {
        if(usersList.size() == 0){
            mUsers.clear();
            Toast.makeText(Admin_ChatDisplay.this, "Doctor has no Current Chats", Toast.LENGTH_SHORT).show();
            adminUserAdapter = new Admin_UserAdapter(Admin_ChatDisplay.this, mUsers, email);
            recyclerView.setAdapter(adminUserAdapter);
        }
        else {
            reference = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Details");
            reference.child(email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mUsers.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Patient_Details details = snapshot1.getValue(Patient_Details.class);
                        mUsers.add(details);
                    }

                    if (mUsers.size() == 0) {
                        Toast.makeText(Admin_ChatDisplay.this, "Doctor has no Current Chats", Toast.LENGTH_SHORT).show();
                    } else {
                        adminUserAdapter = new Admin_UserAdapter(Admin_ChatDisplay.this, mUsers, email);
                        recyclerView.setAdapter(adminUserAdapter);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}
