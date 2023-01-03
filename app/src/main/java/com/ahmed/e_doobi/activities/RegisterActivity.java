package com.ahmed.e_doobi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmed.e_doobi.R;
import com.ahmed.e_doobi.models.MyFirebaseKeys;
import com.ahmed.e_doobi.models.MyUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG_REGISTER = "TAG_REGISTER";
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private EditText etFullName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;


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
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            openActivityLogin();
        });

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            verify();
        });


    }


    private void verify() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();


        boolean isFullNameOk = true;
        boolean isEmailOk = true;
        boolean isPasswordOk = true;
        boolean isConfirmPasswordOk = true;

        //check email Pattern
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (fullName.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please write your full name", Toast.LENGTH_SHORT).show();
            isFullNameOk = false;
        }


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


        if (confirmPassword.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please write your confirm password", Toast.LENGTH_SHORT).show();
            isConfirmPasswordOk = false;
        } else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "invalid confirm password", Toast.LENGTH_SHORT).show();
            isConfirmPasswordOk = false;
        } else if (!confirmPassword.equals(password)) {
            Toast.makeText(getApplicationContext(), "confirm password not equal to password", Toast.LENGTH_SHORT).show();
            isConfirmPasswordOk = false;
        }

        if (isFullNameOk && isEmailOk && isPasswordOk && isConfirmPasswordOk) {
            register(fullName, email, password);
        }


    }

    private void register(String fullName, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        mCurrentUser = mAuth.getCurrentUser();

                        createDBUser(fullName, email);

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG_REGISTER, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void createDBUser(String fullName, String email) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(MyFirebaseKeys.users.toString());

        MyUser myUser = new MyUser(mCurrentUser.getUid(), fullName, email);

        myRef.child(myUser.getId()).setValue(myUser).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG_REGISTER, "createUserWithEmail:success");
                openActivityMain();

            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG_REGISTER, "createUserWithEmail:failure", task.getException());
                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void openActivityMain() {
        Intent registerIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(registerIntent);
        finish();
    }

    private void openActivityLogin() {
        Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(registerIntent);
        finish();
    }
}