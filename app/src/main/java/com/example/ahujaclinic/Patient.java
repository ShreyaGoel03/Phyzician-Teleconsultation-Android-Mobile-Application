package com.example.ahujaclinic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class Patient extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toast backToast;
    private long backPressedTime;
    private DrawerLayout drawerLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        RecyclerView recyclerView_spec = (RecyclerView) findViewById(R.id.recycler_spec);
        ImageView all_doctors = (ImageView) findViewById(R.id.imageView_doc);
        SliderView sliderView = findViewById(R.id.slider);

        drawerLayout1 = findViewById(R.id.drawer_layout1);
        NavigationView navigationView1 = findViewById(R.id.nav_view1);
        Toolbar toolbar1 = findViewById(R.id.toolbar1);

        setSupportActionBar(toolbar1);

        navigationView1.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout1, toolbar1, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout1.addDrawerListener(toggle);
        toggle.syncState();
        navigationView1.setNavigationItemSelectedListener(Patient.this);
        navigationView1.setCheckedItem(R.id.nav_home);



        Integer [] specialisation_pic={R.drawable.infectious,R.drawable.dermavenereolepro,R.drawable.skin,R.drawable.diabetes,
                                        R.drawable.thyroid,R.drawable.hormone, R.drawable.immunology, R.drawable.rheuma, R.drawable.neuro, R.drawable.ophtha, R.drawable.cardiac, R.drawable.cancer,
                                        R.drawable.gastro, R.drawable.ent};

        String[] specialisation_type={"Infectious Disease","Dermatology & Venereology","Leprology","Endocrinology & Diabetes","Thyroid","Hormone","Immunology","Rheumatology","Neurology","Ophthalmology","Cardiac Sciences","Cancer Care / Oncology","Gastroenterology, Hepatology & Endoscopy","Ear Nose Throat"};

        ArrayList<Main_Specialisation> main_specialisations = new ArrayList<>();

        for(int i=0;i<specialisation_pic.length;i++){
           Main_Specialisation specialisation=new Main_Specialisation(specialisation_pic[i],specialisation_type[i]);
           main_specialisations.add(specialisation);

        }

        //design horizontal layout
        LinearLayoutManager layoutManager_spec=new LinearLayoutManager(
                Patient.this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView_spec.setLayoutManager(layoutManager_spec);
        recyclerView_spec.setItemAnimator(new DefaultItemAnimator());

        Specialist_Adapter specialist_adapter = new Specialist_Adapter(main_specialisations, Patient.this);
        recyclerView_spec.setAdapter(specialist_adapter);

        all_doctors.setOnClickListener(v -> {
            Intent intent=new Intent(Patient.this,Available_Doctors.class);
            intent.putExtra("flag",0+"");
            startActivity(intent);
        });

        Integer [] sliderDataArrayList={R.drawable.image1,R.drawable.image2,R.drawable.image3};

        ArrayList<Slider_Data> slider_data = new ArrayList<>();

        for (Integer integer : sliderDataArrayList) {

            Slider_Data slider_data_arr = new Slider_Data(integer);
            slider_data.add(slider_data_arr);
        }

        SliderAdapter adapter = new SliderAdapter(this, slider_data);

        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        sliderView.setSliderAdapter(adapter);

        sliderView.setScrollTimeInSec(3);

        sliderView.setAutoCycle(true);

        sliderView.startAutoCycle();

    }

    private void moveToMainpage() {
        Intent intent=new Intent(Patient.this,AskRole.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout1.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout1.closeDrawer(GravityCompat.START);
        }
        else
        {
            if(backPressedTime+2000>System.currentTimeMillis())
            {
                finishAffinity();
                backToast.cancel();
                super.onBackPressed();
                return;
            }
            else
            {
                backToast = Toast.makeText(getBaseContext(),"Press Back again to exit",Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, AskRole.class));
                break;
            case R.id.doctors:
                Intent intent=new Intent(Patient.this,Available_Doctors.class);
                intent.putExtra("flag",0+"");
                startActivity(intent);
                break;
            case R.id.appointments:
                startActivity(new Intent(Patient.this, Patient_Appointments.class));
                break;
            case R.id.chats:
                startActivity(new Intent(Patient.this, Patient_Chat_Display.class));
                break;
            case R.id.step:
                startActivity(new Intent(Patient.this, PhyziHealth.class));
                break;
            case R.id.settingsApp:
                Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent1.setData(Uri.parse("package:"+getApplicationContext().getPackageName()));
                startActivity(intent1);
                break;
            case R.id.website:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
                break;
            case R.id.facebook:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
                break;
            case R.id.twitter:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
                break;
            case R.id.linkedin:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("")));
                break;
            case R.id.logout:
                Patient_Session_Management session_management=new Patient_Session_Management(Patient.this);
                session_management.removeSession();
                moveToMainpage();
                break;
            case R.id.speciality_view_all:
                startActivity(new Intent(Patient.this,View_All_Speciality.class));
                break;

        }
        drawerLayout1.closeDrawer(GravityCompat.START);
        return true;
    }
}
