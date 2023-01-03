package com.ahmed.e_doobi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmed.e_doobi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG_LOGIN = "TAG_LOGIN";


    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private EditText etEmail;
    private EditText etPassword;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        mCurrentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            verify();
        });

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            openActivityRegister();
        });


    }


    private void verify() {

        boolean isEmailOk = true;
        boolean isPasswordOk = true;
        //check email Pattern
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();


        if (email.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please write your email", Toast.LENGTH_SHORT).show();
            isEmailOk = false;
        } else if (email.matches(emailPattern)) {
            isEmailOk = true;
        } else {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            isEmailOk = false;
        }

        if (password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please write your password", Toast.LENGTH_SHORT).show();
            isPasswordOk = false;
        } else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "invalid password", Toast.LENGTH_SHORT).show();
            isPasswordOk = false;
        }

        if (isEmailOk && isPasswordOk) {
            login(email, password);
        }

    }

    private void login(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG_LOGIN, "signInWithEmail:success");
                        mCurrentUser = mAuth.getCurrentUser();

                        openActivityMain();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG_LOGIN, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void openActivityMain() {

        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }


    private void openActivityRegister() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }
}