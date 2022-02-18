package com.example.ahujaclinic;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class Admin_Payments_Current extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseUser user;
    private DatabaseReference reference, reference_user, reference_doctor, reference_booking, reference_patient, reference_details, reference_doctor_appt;
    private ArrayList<Admin_Payment_Class> current_payment;
    private ArrayList<DataSnapshot> data_payment;
    private String email;
    private Date d1, d2;
    private Admin_Payment_Show_Adapter adapter;
    private EditText search;
    private Context mcontext;

    public Admin_Payments_Current() {

    }

    public static Admin_Payments_Current getInstance() {
        Admin_Payments_Current currentFragment = new Admin_Payments_Current();
        return currentFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.row_current, container, false);

        search = (EditText) view.findViewById(R.id.editTextSearch_current);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        current_payment = new ArrayList<>();
        data_payment = new ArrayList<>();
        reference = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Admin_Payment");
        reference_booking = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Chosen_Slots");
        reference_patient = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Chosen_Slots");
        reference_details = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Patient_Details");
        reference_doctor_appt = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Appointments");

        reference.child("Payment0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    current_payment = new ArrayList<>();
                    data_payment = new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                            for (DataSnapshot snapshot3 : snapshot2.getChildren()) {
                                Admin_Payment_Class payment_data = snapshot3.getValue(Admin_Payment_Class.class);
                                current_payment.add(payment_data);
                                data_payment.add(snapshot3);
                            }
                        }
                    }
                    adapter = new Admin_Payment_Show_Adapter(current_payment);
                    recyclerView.setAdapter(adapter);

                    ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            new AlertDialog.Builder(viewHolder.itemView.getContext())
                                    .setMessage("Do you want to mark this Payment as Done? or Cancel the Appointment")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int position = viewHolder.getAdapterPosition();
                                            DataSnapshot data = data_payment.get(position);
                                            Admin_Payment_Class payment_class = current_payment.get(position);
                                            reference.child("Payment1").child(payment_class.getPhone()).child(payment_class.getDate()).child(payment_class.getTime()).setValue(payment_class);
                                            reference.child("Payment1").child(payment_class.getPhone()).child(payment_class.getDate()).child(payment_class.getTime()).child("payment").setValue(1);
                                            data.getRef().removeValue();
                                            Patient_Details details = new Patient_Details(payment_class.getPhone(), payment_class.getName());
                                            String encoded_email = payment_class.getEmail().replace(".", ",");
                                            reference_details.child(encoded_email).child(payment_class.getPhone()).setValue(details);
                                            reference_patient.child(payment_class.getPhone()).child(encoded_email).child(payment_class.getDate()).child(payment_class.getTime()).child("payment").setValue(1);
                                            reference_patient.child(payment_class.getPhone()).child(encoded_email).child(payment_class.getDate()).child(payment_class.getTime()).child("question").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String ques = " ";
                                                    if (snapshot.exists()) {
                                                        ques = snapshot.getValue(String.class);
                                                    }
                                                    Appointment_notif appointment_notif = new Appointment_notif("1", payment_class.getDate(), payment_class.getTime(), ques, payment_class.getPhone(), payment_class.getName());
                                                    reference_doctor_appt.child(encoded_email).child(payment_class.getDate()).child(payment_class.getTime()).setValue(appointment_notif);
                                                    String check = payment_class.getTime().split(" - ", 5)[0];
                                                    Booking_Appointments booking = new Booking_Appointments(1, payment_class.getPhone());
                                                    reference_booking.child(encoded_email).child(payment_class.getDate()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            if (snapshot.exists()) {
                                                                String slot_val = "";
                                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                                    String item = dataSnapshot.getKey();
                                                                    int s = Integer.parseInt(item.split(" - ", 5)[0]);
                                                                    int e = Integer.parseInt(item.split(" - ", 5)[1]);
                                                                    int t = Integer.parseInt(payment_class.getTime().split(":", 5)[0]);
                                                                    if (s <= t && t < e) {
                                                                        slot_val = item;
                                                                        break;
                                                                    }
                                                                }
                                                                reference_booking.child(encoded_email).child(payment_class.getDate()).child(slot_val).child(check).child("phone").setValue(payment_class.getPhone());
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });


                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int position = viewHolder.getAdapterPosition();
                                            DataSnapshot data = data_payment.get(position);
                                            Admin_Payment_Class payment_class = current_payment.get(position);
                                            reference.child("Payment1").child(payment_class.getPhone()).child(payment_class.getDate()).child(payment_class.getTime()).setValue(payment_class);
                                            reference.child("Payment1").child(payment_class.getPhone()).child(payment_class.getDate()).child(payment_class.getTime()).child("payment").setValue(2);
                                            data.getRef().removeValue();
                                            String encoded_email = payment_class.getEmail().replace(".", ",");
                                            reference_patient.child(payment_class.getPhone()).child(encoded_email).child(payment_class.getDate()).child(payment_class.getTime()).child("payment").setValue(2);
                                            String check = payment_class.getTime().split(" - ", 5)[0];
                                            Booking_Appointments booking = new Booking_Appointments(0, "null");
                                            reference_booking.child(encoded_email).child(payment_class.getDate()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.exists()) {
                                                        String slot_val = "";
                                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                            String item = dataSnapshot.getKey();
                                                            int s = Integer.parseInt(item.split(" - ", 5)[0]);
                                                            int e = Integer.parseInt(item.split(" - ", 5)[1]);
                                                            int t = Integer.parseInt(payment_class.getTime().split(":", 5)[0]);
                                                            if (s <= t && t < e) {
                                                                slot_val = item;
                                                                break;
                                                            }
                                                        }
                                                        if (!(slot_val.equals(""))) {
                                                            reference_booking.child(encoded_email).child(payment_class.getDate()).child(slot_val).child(check).setValue(booking);
                                                            String finalSlot_val = slot_val;
                                                            reference_booking.child(encoded_email).child(payment_class.getDate()).child(slot_val).child("Count").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    int count = snapshot.getValue(Integer.class);
                                                                    count = count - 1;
                                                                    reference_booking.child(encoded_email).child(payment_class.getDate()).child(finalSlot_val).child("Count").setValue(count);
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    };
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                } else {
                    if (mcontext != null) {
                        Toast.makeText(mcontext, "There are no Payments!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void filter(String text) {

        ArrayList<Admin_Payment_Class> filterdNames = new ArrayList<>();
        for (Admin_Payment_Class data : current_payment) {
            //if the existing elements contains the search input
            if (data.getDate().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(data);
            }
        }
        adapter.filterList(filterdNames);
    }

}
