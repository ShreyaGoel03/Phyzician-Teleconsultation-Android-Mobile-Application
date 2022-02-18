package com.example.ahujaclinic;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Contact extends AppCompatActivity {

    private LottieAnimationView location,email,whatsapp,call,skype,link;
    private String phone;
    private String CurrentLocation,DestLocation;
    private TextView loc1,loc2;


    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        location = findViewById(R.id.location_animation_view);
        email = findViewById(R.id.email_animation_view);
        whatsapp = findViewById(R.id.whatsapp_animation_view);
        call = findViewById(R.id.call_animation_view);
        skype = findViewById(R.id.skype_animation_view);
        link = findViewById(R.id.websiteLink);
        loc1=findViewById(R.id.textView19);
        DestLocation=  loc1.getText().toString().trim();
        Toast.makeText(Contact.this, "Click on the icons to contact!", Toast.LENGTH_LONG).show();
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
            }
        });

        skype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
            }
        });


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] to = {""};
                String[] cc = {""};

                Intent email = new Intent(Intent.ACTION_SEND);

                email.putExtra(Intent.EXTRA_EMAIL,to);
                email.putExtra(Intent.EXTRA_CC,cc);

                email.putExtra(Intent.EXTRA_SUBJECT,"Consultation Regarding %Problem%");

                email.putExtra(Intent.EXTRA_TEXT, "Type your query here");

                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email,"Choose an email client"));

                //need this to prompts email client only.
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Contact.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getExactLocation();
                } else {
                    //when permission denied
                    ActivityCompat.requestPermissions(Contact.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = "";
                if(phone.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Number !",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(isPermissionGranted()){
                        String s = "tel:" + phone;
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(s));
                        startActivity(intent);
                    }
                }



            }
        });



        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = "";
                if(num.length()==10){
                    num = "91"+num;
                }
                String text = "Hello";
                try {
                    Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                    sharingIntent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + num + "&text" + text));
                    startActivity(Intent.createChooser(sharingIntent, "Chat with Doctor!"));
                }
                catch(Exception e){
                    Intent I =new Intent(Intent.ACTION_VIEW);
                    I.setData(Uri.parse("smsto:"));
                    I.putExtra("address", num);
                    startActivity(Intent.createChooser(I, "Chat with Doctor!"));
                }
            }
        });
    }
    private boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else {

            return true;
        }
    }


    private void getExactLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(Contact.this, Locale.getDefault());

                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        CurrentLocation=addressList.get(0).getAddressLine(0);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(!CurrentLocation.equals("null")) {
                    String source = CurrentLocation.toString();
                    String destination = DestLocation.toString();
                    if (source.equals("") && destination.equals("")) {
                        Toast.makeText(getApplicationContext(), "Enter both locations ! ", Toast.LENGTH_SHORT).show();
                    } else {
                        displayTrack(source, destination);
                    }
                }
            }
        });
    }

    private void displayTrack(String source, String destination) {
        // if the device does not have a map installed , redirect it to playstore
        try
        {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + source + "/" + destination);
            Intent intent = new  Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        catch(ActivityNotFoundException e)
        {
            // when google map is not installed
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
