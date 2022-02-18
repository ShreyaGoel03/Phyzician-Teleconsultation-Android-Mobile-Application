package com.example.ahujaclinic;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class verifyOtp extends AppCompatActivity {


    private EditText inputOne, inputTwo, inputThree, inputFour, inputFive, inputSix;
    private Button verifyButton;
    private String backendotp;
    private Toast backToast;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        final Button verifyButton = findViewById(R.id.submitButton);

        inputOne = findViewById(R.id.inputOtp1);
        inputTwo = findViewById(R.id.inputOtp2);
        inputThree = findViewById(R.id.inputOtp3);
        inputFour = findViewById(R.id.inputOtp4);
        inputFive = findViewById(R.id.inputOtp5);
        inputSix = findViewById(R.id.inputOtp6);

        TextView textMobile = findViewById(R.id.textMobile);
        textMobile.setText(String.format(
                "+91-%s", getIntent().getStringExtra("mobile")
        ));

        String new_mobile = "+91" + getIntent().getStringExtra("mobile");
        backendotp = getIntent().getStringExtra("backendotp");
        final ProgressBar progressVerifyBar = findViewById(R.id.progressBarVerifyingOtp);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!inputOne.getText().toString().trim().isEmpty() && !inputTwo.getText().toString().trim().isEmpty() && !inputThree.getText().toString().trim().isEmpty() && !inputFour.getText().toString().trim().isEmpty() && !inputFive.getText().toString().trim().isEmpty() && !inputSix.getText().toString().trim().isEmpty()) {
                    String entercodeotp = inputOne.getText().toString() + inputTwo.getText().toString() + inputThree.getText().toString() + inputFour.getText().toString() + inputFive.getText().toString() + inputSix.getText().toString();

                    if (backendotp != null) {
                        progressVerifyBar.setVisibility(View.VISIBLE);
                        verifyButton.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                backendotp, entercodeotp
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressVerifyBar.setVisibility(View.INVISIBLE);
                                        verifyButton.setVisibility(View.VISIBLE);

                                        if (task.isSuccessful()) {
//
                                            Intent intent = new Intent(getApplicationContext(), Patient.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            Patient_Phone_No patientPhoneNo = new Patient_Phone_No(new_mobile);
                                            Patient_Session_Management session_management = new Patient_Session_Management(verifyOtp.this);
                                            session_management.saveSession(patientPhoneNo);
                                        } else {
                                            Toast.makeText(verifyOtp.this, "Enter the correct OTP", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(verifyOtp.this, "Please check internet connection", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(verifyOtp.this, "Incorrect OTP", Toast.LENGTH_LONG).show();
                }
            }
        });

        numberOtpMove();

        findViewById(R.id.resendOtp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        verifyOtp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(verifyOtp.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
                            }


                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                backendotp = newbackendotp;
                                Toast.makeText(verifyOtp.this, "OTP sent successfully", Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }
        });


    }

    private void numberOtpMove() {

        inputOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputTwo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputThree.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputFour.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputFive.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputFive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputSix.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            finishAffinity();
            super.onBackPressed();
            Toast.makeText(this, "Please wait for 60 seconds for other OTP Login!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press Back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

}