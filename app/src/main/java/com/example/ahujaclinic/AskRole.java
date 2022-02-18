package com.example.ahujaclinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AskRole extends AppCompatActivity {

    private ImageView doctorButton,patientButton;
    private TextView doctorview, patientview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_role);
        doctorButton = findViewById(R.id.doctorLogin);
        patientButton = findViewById(R.id.patientLogin);
        doctorview = findViewById(R.id.doctortextView);
        patientview = findViewById(R.id.PatienttextView2);

        doctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDoctorSession();
            }
        });

        doctorview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDoctorSession();
            }
        });

        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPatientSession();
            }
        });

        patientview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPatientSession();
            }
        });
    }

    private void checkPatientSession() {

        Patient_Session_Management session_management = new Patient_Session_Management(AskRole.this);
        String isUserLoggedin =session_management.getSession();
        if(!isUserLoggedin.equals("-1")){
            moveToPatientActivity();
        }
        else{
            Intent intent =new Intent(AskRole.this, PatientLogin.class);
            startActivity(intent);
        }
    }

    private void checkDoctorSession() {

        Doctors_Session_Mangement doctors_session_mangement=new Doctors_Session_Mangement(AskRole.this);
        String isDoctorLoggedin[] =doctors_session_mangement.getDoctorSession();
        if(!isDoctorLoggedin[0].equals("-1")){
            moveToDoctorActivity();
        }
        else {
            startActivity(new Intent(AskRole.this,DoctorsLogin.class));
        }
    }

    private void moveToDoctorActivity() {
        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(AskRole.this);
        String type[] = doctors_session_mangement.getDoctorSession();


        if(type[1].equals("Doctor")){
            Intent intent = new Intent(AskRole.this, Doctors.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if (type[1].equals("Admin")){
            Intent intent = new Intent(AskRole.this, Admin_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void moveToPatientActivity() {
        Intent intent = new Intent(AskRole.this, Patient.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}