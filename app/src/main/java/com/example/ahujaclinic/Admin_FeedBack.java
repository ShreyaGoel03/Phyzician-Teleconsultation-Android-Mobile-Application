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

public class Admin_FeedBack extends AppCompatActivity {

    private DatabaseReference reference_doc, reference;
    private ArrayList<String> doc_names,doc_id;
    private ArrayAdapter<String> doctor_adapter;
    private TextInputLayout doctor_layout;
    private AutoCompleteTextView doctor_view;
    private String doctor_data, doctor_email, email;
    private RecyclerView recyclerView;
    private ArrayList<String> phone_num, dates, time, email_val,name_val;
    private Admin_FeedBack_Adapter admin_feedBack_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feedback);
        doctor_layout = (TextInputLayout) findViewById(R.id.docLayout);
        doctor_view = (AutoCompleteTextView) findViewById(R.id.docTextView);
        doc_names = new ArrayList<>();
        doc_id = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        reference_doc = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        reference = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Feedback");
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
                email_val = new ArrayList<>();
                name_val = new ArrayList<>();
                phone_num = new ArrayList<>();
                dates = new ArrayList<>();
                time = new ArrayList<>();

                reference.child(email).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        email_val = new ArrayList<>();
                        name_val = new ArrayList<>();
                        phone_num = new ArrayList<>();
                        dates = new ArrayList<>();
                        time = new ArrayList<>();
                        if(snapshot.exists()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                for(DataSnapshot snapshot2: snapshot1.getChildren()){
                                    for(DataSnapshot snapshot3: snapshot2.getChildren()){
                                        email_val.add(email);
                                        name_val.add(doc_names.get(position));
                                        phone_num.add(snapshot1.getKey());
                                        dates.add(snapshot2.getKey());
                                        time.add(snapshot3.getKey());
                                    }
                                }
                            }
                            admin_feedBack_adapter = new Admin_FeedBack_Adapter(email_val, name_val, dates, time, phone_num);
                            recyclerView.setAdapter(admin_feedBack_adapter);
                        }
                        else{
                            admin_feedBack_adapter = new Admin_FeedBack_Adapter(email_val, name_val, dates, time, phone_num);
                            recyclerView.setAdapter(admin_feedBack_adapter);
                            Toast.makeText(Admin_FeedBack.this, "Doctor has no Current Feedbacks", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

}
