package com.example.ahujaclinic;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ahujaclinic.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Calendar;

public class PrescriptionActivity extends AppCompatActivity {

    private TextInputEditText name,age,address,height,weight,consultationType,labReport,diagnosis,relevant,exam,instruct ,rx;
    private EditText selectDate;
    private String finalDate;
    private String[] gender;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference prescription_doctor;
    private DatePickerDialog.OnDateSetListener setListener;
    private ArrayAdapter<String> gender_adapter;
    private TextInputLayout gender_layout;
    private AutoCompleteTextView gender_view;
    private String gender_data,age_data,address_data,Height_data,Weight_data,Consultation_type_data,Previous_Lab_reports_data
            ,relevent_point_data,Diagnosis_data,exmination_data,instructions_data,Rx_data,date_data;
    private String date,time,pname,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
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
        rx = findViewById(R.id.textInputRx);
        gender_layout = (TextInputLayout) findViewById(R.id.genderLayout_pres);
        gender_view = (AutoCompleteTextView) findViewById(R.id.genderTextView_pres);
        gender = getResources().getStringArray(R.array.Gender);
        gender_adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_gender, gender );
        gender_view.setAdapter(gender_adapter);
        gender_view.setThreshold(1);
        prescription_doctor = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Prescription_By_Doctor");

        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(this);
        email = doctors_session_mangement.getDoctorSession()[0].replace(".",",");

        firebaseStorage = FirebaseStorage.getInstance();

        selectDate = findViewById(R.id.consultationDateEdit);

        date= (String) getIntent().getSerializableExtra("date");
        time= (String) getIntent().getSerializableExtra("time");
        pname=(String) getIntent().getSerializableExtra("name");
        phone= (String) getIntent().getSerializableExtra("phone");
        name.setText(pname);

        selectDate.setText(date);

        gender_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gender_data = gender_adapter.getItem(position);
            }
        });

        prescription_doctor.child(email).child(phone).child(date).child(time).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    PrescriptionDetails prescriptionDetails = snapshot.getValue(PrescriptionDetails.class);
                    age.setText(prescriptionDetails.getAge());
                    gender_view.setText(prescriptionDetails.getPatientGender(), false);
                    address.setText(prescriptionDetails.getDoctorsAddress());
                    height.setText(prescriptionDetails.getPatientHeight());
                    weight.setText(prescriptionDetails.getPatientWeight());
                    consultationType.setText(prescriptionDetails.getConsultationType());
                    labReport.setText(prescriptionDetails.getPrevious_Lab_Report());
                    relevant.setText(prescriptionDetails.getRelevent_Point_from_history());
                    diagnosis.setText(prescriptionDetails.getDiagnosis_Information());
                    exam.setText(prescriptionDetails.getExaminations());
                    instruct.setText(prescriptionDetails.getInstructions());
                    rx.setText(prescriptionDetails.getRx());
                    selectDate.setText(prescriptionDetails.getConsultation_Date());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void send_prescription(View view) {

        age_data=age.getText().toString().trim();
        address_data=address.getText().toString().trim();
        Height_data=height.getText().toString().trim();
        Weight_data=weight.getText().toString().trim();
        Consultation_type_data=consultationType.getText().toString().trim();
        Previous_Lab_reports_data=labReport.getText().toString().trim();
        relevent_point_data=relevant.getText().toString().trim();
        Diagnosis_data=diagnosis.getText().toString().trim();
        exmination_data=exam.getText().toString().trim();
        instructions_data=instruct.getText().toString().trim();
        Rx_data=rx.getText().toString().trim();
        date_data=selectDate.getText().toString().trim();

        if(date_data.isEmpty()){
            selectDate.setText("date is required field");
            selectDate.requestFocus();
            return;
        }
        if(pname.isEmpty()){
            name.setError("Name is Required Field");
            name.requestFocus();
            return;
        }
        if(age_data.isEmpty()){
            age.setError("Age is a required field");
            age.requestFocus();
            return;
        }

        PrescriptionDetails prescriptionDetails = new PrescriptionDetails(pname,gender_data,age_data,address_data,
                Height_data,Weight_data,Consultation_type_data,date_data,Previous_Lab_reports_data,
                relevent_point_data,Diagnosis_data,exmination_data,instructions_data,Rx_data,0);
        prescription_doctor.child(email).child(phone).child(date).child(time).setValue(prescriptionDetails);
        Toast.makeText(PrescriptionActivity.this, "Prescription Uploaded Successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PrescriptionActivity.this, Doctors.class));


    }
}