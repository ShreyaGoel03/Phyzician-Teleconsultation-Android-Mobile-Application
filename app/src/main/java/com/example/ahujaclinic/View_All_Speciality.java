package com.example.ahujaclinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class View_All_Speciality extends AppCompatActivity {

    private RecyclerView recyclerView_spec_all;
    private ArrayList<Main_Specialisation> main_specialisations;
    private View_All_Adapter view_all_adapter;
    private TextView view_all;
    private EditText search_spec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all__speciality);
        search_spec=(EditText) findViewById(R.id.editTextSearch_spec);

        recyclerView_spec_all=(RecyclerView) findViewById(R.id.recycler_view_all_doc);

        Integer [] specialisation_pic={R.drawable.infectious,R.drawable.dermavenereolepro,R.drawable.skin,R.drawable.diabetes,
                R.drawable.thyroid,R.drawable.hormone, R.drawable.immunology, R.drawable.rheuma, R.drawable.neuro, R.drawable.ophtha, R.drawable.cardiac, R.drawable.cancer,
                R.drawable.gastro, R.drawable.ent};

        String[] specialisation_type={"Infectious Disease","Dermatology & Venereology","Leprology","Endocrinology & Diabetes","Thyroid","Hormone","Immunology","Rheumatology","Neurology","Ophthalmology","Cardiac Sciences","Cancer Care / Oncology","Gastroenterology, Hepatology & Endoscopy","Ear Nose Throat"};

        main_specialisations=new ArrayList<>();

        for(int i=0;i<specialisation_pic.length;i++){
            Main_Specialisation specialisation=new Main_Specialisation(specialisation_pic[i],specialisation_type[i]);
            main_specialisations.add(specialisation);

        }

        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView_spec_all.setLayoutManager(staggeredGridLayoutManager);
        recyclerView_spec_all.setItemAnimator(new DefaultItemAnimator());



        view_all_adapter =new View_All_Adapter(main_specialisations,View_All_Speciality.this);
        recyclerView_spec_all.setAdapter(view_all_adapter);

        search_spec.addTextChangedListener(new TextWatcher() {
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

    }

    private void filter(String text) {

        ArrayList<Main_Specialisation> filterdNames = new ArrayList<>();
        for (Main_Specialisation doc_data: main_specialisations) {
            //if the existing elements contains the search input
            if (doc_data.getSpecialisation_type().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(doc_data);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        view_all_adapter.filterList(filterdNames);
    }
}