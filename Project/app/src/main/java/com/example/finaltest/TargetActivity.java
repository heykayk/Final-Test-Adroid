package com.example.finaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TargetActivity extends AppCompatActivity {
    private EditText txtHeight, txtWeight, txtTarget, txtAge;
    private Spinner spinnerR, spinnerSex;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        initView();
    }

    private void initView(){
        txtHeight = findViewById(R.id.txtHeight);
        txtWeight = findViewById(R.id.txtWeight);
        txtTarget = findViewById(R.id.txtTarget);
        spinnerR = findViewById(R.id.spinnerR);
        spinnerSex = findViewById(R.id.spinnerSex);
        btnSubmit = findViewById(R.id.btnSubmit);

        spinnerR.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,
                getResources().getStringArray(R.array.chi_so_r)));

        spinnerSex.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,
                getResources().getStringArray(R.array.gioi_tinh)));
    }
}