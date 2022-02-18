package com.example.ahujaclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedback extends AppCompatActivity {

    private TextView feedbackText;
    private EditText feedbackEdit;
    private RatingBar feedbackRating;
    private DatabaseReference feedback, prescription, user;
    private String date, email, time, phone;
    private Button submit;
    private float rating_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedbackEdit=findViewById(R.id.editFeedback);
        feedbackText=findViewById(R.id.feedback);
        feedbackRating=findViewById(R.id.ratingBar);
        submit=findViewById(R.id.sendFeedback);

        feedbackRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating == 0 || rating == 0.5)
                {
                    feedbackText.setText("Very Dissatisfied");
                }
                else if(rating == 1 || rating == 1.5)
                {
                    feedbackText.setText("Dissatisfied");
                }
                else if(rating == 2 || rating == 2.5)
                {
                    feedbackText.setText("Okay");
                }
                else if(rating == 3)
                {
                    feedbackText.setText("Okay!");
                }
                else if(rating == 3.5)
                {
                    feedbackText.setText("Satisfied");
                }
                else if(rating == 4|| rating == 4.5)
                {
                    feedbackText.setText("Satisfied");
                }
                else if(rating == 5)
                {
                    feedbackText.setText("Very Satisfied");
                }
                else
                {

                }
                rating_val = rating;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feedback_Model feedback_model = new Feedback_Model(rating_val, feedbackEdit.getText().toString().trim());
                feedback = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Feedback");
                prescription = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Prescription_By_Doctor");
                user = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
                email= (String) getIntent().getSerializableExtra("email");
                date=(String) getIntent().getSerializableExtra("date");
                time=(String) getIntent().getSerializableExtra("time");
                phone = (String) getIntent().getSerializableExtra("phone");
                prescription.child(email).child(phone).child(date).child(time).child("flag").setValue(1);
                feedback.child(email).child(phone).child(date).child(time).setValue(feedback_model);
                Toast.makeText(Feedback.this, "FeedBack Submitted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Feedback.this, Patient_side_prescription_recycler.class);
                intent.putExtra("email", email);
                user.child(email).child("name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.getValue(String.class);
                        intent.putExtra("dr_name",name);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}