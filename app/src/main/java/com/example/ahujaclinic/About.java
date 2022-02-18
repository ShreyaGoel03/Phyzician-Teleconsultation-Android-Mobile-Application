package com.example.ahujaclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


import com.airbnb.lottie.LottieAnimationView;

public class About extends AppCompatActivity {

    private LottieAnimationView link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        link = findViewById(R.id.websiteLink);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://drahujaidclinic.com/")));
            }
        });
    }
}