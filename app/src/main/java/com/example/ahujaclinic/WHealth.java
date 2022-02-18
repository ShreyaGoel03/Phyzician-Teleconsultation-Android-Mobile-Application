package com.example.ahujaclinic;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class WHealth extends AppCompatActivity {
    private CalendarPickerView calendar;
    private String startdate;
    private String enddate;
    private String phoneno;
    private DatabaseReference Fitness;
    private ArrayList<Date> arrayList;
    private int duration;
    private int total_duration;
    private MaterialDialog mAnimatedDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_health);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -2);

        calendar = (CalendarPickerView) findViewById(R.id.calendarw);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        Fitness = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Fitness");
        Patient_Session_Management session = new Patient_Session_Management(WHealth.this);
        phoneno = session.getSession();
        arrayList = new ArrayList<>();
        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, yyyy", Locale.getDefault())) //
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);

        calendar.scrollToDate(new Date());

        Fitness.child("Women Health").child(phoneno).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    arrayList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            if (Objects.equals(dataSnapshot.getKey(), "Duration")) {
                                total_duration = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child("duration").getValue()).toString());
                            } else {
                                String startdate = Objects.requireNonNull(dataSnapshot.child("start_Date").getValue()).toString();
                                String enddate = Objects.requireNonNull(dataSnapshot.child("end_Date").getValue()).toString();

                                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
                                Calendar calendar = new GregorianCalendar();
                                try {
                                    calendar.setTime(dateformat.parse(startdate));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date newenddate = null;
                                try {
                                    newenddate = dateformat.parse(enddate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                while (calendar.getTime().before(newenddate)) {
                                    Date result = calendar.getTime();
                                    arrayList.add(result);
                                    calendar.add(Calendar.DATE, 1);
                                }

                            }
                            calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, yyyy", Locale.getDefault())) //
                                    .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
//                            .withSubTitles(getSubTitles())
                                    .withSelectedDates(arrayList);
                            calendar.scrollToDate(new Date());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                startdate = "" + calSelected.get(Calendar.DAY_OF_MONTH)
                        + "-" + (calSelected.get(Calendar.MONTH) + 1)
                        + "-" + calSelected.get(Calendar.YEAR);

                Calendar calSelected_end = Calendar.getInstance();
                calSelected_end.setTime(date);

                calSelected_end.add(Calendar.DATE, total_duration);

                enddate = "" + calSelected_end.get(Calendar.DAY_OF_MONTH)
                        + "-" + (calSelected_end.get(Calendar.MONTH) + 1)
                        + "-" + calSelected_end.get(Calendar.YEAR);
                Log.d("One", enddate + "  enddate");

                mAnimatedDialog = new MaterialDialog.Builder(WHealth.this)
                        .setTitle("Edit Calendar Entry")
                        .setMessage("Do You Want to Mark this Date as Start Date ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                Toast.makeText(WHealth.this, "Selected Option: YES", Toast.LENGTH_SHORT).show();
                                Whealth_Record record = new Whealth_Record(startdate, enddate);
                                String monthname = new DateFormatSymbols().getMonths()[calSelected.get(Calendar.MONTH)];
                                Fitness.child("Women Health").child(phoneno).child(monthname + "-" + calSelected.get(Calendar.YEAR)).setValue(record);

                                dialogInterface.dismiss();
                            }

                        })
                        .setNegativeButton("No", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                Toast.makeText(WHealth.this, "Selected Option: No", Toast.LENGTH_SHORT).show();
                                calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, yyyy" +
                                        "", Locale.getDefault())) //
                                        .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
//                            .withSubTitles(getSubTitles())
                                        .withSelectedDates(arrayList);
                                calendar.scrollToDate(new Date());
                                dialogInterface.dismiss();
                            }

                        })
                        .setAnimation("whealth_date_cal.json")
                        .build();

                mAnimatedDialog.show();

