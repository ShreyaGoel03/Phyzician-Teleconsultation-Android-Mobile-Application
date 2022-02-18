package com.example.ahujaclinic;

import android.app.NotificationChannel;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;

public class Doctor_ChooseSlots extends AppCompatActivity {
    private TextInputLayout date_layout, start_layout, end_layout;
    private AutoCompleteTextView date_view, start_view, end_view;
    private ArrayList<String> dates, start_time1, end_time1, start_time, end_time, date_slot;
    private ArrayAdapter<String> date_adapter, start_adapter, end_adapter;
    private String chosen_date = "";
    private DatabaseReference reference_user, reference_doctor, reference_booking, reference_token, reference_patient, reference_payment, reference_details, reference_appt;
    private String start_data = "", end_data = "", email;
    private StorageReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private Button choose;
    private RecyclerView recyclerView;
    private ArrayList<DataSnapshot> doctor_slots;
    private Doctor_View_Slots_Adapter adapter;
    private int start = 0, end = 0;
    private RequestQueue mRequestQue;
    private ArrayList<Integer> count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_choose_slots);

        dates = new ArrayList<>();
        start_time = new ArrayList<>();
        start_time1 = new ArrayList<>();
        end_time = new ArrayList<>();

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        email = user.getEmail();
//        email = email.replace(".",",");

        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(this);
        email = doctors_session_mangement.getDoctorSession()[0].replace(".", ",");
        Toast.makeText(Doctor_ChooseSlots.this, "Swipe to cancel the slots from the chosen!", Toast.LENGTH_LONG).show();
        firebaseStorage = FirebaseStorage.getInstance();

        databaseReference = firebaseStorage.getReference().child(email);
        reference_doctor = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Data");
        reference_appt = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Appointments");
        reference_user = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        reference_booking = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Chosen_Slots");
        reference_token = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Tokens");
        reference_patient = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Chosen_Slots");
        reference_details = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Details");
        reference_payment = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Admin_Payment");


        date_layout = (TextInputLayout) findViewById(R.id.dateLayout);
        date_view = (AutoCompleteTextView) findViewById(R.id.date);
        start_layout = (TextInputLayout) findViewById(R.id.starttimelayout);
        start_view = (AutoCompleteTextView) findViewById(R.id.starttime);
        end_layout = (TextInputLayout) findViewById(R.id.endtimelayout);
        end_view = (AutoCompleteTextView) findViewById(R.id.endtime);
        choose = (Button) findViewById(R.id.choose_btn);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_slots);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Doctor_ChooseSlots.this));
        doctor_slots = new ArrayList<>();
        count = new ArrayList<>();

        String[] monthName = {"Jan", "Feb",
                "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov",
                "Dec"};
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            int day = cal.get(Calendar.DATE);
            String month = monthName[cal.get(Calendar.MONTH)];
            int year = cal.get(Calendar.YEAR);
            String value = day + " " + month + " " + year;
            dates.add(value);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        Calendar cal_1 = Calendar.getInstance();
        for (int j = cal_1.get(Calendar.HOUR_OF_DAY) + 1; j < 24; j++) {
            String time = j + ":00";
            start_time1.add(time);
        }

        for (int j = 0; j < 24; j++) {
            String time = j + ":00";
            start_time.add(time);
        }

        date_adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_gender, dates);
        date_view.setAdapter(date_adapter);
        date_view.setThreshold(1);
        date_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chosen_date = date_adapter.getItem(position);
                if (position == 0) {
                    start_adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_gender, start_time1);
                } else {
                    start_adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_gender, start_time);
                }
                start_view.setAdapter(start_adapter);
                start_view.setText("");
                start_data = "";
                end_data = "";
                end_view.setText("");
                end_time = new ArrayList<>();
                end_adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_gender, end_time);
                end_view.setAdapter(end_adapter);
                start_view.setThreshold(1);
            }
        });

        start_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (chosen_date.equals("")) {
                    Toast.makeText(Doctor_ChooseSlots.this, "Select Date First", Toast.LENGTH_SHORT).show();

                }
                start_data = start_adapter.getItem(position);
                int start = Integer.parseInt(start_data.split(":", 5)[0]);
                end_time = new ArrayList<>();
                for (int i = start + 1; i < 24; i++) {
                    end_time.add(i + ":00");
                }
                end_adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_gender, end_time);
                end_view.setAdapter(end_adapter);
                end_view.setText("");
                end_data = "";
                end_view.setThreshold(1);
            }
        });

        end_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                end_data = end_adapter.getItem(position);
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chosen_date.equals("")) {
                    Toast.makeText(Doctor_ChooseSlots.this, "Select Date!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (start_data.equals("") || start_data.equals("Start Time")) {
                    Toast.makeText(Doctor_ChooseSlots.this, "Choose Start Time!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (end_data.equals("") || end_data.equals("End Time")) {
                    Toast.makeText(Doctor_ChooseSlots.this, "Choose End Time!", Toast.LENGTH_SHORT).show();
                    return;
                }

                start = Integer.parseInt(start_data.split(":", 5)[0]);
                end = Integer.parseInt(end_data.split(":", 5)[0]);
                reference_booking.child(email).child(chosen_date).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            int flag = 0;
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                int start_val = Integer.parseInt(dataSnapshot.getKey().split(" - ", 5)[0]);
                                int end_val = Integer.parseInt(dataSnapshot.getKey().split(" - ", 5)[1]);
                                if ((start < start_val && end <= start_val) || (start >= end_val && end > end_val)) {
                                    continue;
                                } else {
                                    flag = 1;
                                    break;
                                }
                            }

                            if (flag == 1) {
                                Toast.makeText(Doctor_ChooseSlots.this, "You have already booked slot in this Time Frame", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                int slots = (end - start) * 2;
                                String date = "";
                                Doctor_Slots_Time time = new Doctor_Slots_Time(String.valueOf(start), String.valueOf(end));
                                String complete_slot = start + " - " + end;
                                reference_booking.child(email).child(chosen_date).child(complete_slot).child("Count").setValue(0);
                                for (int i = 0; i < slots; i++) {
                                    if (i % 2 == 0) {
                                        date = start + ":00";
                                    } else {
                                        date = start + ":30";
                                        start += 1;
                                    }
                                    Booking_Appointments booking = new Booking_Appointments(0, "null");
                                    reference_booking.child(email).child(chosen_date).child(complete_slot).child(date).setValue(booking);
                                }
                                Toast.makeText(Doctor_ChooseSlots.this, "Slots Chosen!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            int slots = (end - start) * 2;
                            String date = "";
                            Doctor_Slots_Time time = new Doctor_Slots_Time(String.valueOf(start), String.valueOf(end));
                            String complete_slot = start + " - " + end;
                            reference_booking.child(email).child(chosen_date).child(complete_slot).child("Count").setValue(0);
                            for (int i = 0; i < slots; i++) {
                                if (i % 2 == 0) {
                                    date = start + ":00";
                                } else {
                                    date = start + ":30";
                                    start += 1;
                                }
                                Booking_Appointments booking = new Booking_Appointments(0, "null");
                                reference_booking.child(email).child(chosen_date).child(complete_slot).child(date).setValue(booking);
                            }
                            Toast.makeText(Doctor_ChooseSlots.this, "Slots Chosen!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        reference_booking.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    doctor_slots = new ArrayList<>();
                    date_slot = new ArrayList<String>();
                    count = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            doctor_slots.add(dataSnapshot1);
                            date_slot.add(dataSnapshot.getKey());
                            count.add(dataSnapshot1.child("Count").getValue(Integer.class));
                        }
                    }

                    adapter = new Doctor_View_Slots_Adapter(Doctor_ChooseSlots.this, doctor_slots, date_slot, count);
                    recyclerView.setAdapter(adapter);

                    ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            new AlertDialog.Builder(viewHolder.itemView.getContext())
                                    .setMessage("Do you want to Delete this Slot?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int position = viewHolder.getAdapterPosition();
                                            DataSnapshot data = doctor_slots.get(position);
                                            String date = date_slot.get(position);
                                            int count = data.child("Count").getValue(Integer.class);
                                            if (count != 0) {
                                                for (DataSnapshot datasnap : data.getChildren()) {
                                                    if (!(datasnap.getKey().equals("Count"))) {
                                                        String slot = "";
                                                        int start = Integer.parseInt(datasnap.getKey().split(":", 5)[0]);
                                                        String end = datasnap.getKey().split(":", 5)[1];
                                                        if (end.equals("00")) {
                                                            slot = datasnap.getKey() + " - " + start + ":30";
                                                        } else {
                                                            slot = datasnap.getKey() + " - " + (start + 1) + ":00";
                                                        }
                                                        Booking_Appointments appts = datasnap.getValue(Booking_Appointments.class);
                                                        String phone_num = appts.getPhone();
                                                        if (!phone_num.equals("null")) {
                                                            reference_patient.child(phone_num).child(email).child(date).child(slot).child("status").setValue(2);
                                                            reference_appt.child(email).child(date).child(slot).child("appointment_text").setValue("2");
                                                            String finalSlot = slot;
                                                            reference_patient.child(phone_num).child(email).child(date).child(slot).addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int value = snapshot.child("payment").getValue(Integer.class);
                                                                    if (value == 1) {
                                                                        reference_payment.child("Payment1").child(phone_num).child(date).child(finalSlot).child("status").setValue(2);
                                                                    } else {
                                                                        reference_payment.child("Payment0").child(phone_num).child(date).child(finalSlot).child("status").setValue(2);
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });

                                                        }
                                                    }
                                                }
                                            }
                                            data.getRef().removeValue();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    };
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
