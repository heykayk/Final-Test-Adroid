package com.example.finaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finaltest.dal.Database;
import com.example.finaltest.model.User;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUsername, txtPassword;
    private TextView txtSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtName);
        txtPassword = findViewById(R.id.txtPassword);
        txtSignup = findViewById(R.id.txtSignUp);

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistationActivity.class));
            }
        });
    }

    public void singin(View view){
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
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
        User user = db.login(new User(username, password));

        if(user == null){
            Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}