package com.example.ahujaclinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.tabs.TabLayout;

public class Patient_Appointments extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);

        getTabs();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void getTabs()
    {
        final FragmentPagerAdapter fragmentPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                fragmentPagerAdapter.addFragment(Patient_PreviousApts.getInstance(),"Previous");
                fragmentPagerAdapter.addFragment(Patient_CurrentAppts.getInstance(),"Upcoming");

                viewPager.setAdapter(fragmentPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }





}