//                AlertDialog.Builder builder = new AlertDialog.Builder(WHealth.this);
//
//
//                builder.setTitle("Edit Calendar Entry")
//                        .setMessage("\"Do You Want to Mark this Date as Start Date ?\"")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(WHealth.this, "Selected Option: YES", Toast.LENGTH_SHORT).show();
//                                Whealth_Record record = new Whealth_Record(startdate, enddate);
//                                String monthname = new DateFormatSymbols().getMonths()[calSelected.get(Calendar.MONTH)];
//                                Fitness.child("Women Health").child(phoneno).child(monthname + "-" + calSelected.get(Calendar.YEAR)).setValue(record);
//
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                flag = 1;
//                                calendar.clearSelectedDates();
//                                Toast.makeText(WHealth.this, "Selected Option: No", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                AlertDialog dialog = builder.create();
//                dialog.show();

            }

            @Override
            public void onDateUnselected(Date date) {
                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                mAnimatedDialog = new MaterialDialog.Builder(WHealth.this)
                        .setTitle("Edit Calendar Entry")
                        .setMessage("Do You Want to Remove this Date from Start Date ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                String monthname = new DateFormatSymbols().getMonths()[calSelected.get(Calendar.MONTH)];
                                Fitness.child("Women Health").child(phoneno).child(monthname + "-" + calSelected.get(Calendar.YEAR)).removeValue();
                                dialogInterface.dismiss();
                            }

                        })
                        .setNegativeButton("No", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                Toast.makeText(WHealth.this, "Selected Option: No", Toast.LENGTH_SHORT).show();
                                calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, yyyy", Locale.getDefault())) //
                                        .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
//                            .withSubTitles(getSubTitles())
                                        .withSelectedDates(arrayList);
                                calendar.scrollToDate(new Date());
                                dialogInterface.dismiss();
                            }

                        })
                        .setAnimation("delete_date.json")
                        .build();

                mAnimatedDialog.show();
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(WHealth.this);
//
//                builder.setTitle("Edit Calendar Entry").setMessage("\"Do You Want to Remove this Date from Start Date ?\"").setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                String monthname = new DateFormatSymbols().getMonths()[calSelected.get(Calendar.MONTH)];
//                                Fitness.child("Women Health").child(phoneno).child(monthname + "-" + calSelected.get(Calendar.YEAR)).removeValue();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(WHealth.this, "Selected Option: No", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                AlertDialog dialog = builder.create();
//                dialog.show();

            }
        });

    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(WHealth.this, Patient.class));
                    break;

                case R.id.navigation_sms:
                    ArrayList<Date> arrayList_prev = new ArrayList<>();
                    View view1 = LayoutInflater.from(WHealth.this).inflate(R.layout.whealth_predicted, null);
                    final CalendarPickerView calendarPickerView1 = view1.findViewById(R.id.calender_predicted);
                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(WHealth.this);
                    builder1.setView(view1);
                    final Calendar nextYear_new = Calendar.getInstance();
                    nextYear_new.add(Calendar.MONTH, 4);

                    final Calendar prev_month_new = Calendar.getInstance();
                    prev_month_new.add(Calendar.MONTH, -1);

                    calendarPickerView1.init(prev_month_new.getTime(), nextYear_new.getTime(), new SimpleDateFormat("MMMM- yyyy", Locale.getDefault())) //
                            .inMode(CalendarPickerView.SelectionMode.SINGLE);

                    Calendar prev_month = Calendar.getInstance();
                    prev_month.add(Calendar.MONTH, -1);
                    String prev_monthname = new DateFormatSymbols().getMonths()[prev_month.get(Calendar.MONTH)];
                    String pre_month_year = "" + prev_monthname
                            + "-" + prev_month.get(Calendar.YEAR);
                    Log.d("One", "previous_month " + pre_month_year);
                    Fitness.child("Women Health").child(phoneno).child(pre_month_year).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String prev_date = snapshot.child("start_Date").getValue().toString();
                                Log.d("One", "Previous_date " + prev_date);
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat2 = new SimpleDateFormat("dd-MM-yyyy");
                                Calendar calendar2 = new GregorianCalendar();
                                try {
                                    calendar2.setTime(dateformat2.parse(prev_date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                calendar2.add(Calendar.DATE, 28);
                                for (int i = 0; i < 4; i++) {
                                    Date result = calendar2.getTime();
                                    Log.d("One", "Date after 28 days " + result);
//                                    if (result.after(new Date())) {
//                                        arrayList_prev.add(result);
//                                    }
                                    arrayList_prev.add(result);

                                    calendar2.add(Calendar.DATE, 28);

                                }
                                Log.d("One", "date after 28 days in arraylist  " + arrayList_prev);
                                calendarPickerView1.init(prev_month_new.getTime(), nextYear_new.getTime(), new SimpleDateFormat("MMMM, yyyy", Locale.getDefault())) //
                                        .inMode(CalendarPickerView.SelectionMode.SINGLE)
//                            .withSubTitles(getSubTitles())
                                        .withHighlightedDates(arrayList_prev);
                                calendarPickerView1.scrollToDate(new Date());

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    AlertDialog dialog1 = builder1.create();
                    dialog1.show();

                    break;


                case R.id.navigation_Duratoin:
                    View view = LayoutInflater.from(WHealth.this).inflate(R.layout.whealth_duration, null);
                    final EditText durationText = view.findViewById(R.id.duration_no);
                    Button save = view.findViewById(R.id.save_duration);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(WHealth.this);
                    builder.setView(view);

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (durationText.getText().toString().isEmpty()) {
                                durationText.setError("Cycle Duration is a required field !");
                                durationText.requestFocus();
                                return;
                            }
                            duration = Integer.parseInt(String.valueOf(durationText.getText()));
                            dialog.dismiss();

                            Whealth_Duration whealth_duration = new Whealth_Duration(duration);
                            Fitness.child("Women Health").child(phoneno).child("Duration").setValue(whealth_duration);
                        }
                    });
            }
            return false;
        }
    };

    private ArrayList<SubTitle> getSubTitles() {
        final ArrayList<SubTitle> subTitles = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            subTitles.add(new SubTitle(arrayList.get(i), "Start Date"));
        }
        return subTitles;
    }
    //    @Override
