package com.example.ahujaclinic;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class Presciption_PatientSide extends AppCompatActivity {
    private EditText name,age,address,height,weight,consultationType,labReport,diagnosis,relevant,exam,instruct ,rx;
    private EditText selectDate;
    private String finalDate, message;
    private String[] gender;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference prescription_doctor,doctor;
    private Button send_pres;
    private DatePickerDialog.OnDateSetListener setListener;
    private ArrayAdapter<String> gender_adapter;
    private TextInputLayout gender_layout;
    private EditText gender_view;
    private String gender_data,age_data,address_data,Height_data,Weight_data,Consultation_type_data,
            Previous_Lab_reports_data,relevent_point_data,Diagnosis_data,exmination_data,
            instructions_data,Rx_data,date_data;
    private String date,time,pname,phone,email,phone_No,dname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presciption__patient_side);

        name = findViewById(R.id.textInputName);
        age = findViewById(R.id.textInputAge);
        address = findViewById(R.id.textInputBio);
        height = findViewById(R.id.textInputHeight);
        weight = findViewById(R.id.textInputWeight);
        consultationType = findViewById(R.id.textInputConsultationType);
        labReport = findViewById(R.id.textInputPreviousLabReport);
        diagnosis = findViewById(R.id.textInputDiagnosis);
        relevant = findViewById(R.id.textInputRelevantPoints);
        exam = findViewById(R.id.textInputExamination);
        instruct = findViewById(R.id.textInputInstruction);
        selectDate = findViewById(R.id.consultationDateEdit);
        rx = findViewById(R.id.textInputRx);
        send_pres=(Button) findViewById(R.id.send_prescription);
        gender_view = findViewById(R.id.genderTextView_pres);

        doctor = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        prescription_doctor = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Prescription_By_Doctor");
        firebaseStorage = FirebaseStorage.getInstance();

        email= (String) getIntent().getSerializableExtra("email");
        date=(String) getIntent().getSerializableExtra("date");
        time=(String) getIntent().getSerializableExtra("time");
        Patient_Session_Management session = new Patient_Session_Management(Presciption_PatientSide.this);
        phone_No = session.getSession();

        doctor.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dname=snapshot.child("name").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        prescription_doctor.child(email).child(phone_No).child(date).child(time).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    PrescriptionDetails prescriptionDetails=snapshot.getValue(PrescriptionDetails.class);
                    name.setText(prescriptionDetails.getPatientName());
                    age.setText(prescriptionDetails.getAge());
                    address.setText(prescriptionDetails.getDoctorsAddress());
                    height.setText(prescriptionDetails.getPatientHeight());
                    weight.setText(prescriptionDetails.getPatientWeight());
                    consultationType.setText(prescriptionDetails.getConsultationType());
                    labReport.setText(prescriptionDetails.getPrevious_Lab_Report());
                    diagnosis.setText(prescriptionDetails.getDiagnosis_Information());
                    relevant.setText(prescriptionDetails.getRelevent_Point_from_history());
                    exam.setText(prescriptionDetails.getExaminations());
                    instruct.setText(prescriptionDetails.getInstructions());
                    selectDate.setText(prescriptionDetails.getConsultation_Date());
                    rx.setText(prescriptionDetails.getRx());
                    gender_view.setText(prescriptionDetails.getPatientGender());
                    pname=prescriptionDetails.getPatientName();
                    age_data=prescriptionDetails.getAge();
                    address_data=prescriptionDetails.getDoctorsAddress();
                    Height_data=prescriptionDetails.getPatientHeight();
                    Weight_data=prescriptionDetails.getPatientWeight();
                    Consultation_type_data=prescriptionDetails.getConsultationType();
                    Previous_Lab_reports_data=prescriptionDetails.getPrevious_Lab_Report();
                    Diagnosis_data=prescriptionDetails.getDiagnosis_Information();
                    relevent_point_data=prescriptionDetails.getRelevent_Point_from_history();
                    exmination_data=prescriptionDetails.getExaminations();
                    instructions_data=prescriptionDetails.getInstructions();
                    date_data=prescriptionDetails.getConsultation_Date();
                    Rx_data=prescriptionDetails.getRx();
                    gender_data=prescriptionDetails.getPatientGender();



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        send_pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_prescription_over_mail();
            }
        });


    }

    private void send_prescription_over_mail() {

        View view = LayoutInflater.from(Presciption_PatientSide.this).inflate(R.layout.prescription_over_mail, null);
        final EditText emailText = view.findViewById(R.id.emailEt);
        Button get = view.findViewById(R.id.send_email);
        final AlertDialog.Builder builder = new AlertDialog.Builder(Presciption_PatientSide.this);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid = emailText.getText().toString().trim();

                if (TextUtils.isEmpty(emailid)){
                    Toast.makeText(Presciption_PatientSide.this, "Enter Your email id...", Toast.LENGTH_SHORT).show();
                    return;
                }
                message="";
                if(!date_data.isEmpty()){
                    message=message+"\n \nDate of Consultation: "+ date_data;
                }
                else {
                    message=message+"\n \nDate of Consultation: "+ "-";
                }

                if(!pname.isEmpty()){
                    message=message+"\n \nPatient Name: "+ pname;
                }
                else {
                    message=message+"\n\n Patient Name: "+ "-";
                }

                if(!age_data.isEmpty()){
                    message=message+"\n\n Age: "+ age_data;
                }
                else {
                    message=message+"\n\n Age: "+ "-";
                }

                if(!address_data.isEmpty()){
                    message=message+"\n \nAddress: "+ address_data;
                }
                else {
                    message=message+"\n\n Address: "+ "-";
                }

                if(!Height_data.isEmpty()){
                    message=message+"\n\n Height: "+ Height_data;
                }
                else {
                    message=message+"\n \nHeight: "+ "-";
                }
                if(!Weight_data.isEmpty()){
                    message=message+"\n Weight: "+ Weight_data;
                }
                else {
                    message=message+"\n\n Weight: "+ "-";
                }

                if(!Consultation_type_data.isEmpty()){
                    message=message+"\n\n Consultation Type: "+ Consultation_type_data;
                }
                else {
                    message=message+"\n \nConsultation Type: "+ "-";
                }

                if(!Previous_Lab_reports_data.isEmpty()){
                    message=message+"\n \n Lab Report: "+ Previous_Lab_reports_data;
                }
                else {
                    message=message+"\n\n Lab Report: "+ "-";
                }
                if(!Diagnosis_data.isEmpty()){
                    message=message+"\n\n Diagnosis: "+ Diagnosis_data;
                }
                else {
                    message=message+"\n \nDiagnosis: "+ "-";
                }
                if(!relevent_point_data.isEmpty()){
                    message=message+"\n \nRelevant Points: "+ relevent_point_data;
                }
                else {
                    message=message+"\n \nRelevant Points: "+ "-";
                }

                if(!exmination_data.isEmpty()){
                    message=message+"\n\n Examination: "+ exmination_data;
                }
                else {
                    message=message+"\n \nExamination: "+ "-";
                }

                if(!instructions_data.isEmpty()){
                    message=message+"\n \nInstructions: "+ instructions_data;
                }
                else {
                    message=message+"\n\n Instructions: "+ "-";
                }

                if(!Rx_data.isEmpty()){
                    message=message+"\n \nRx: "+ Rx_data;
                }
                else {
                    message=message+"\n \nRx: "+ "-";
                }

                try {

                    final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailid});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, " Prescription report of  "+ " " +date + " "+ "by : " + dname);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(emailIntent, "Sending email..."));


                } catch (Throwable t) {
                    Toast.makeText(Presciption_PatientSide.this, "Request failed try again: "+ t.toString(), Toast.LENGTH_LONG).show();
                }


                dialog.dismiss();

            }
        });
    }

}