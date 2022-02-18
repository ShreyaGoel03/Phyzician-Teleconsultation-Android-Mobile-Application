package com.example.ahujaclinic;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class Doctors_Show_Previous_Prescription_Doctors extends AppCompatActivity {

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
    private String date,time,pname,phone,email,phone_No,dname;
    private String patient_phone_No;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors__show__previous__prescription__doctors);

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

        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(this);
        email = doctors_session_mangement.getDoctorSession()[0].replace(".",",");


        doctor = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        prescription_doctor = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Prescription_By_Doctor");

        patient_phone_No= (String) getIntent().getSerializableExtra("phone");
        date=(String) getIntent().getSerializableExtra("date");
        time=(String) getIntent().getSerializableExtra("time");

        prescription_doctor.child(email).child(patient_phone_No).child(date).child(time).addValueEventListener(new ValueEventListener() {
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

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}