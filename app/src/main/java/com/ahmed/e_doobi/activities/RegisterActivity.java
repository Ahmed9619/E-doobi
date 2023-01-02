package com.ahmed.e_doobi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmed.e_doobi.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etPhone;
    private EditText etPassword;
    private EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            verify();
        });

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            openActivityLogin();
        });


    }


    private void verify() {
        String fullName = etFullName.getText().toString();
        String phoneNumber = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        register(fullName, phoneNumber, password);

    }

    private void register(String fullName, String phoneNumber, String password) {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }


    private void openActivityLogin() {
        Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(registerIntent);
        finish();
    }
}