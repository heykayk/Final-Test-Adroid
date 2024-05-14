package com.example.finaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finaltest.dal.Database;
import com.example.finaltest.model.User;

public class RegistationActivity extends AppCompatActivity {
    private EditText txtName, txtFullname, txtPassword;
    private TextView txtSingIn;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        txtName = findViewById(R.id.txtName);
        txtFullname = findViewById(R.id.txtFullName);
        txtPassword = findViewById(R.id.txtPassword);
        txtSingIn = findViewById(R.id.txtSingin);

        txtSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistationActivity.this, LoginActivity.class));
            }
        });
    }

    public void signup(View view){
        String username = txtName.getText().toString();
        String password = txtPassword.getText().toString();
        String fullname = txtFullname.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(fullname)){
            Toast.makeText(this, "Enter fullname", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length()<6){
            Toast.makeText(this, "Password too short, enter minimum 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // check tài khoản người dùng tạo mới
        Database db = new Database(this);
        User user = db.createUser(new User(username, password, fullname));

        if(user == null){
            Toast.makeText(this, "Username đã được sử dụng!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent=new Intent(RegistationActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

}