//    protected void onResume() {
//        super.onResume();
//        final Calendar nextYear = Calendar.getInstance();
//        nextYear.add(Calendar.MONTH, 1);
//
//        final Calendar lastYear = Calendar.getInstance();
//        lastYear.add(Calendar.YEAR, -2);
//
//        Date today = new Date();
//        calendar = (CalendarPickerView) findViewById(R.id.calendarw);
//        bottomNavigation = findViewById(R.id.bottom_navigation);
//        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
//
//        firebaseStorage = FirebaseStorage.getInstance();
//        Fitness = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Fitness");
//        Patient_Session_Management session = new Patient_Session_Management(WHealth.this);
//        phoneno = session.getSession();
//
////        ArrayList<Integer> list = new ArrayList<>();
////        list.add(2);
//
//        arrayList = new ArrayList<>();
//        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault())) //
//                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);
//
//        calendar.scrollToDate(new Date());
//
//        Fitness.child("Women Health").child(phoneno).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    arrayList = new ArrayList<>();
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        if (dataSnapshot.exists()) {
//                            if (dataSnapshot.getKey().equals("Duration")) {
//                                total_duration = Integer.parseInt(dataSnapshot.child("duration").getValue().toString());
//                            } else {
//                                String startdate = dataSnapshot.child("start_Date").getValue().toString();
//                                String enddate = dataSnapshot.child("end_Date").getValue().toString();
//
//                                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
//                                Calendar calendar = new GregorianCalendar();
//                                try {
//                                    calendar.setTime(dateformat.parse(startdate));
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                                Date newenddate = null;
//                                try {
//                                    newenddate = dateformat.parse(enddate);
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//
//                                while (calendar.getTime().before(newenddate)) {
//                                    Date result = calendar.getTime();
//                                    arrayList.add(result);
//                                    calendar.add(Calendar.DATE, 1);
//                                }
//
//                            }
//                            calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault())) //
//                                    .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
////                            .withSubTitles(getSubTitles())
//                                    .withSelectedDates(arrayList);
//                            calendar.scrollToDate(new Date());
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//    }
}