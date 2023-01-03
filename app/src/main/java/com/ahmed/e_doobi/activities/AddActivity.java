package com.ahmed.e_doobi.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmed.e_doobi.R;
import com.ahmed.e_doobi.models.MyFirebaseKeys;
import com.ahmed.e_doobi.models.MyOrder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    private static final String TAG_ORDER = "TAG_ORDER";
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;


    private ArrayList<String> mClothTypesArrayList;

    private String mSelectedClothType;
    private Spinner spClothType;
    private EditText etClothQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        populateClothTypesArray();

        spClothType = findViewById(R.id.spClothType);
        etClothQuantity = findViewById(R.id.etClothQuantity);


        spClothType.setOnItemSelectedListener(onItemSelectedListener());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_spinner, mClothTypesArrayList);
        spClothType.setAdapter(adapter);

        Button btnAddNewOrder = findViewById(R.id.btnAddNewOrder);
        btnAddNewOrder.setOnClickListener(view -> {
            verify();
        });


    }

    private void populateClothTypesArray() {
        mClothTypesArrayList = new ArrayList<>();
        mClothTypesArrayList.add("Omani Mussar");
        mClothTypesArrayList.add("Omani Dishdasha");
        mClothTypesArrayList.add("T-Shirt");
        mClothTypesArrayList.add("Jacket");
        mClothTypesArrayList.add("Suit");
        mClothTypesArrayList.add("Trousers");
        mClothTypesArrayList.add("Bed Sheet");
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener() {
        AdapterView.OnItemSelectedListener callback = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedClothType = mClothTypesArrayList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        return callback;
    }

    private void verify() {
        boolean isQuantityOk = true;

        String stringQuantity = etClothQuantity.getText().toString();


        if (stringQuantity.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please write the quantity", Toast.LENGTH_SHORT).show();
            isQuantityOk = false;
        } else if (Integer.parseInt(stringQuantity) > 20 || Integer.parseInt(stringQuantity) <= 0) {
            Toast.makeText(getApplicationContext(), "Invalid quantity", Toast.LENGTH_SHORT).show();
            isQuantityOk = false;
        }

        if (isQuantityOk) {
            createDBOrder(stringQuantity);
        }
    }

    private void createDBOrder(String stringQuantity) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(MyFirebaseKeys.users.toString())
                        .child(mCurrentUser.getUid())
                        .child(MyFirebaseKeys.orders.toString());
        String orderId = myRef.push().getKey();

        MyOrder myOrder = new MyOrder(orderId, mSelectedClothType, stringQuantity);

        myRef.child(orderId).setValue(myOrder).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG_ORDER, "Order Created Successfully");
                finish();

            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG_ORDER, "Order Creation:failure", task.getException());
                Toast.makeText(AddActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

}