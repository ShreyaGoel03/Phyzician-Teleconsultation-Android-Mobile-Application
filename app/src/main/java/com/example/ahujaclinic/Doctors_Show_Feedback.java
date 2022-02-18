package com.example.ahujaclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Doctors_Show_Feedback extends AppCompatActivity {
    private String phone,date,time,name,email;

    private TextView feedbackText;
    private EditText feedbackEdit;
    private RatingBar feedbackRating;
    private DatabaseReference feedback, prescription, user;
    private float rating,rating_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors__show__feedback);
        feedbackEdit=findViewById(R.id.editFeedback);
        feedbackText=findViewById(R.id.feedback);
        feedbackRating=findViewById(R.id.ratingBar);
        date= (String) getIntent().getSerializableExtra("date");
        time= (String) getIntent().getSerializableExtra("time");
        name=(String) getIntent().getSerializableExtra("name");
        phone=(String) getIntent().getSerializableExtra("phone");

        Doctors_Session_Mangement doctors_session_mangement = new Doctors_Session_Mangement(this);
        email = doctors_session_mangement.getDoctorSession()[0].replace(".",",");
        feedback = FirebaseDatabase.getInstance("https://ahujaclinic-86055-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Doctors_Feedback");

        feedback.child(email).child(phone).child(date).child(time).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Feedback_Model feedback_model=snapshot.getValue(Feedback_Model.class);
                    feedbackEdit.setText(feedback_model.getFeedback());
                    rating=feedback_model.getRating();
                    rating = Float.parseFloat(String.valueOf(rating));
                    feedbackRating.setStepSize((float) 0.5);
                    feedbackRating.setRating(rating);
                    feedbackRating.setFocusable(false);

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}