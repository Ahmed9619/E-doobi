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
import com.ahmed.e_doobi.models.ClothTypesArray;
import com.ahmed.e_doobi.models.MyFirebaseKeys;
import com.ahmed.e_doobi.models.MyOrder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    private static final String TAG_ORDER = "TAG_ORDER";
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;



    private ArrayList<String> mClothTypesArrayList;

    private String mSelectedClothType;
    private Spinner spClothType;
    private EditText etClothQuantity;
    private MyOrder mOldOrderObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mOldOrderObj = (MyOrder) extras.getSerializable("orderObj");
        }

        populateClothTypesArray();

        spClothType = findViewById(R.id.spClothType);
        etClothQuantity = findViewById(R.id.etClothQuantity);


        spClothType.setOnItemSelectedListener(onItemSelectedListener());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_spinner, mClothTypesArrayList);
        spClothType.setAdapter(adapter);
        spClothType.setSelection(mClothTypesArrayList.indexOf(mOldOrderObj.getClothType()));

        etClothQuantity.setText(mOldOrderObj.getClothQuantity());

        Button btnUpdateOrder = findViewById(R.id.btnUpdateOrder);
        btnUpdateOrder.setOnClickListener(view -> {
            verify();
        });


    }


    private void populateClothTypesArray() {
        ClothTypesArray clothTypesArray = new ClothTypesArray();
        mClothTypesArrayList = clothTypesArray.getClothTypesArrayList();
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
            updateDBOrder(stringQuantity);
        }
    }

    private void updateDBOrder(String stringQuantity) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(MyFirebaseKeys.users.toString())
                .child(mCurrentUser.getUid())
                .child(MyFirebaseKeys.orders.toString())
                .child(mOldOrderObj.getId());

        MyOrder myOrder = new MyOrder(mOldOrderObj.getId(), mSelectedClothType, stringQuantity);

        myRef.setValue(myOrder).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG_ORDER, "Order Updated Successfully");
                finish();

            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG_ORDER, "Order Update:failure", task.getException());
                Toast.makeText(UpdateActivity.this, "Order Update failed.",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

}