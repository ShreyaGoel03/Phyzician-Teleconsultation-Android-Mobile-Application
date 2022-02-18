package com.example.ahujaclinic;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class PhyziHealth_BloodPressure_View extends AppCompatActivity {
    private TextView sys, dys, pulse;
    private DatabaseReference reference_fitness;
    private String phone,date_val;
    private HorizontalCalendar horizontalCalendar;
    private Button record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bp);
        sys = findViewById(R.id.sys);
        dys = findViewById(R.id.dys);
//        pulse = findViewById(R.id.pulse);
        record = findViewById(R.id.button);
        reference_fitness = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Fitness");
        Patient_Session_Management session = new Patient_Session_Management(PhyziHealth_BloodPressure_View.this);
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
        date_val = DateFormat.format("dd MMM yyyy", todayDate).toString();
        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.date_View)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        reference_fitness.child("BP").child(phone).child(date_val).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            BP_Record record = snapshot.getValue(BP_Record.class);
                            sys.setText(record.getSys());
                            dys.setText(record.getDia());
//                            pulse.setText(record.getPulse());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                date_val = DateFormat.format("dd MMM yyyy", date).toString();
                sys.setText("--");
                dys.setText("--");
//                pulse.setText("");
                reference_fitness.child("BP").child(phone).child(date_val).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            BP_Record record = snapshot.getValue(BP_Record.class);
                            sys.setText(record.getSys());
                            dys.setText(record.getDia());
//                            pulse.setText(record.getPulse());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhyziHealth_BloodPressure_View.this, BloodPressure.class));
            }
        });

    }
}
