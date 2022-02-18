package com.example.ahujaclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doctors_View_Profile extends AppCompatActivity {
    private TextView name,gender,email,speciality,bio,experience, fees;
    private String email_id;
    private ImageView update,sign;
    private CircleImageView circle_image;
    private Doctor_Images doctor_images, sign_images;
    private DatabaseReference reference_user, reference_doctor;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_profile);
        progressBar=(ProgressBar) findViewById(R.id.progressbar_view_profile);
        name = (TextView) findViewById(R.id.name);
        gender = (TextView) findViewById(R.id.gender);
        email = (TextView) findViewById(R.id.email);
        experience = (TextView) findViewById(R.id.experience);
        speciality = (TextView) findViewById(R.id.speciality);
        bio = (TextView) findViewById(R.id.bio);
        fees = (TextView) findViewById(R.id.consultation);
        update = (ImageView) findViewById(R.id.imageView5);
        sign = (ImageView) findViewById(R.id.signImage);
        circle_image = (CircleImageView) findViewById(R.id.profileImage);

        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(this);
        email_id = doctors_session_mangement.getDoctorSession()[0].replace(".",",");

        progressBar.setVisibility(View.VISIBLE);
        reference_doctor = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Data");
        reference_user = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        reference_user.child(email_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    name.setText(snapshot.child("name").getValue(String.class));
                    email.setText(snapshot.child("email").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference_doctor.child(email_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    gender.setText(datasnapshot.child("gender").getValue(String.class));
                    speciality.setText(datasnapshot.child("type").getValue(String.class));
                    bio.setText(datasnapshot.child("desc").getValue(String.class));
                    experience.setText(datasnapshot.child("experience").getValue(String.class)+ " years");
                    fees.setText("Rs. " + datasnapshot.child("fees").getValue(String.class));
                    doctor_images = datasnapshot.child("doc_pic").getValue(Doctor_Images.class);
                    sign_images = datasnapshot.child("sign_pic").getValue(Doctor_Images.class);
                    if(doctor_images != null) {
                        Picasso.with(Doctors_View_Profile.this).load(doctor_images.getUrl()).into(circle_image);
                    }
                    if(sign_images != null){
                        Picasso.with(Doctors_View_Profile.this).load(sign_images.getUrl()).into(sign);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Doctors_View_Profile.this, Doctors_Update_Profile.class));
            }
        });

    }
}
