package com.example.ahujaclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Admin_Available_Appointments extends AppCompatActivity {
    private RecyclerView rv;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private DatabaseReference reference_doc;
    private ArrayList<String> doc_names,doc_id;
    private ArrayAdapter<String> doctor_adapter;
    private TextInputLayout doctor_layout;
    private AutoCompleteTextView doctor_view;
    private String doctor_data, doctor_email;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_appointments);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        doctor_layout = (TextInputLayout) findViewById(R.id.docLayout);
        doctor_view = (AutoCompleteTextView) findViewById(R.id.docTextView);
        doc_names = new ArrayList<>();
        doc_id = new ArrayList<>();
        reference_doc = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
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
                doctor_email = doc_id.get(position);
                getTabs(doctor_data, doctor_email);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getTabs(String name, String email)
    {
        final FragmentPagerAdapterAdmin fragmentPagerAdapter=new FragmentPagerAdapterAdmin(getSupportFragmentManager());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                fragmentPagerAdapter.addFragment(AdminPreviousFragment.getInstance(doctor_email),"Previous", doctor_email);
                fragmentPagerAdapter.addFragment(AdminCurrentFragment.getInstance(doctor_email),"Current", doctor_email);
                viewPager.setAdapter(fragmentPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}