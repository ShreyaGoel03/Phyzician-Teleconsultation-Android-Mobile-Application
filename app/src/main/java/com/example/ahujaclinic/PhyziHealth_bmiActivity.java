package com.example.ahujaclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class PhyziHealth_bmiActivity extends AppCompatActivity {

    TextInputEditText weight,height;
    TextView result;
    String calculation,bmiResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        weight=findViewById(R.id.inputWeight);
        height=findViewById(R.id.inputHeight);
        result=findViewById(R.id.resultText);

    }

    public void calculateBMI(View view) {
        String wt = weight.getText().toString();
        String ht = height.getText().toString();

        float wtValue = Float.parseFloat(wt);
        float htValue = Float.parseFloat(ht)/100;

        float bmi = wtValue / (htValue * htValue);

        if(bmi<16)
        {
            bmiResult = "Severly Under Weight";
        }else if(bmi<18.5)
        {
            bmiResult="Under Weight";
        }else if(bmi >= 18.5 && bmi < 24.9)
        {
            bmiResult = "Normal Weight";
        }else if(bmi>=25 && bmi < 29.9)
        {
            bmiResult = "Over Weight";
        }
        else
            bmiResult="Obese";

        calculation = "BMI : "+bmi+"\n\n"+bmiResult;

        result.setVisibility(View.VISIBLE);
        result.setText(calculation);
    }
}