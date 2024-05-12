package com.example.finaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);
    }

    public void signup(View view){
        startActivity(new Intent(RegistationActivity.this, MainActivity.class));
    }
    public void signin(View view){
        startActivity(new Intent(RegistationActivity.this, LoginActivity.class));
    }

}