package com.example.ahujaclinic;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class PhyziHealth_StepCounter extends AppCompatActivity {
    private TextView steps_val, cal_val, dist_val;
    private boolean running = false;
    private float total_steps = 0f;
    private float previous_total_steps = 0f;
    private HorizontalCalendar horizontalCalendar;
    private String date_val,phone, date_today, date_val2;
    private DatabaseReference reference_fitness;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);
        steps_val = (TextView) findViewById(R.id.steps_taken);
        cal_val = (TextView)findViewById(R.id.textView6);
        dist_val = (TextView)findViewById(R.id.textView22);
        reference_fitness = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Fitness");
        Patient_Session_Management session = new Patient_Session_Management(PhyziHealth_StepCounter.this);
        phone = session.getSession();
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        Calendar todayDate = Calendar.getInstance();
        todayDate.add(Calendar.MONTH, 0);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        String[] monthName = {"Jan", "Feb",
                "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov",
                "Dec"};

        date_today = DateFormat.format("dd MMM yyyy", todayDate).toString();
        date_val = DateFormat.format("dd MMM yyyy", todayDate).toString();
        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.date_View)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        reference_fitness.child("Steps").child(phone).child(date_today).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int steps = snapshot.child("steps").getValue(Integer.class);
                    steps_val.setText(""+steps);
                    float distance = (float) (steps*74)/(float)100000;
                    dist_val.setText(distance+" km");
                    float cal = (float)(steps*0.04);
                    cal_val.setText(cal+" cal");
                }
                else{
                    steps_val.setText("0");
                    dist_val.setText("0 km");
                    cal_val.setText("0 cal");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        else{
            if (isMyServiceRunning(StepCounter_Service.class)){
                stopService(new Intent(PhyziHealth_StepCounter.this, StepCounter_Service.class));
            }
            startService(new Intent(PhyziHealth_StepCounter.this, StepCounter_Service.class));
        }

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                date_val = DateFormat.format("dd MMM yyyy", date).toString();
                steps_val.setText("");
                dist_val.setText("");
                cal_val.setText("");
                reference_fitness.child("Steps").child(phone).child(date_val).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            int steps = snapshot.child("steps").getValue(Integer.class);
                            steps_val.setText(""+steps);
                            float distance = (float) (steps*74)/(float)100000;
                            dist_val.setText(distance+" km");
                            float cal = (float)(steps*0.04);
                            cal_val.setText(cal+" cal");
                        }
                        else{
                             steps_val.setText("0");
                            dist_val.setText("0 km");
                            cal_val.setText("0 cal");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar todayDate = Calendar.getInstance();
        todayDate.add(Calendar.MONTH, 0);
        date_val2 = DateFormat.format("dd MMM yyyy", todayDate).toString();
        reference_fitness.child("Steps").child(phone).child(date_val2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    steps_val.setText(snapshot.child("steps").getValue(Integer.class).toString());
                }
                else{
                    steps_val.setText("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
