package com.example.ahujaclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class Doctor_Side_Previous_Prescriptions extends AppCompatActivity {
    private RecyclerView rv;
    private String[] gender;
    private String email,dname;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference prescription_doctor;
    private ArrayAdapter<String> gender_adapter;
    private ArrayList<Get__Previous_Prescription_Details> presc;
    private Doctor_Previous_Prescription_Adapter adapter;

    String name,phone,date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__side__previous__prescriptions);

        rv=findViewById(R.id.recycler_previous_prescription);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        presc = new ArrayList<>();

        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(this);
        email = doctors_session_mangement.getDoctorSession()[0].replace(".",",");
        name = getIntent().getStringExtra("pname");
        phone = getIntent().getStringExtra("phone");

        prescription_doctor = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Prescription_By_Doctor");
        prescription_doctor.child(email).child(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                presc = new ArrayList<>();
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        date=dataSnapshot.getKey();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            time=dataSnapshot1.getKey();
                            Get__Previous_Prescription_Details gpd= new Get__Previous_Prescription_Details(name,date,time,phone);
                            presc.add(gpd);

                        }


                    }
                    adapter = new Doctor_Previous_Prescription_Adapter(presc, Doctor_Side_Previous_Prescriptions.this);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}