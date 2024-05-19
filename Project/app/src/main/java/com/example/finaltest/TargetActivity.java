package com.example.finaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finaltest.dal.Database;
import com.example.finaltest.model.Target;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TargetActivity extends AppCompatActivity {
    private EditText txtHeight, txtWeight, txtTarget, txtAge;
    private Spinner spinnerR, spinnerSex;
    private Button btnSubmit;
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        initView();
    }

    private void initView(){
        txtHeight = findViewById(R.id.txtHeight);
        txtWeight = findViewById(R.id.txtWeight);
        txtTarget = findViewById(R.id.txtTarget);
        txtAge = findViewById(R.id.txtAge);
        spinnerR = findViewById(R.id.spinnerR);
        spinnerSex = findViewById(R.id.spinnerSex);
        btnSubmit = findViewById(R.id.btnSubmit);

        spinnerR.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,
                getResources().getStringArray(R.array.chi_so_r)));

        spinnerSex.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,
                getResources().getStringArray(R.array.gioi_tinh)));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String heightString = txtHeight.getText().toString().trim();
                String weightString = txtWeight.getText().toString().trim();
                String weightTargetString = txtTarget.getText().toString().trim();
                String ageString = txtAge.getText().toString().trim();

                if(heightString == null || heightString.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui long nhap chieu cao cua ban!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(weightString == null || heightString.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui long nhap can nang cua ban!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(weightTargetString == null || heightString.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui long nhap can nang mong muon cua ban!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ageString == null || heightString.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui long nhap tuoi cua ban!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double height = 0, weight = 0, weightTarget = 0;
                int age = 1;

                try {
                    height = Double.parseDouble(heightString);
                    weight = Double.parseDouble(weightString);
                    weightTarget = Double.parseDouble(weightTargetString);
                    age = Integer.parseInt(ageString);
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Vui long nhap so!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double R = 1.2;
                String itemR = spinnerR.getSelectedItem().toString().trim();
                switch (itemR){
                    case "Người ít vận động":
                        R = 1.2;
                        break;
                    case "Người vận động nhẹ":
                        R = 1.375;
                        break;
                    case "Người vận động vừa(tập luyện 3 – 5 lần/tuần)":
                        R = 1.55;
                        break;
                    case "Người vận động nặng(tập luyện 6 – 7 lần/tuần)":
                        R = 1.725;
                        break;
                    case "Người vận động rất nặng(2 lần/ngày)":
                        R = 1.9;
                        break;
                }

                int sex = spinnerSex.getSelectedItem().toString().trim().equalsIgnoreCase("Nam")?1:0;
                Date currentDate = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String date = formatter.format(currentDate);

                Target newTarget = new Target(userId,height,
                        weight, weightTarget,
                        age, R, sex, date);
                Database db = new Database(getApplicationContext());
                long tmp = db.createTarget(newTarget);

                if(tmp > -1){
                    startActivity(new Intent(TargetActivity.this, MainActivity.class));
                }
            }
        });
    }
}