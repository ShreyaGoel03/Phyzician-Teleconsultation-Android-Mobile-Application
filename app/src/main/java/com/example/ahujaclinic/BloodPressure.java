package com.example.ahujaclinic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class BloodPressure extends AppCompatActivity {

    private TextView bp_date, sys_val, dys_val;
    private SeekBar systolic_bar, distolic_bar;
    private EditText pulse_rate;
    private String bp_systolic,bp_dystolic,bp_pulse_rate,phone;
    private Button save;
    private DatabaseReference reference_fitness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        reference_fitness = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Fitness");
        Patient_Session_Management session = new Patient_Session_Management(BloodPressure.this);
        phone = session.getSession();
        bp_date = findViewById(R.id.show_date);
        systolic_bar = findViewById(R.id.seekBar2);
        distolic_bar = findViewById(R.id.seekBar);
        pulse_rate = findViewById(R.id.edittext_pulse_rate);
        sys_val = findViewById(R.id.sys_val);
        dys_val = findViewById(R.id.dys_val);
        save = findViewById(R.id.save_btn);
        bp_date.setText(DateFormat.getDateTimeInstance().format(new Date()));
        systolic_bar.setMax(300);
        systolic_bar.setProgress(0);
        systolic_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("ABC", "P1 "+progress);
                sys_val.setText(String.valueOf(progress));
                bp_systolic=sys_val.getText().toString();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        distolic_bar.setProgress(0);
        distolic_bar.setMax(300);
        distolic_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("ABC", "P "+progress);
                dys_val.setText(String.valueOf(progress));
                bp_dystolic=dys_val.getText().toString();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bp_pulse_rate=pulse_rate.getText().toString();
        Log.d("ABC", "Value"+bp_systolic);
        String[] monthName = {"Jan", "Feb",
                "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov",
                "Dec"};
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        String month = monthName[cal.get(Calendar.MONTH)];
        int year = cal.get(Calendar.YEAR);
        String value = day + " " + month + " " + year;

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BP_Record record = new BP_Record(bp_systolic,bp_dystolic, bp_pulse_rate);
                reference_fitness.child("BP").child(phone).child(value).setValue(record);
                Toast.makeText(BloodPressure.this,"Data Saved!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}