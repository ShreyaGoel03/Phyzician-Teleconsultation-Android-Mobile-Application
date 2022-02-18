package com.example.ahujaclinic;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class Patient_Display_Doctor extends AppCompatActivity {
    private Button book_app;
    private TextView doctor_name, doctor_spec, doctor_experience, doctor_fee, doctor_slots, doctor_about, emailid;
    private ImageView doctor_image;
    private Doctor_Images doctor_images;
    private DatabaseReference reference_doctor, reference_booking;
    private int start, end;
    private Button prescription, chat_btn;
    private String encoded_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_doctor);
        doctor_name = (TextView) findViewById(R.id.doc_name_textview);
        doctor_spec = (TextView) findViewById(R.id.doc_spec_textview);
        doctor_experience = (TextView) findViewById(R.id.Exp_textview_val);
        doctor_fee = (TextView) findViewById(R.id.consult_charges_text_val);
        doctor_slots = (TextView) findViewById(R.id.Available_text_val);
        doctor_about = (TextView) findViewById(R.id.about_doctor);
        emailid = (TextView) findViewById(R.id.email_text_val);
        chat_btn = (Button) findViewById(R.id.chat_button);
        doctor_image = (ImageView) findViewById(R.id.imageView_doc_display);

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

        String date_val = DateFormat.format("dd MMM yyyy", startDate).toString();

        book_app = (Button) findViewById(R.id.book_button);
        String email = getIntent().getSerializableExtra("Email ID").toString();
        String name = getIntent().getSerializableExtra("Doctors Name").toString();
        String speciality = getIntent().getSerializableExtra("Speciality").toString();
        encoded_email = email.replace(".", ",");
        reference_doctor = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Data");
        reference_booking = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Chosen_Slots");
        emailid.setText("Email: " + email);
        DatabaseReference reference_details = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Details");
        reference_details.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    Patient_Session_Management session = new Patient_Session_Management(Patient_Display_Doctor.this);
                    String phone = session.getSession();

                    if (snapshot.child(encoded_email).exists()) {

                        if (snapshot.child(encoded_email).child(phone).exists()) {
                            chat_btn.setVisibility(View.VISIBLE);
                        } else {
                            chat_btn.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        chat_btn.setVisibility(View.INVISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference_doctor.child(encoded_email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    doctor_name.setText(datasnapshot.child("name").getValue(String.class));
                    doctor_spec.setText(datasnapshot.child("type").getValue(String.class));
                    doctor_about.setText(datasnapshot.child("desc").getValue(String.class));
                    doctor_experience.setText(datasnapshot.child("experience").getValue(String.class) + " years");
                    doctor_fee.setText("Rs. " + datasnapshot.child("fees").getValue(String.class));
                    doctor_images = datasnapshot.child("doc_pic").getValue(Doctor_Images.class);
                    if (doctor_images != null) {
                        Picasso.with(Patient_Display_Doctor.this).load(doctor_images.getUrl()).into(doctor_image);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        doctor_slots.setText("");
        reference_booking.child(encoded_email).child(date_val).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String time = "";
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        start = Integer.parseInt(dataSnapshot.getKey().split(" - ", 5)[0]);
                        end = Integer.parseInt(dataSnapshot.getKey().split(" - ", 5)[1]);
                        time = time + String.format("%d:00 -%d:00", start, end) + "\n";

                    }
                    doctor_slots.setText(time);
                } else {
                    doctor_slots.setText("Doctor is not available today!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        book_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Patient_Display_Doctor.this, Calender.class);
                Intent intent = new Intent(Patient_Display_Doctor.this, Patient_Booking_Appointments.class);
                intent.putExtra("Email ID", email);
                startActivity(intent);
            }
        });
    }

    public void current_prescription(View view) {
        Intent intent = new Intent(Patient_Display_Doctor.this, Patient_side_prescription_recycler.class);
        intent.putExtra("email", encoded_email);
        intent.putExtra("dr_name", doctor_name.getText().toString().trim());
        startActivity(intent);
    }

    public void open_chat(View view) {
        Intent intent = new Intent(Patient_Display_Doctor.this, Patient_MessageActivity.class);
        intent.putExtra("email", encoded_email);
        startActivity(intent);
    }
}