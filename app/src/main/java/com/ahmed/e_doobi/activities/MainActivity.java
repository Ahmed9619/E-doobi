package com.ahmed.e_doobi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.e_doobi.R;
import com.ahmed.e_doobi.models.MyFirebaseKeys;
import com.ahmed.e_doobi.models.MyOrder;
import com.ahmed.e_doobi.models.MyOrdersAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_MAIN = "TAG_MAIN";

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;



    private RecyclerView rvOrders;
    MyOrdersAdapter mAdapter;
    private ArrayList<MyOrder> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();


        mData = new ArrayList<>();
        mAdapter = new MyOrdersAdapter();
        rvOrders = findViewById(R.id.rvOrders);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            openActivityAddOrder();
        });

        readDBOrders();

        setupRecyclerView();


    }

    private void readDBOrders() {


        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(MyFirebaseKeys.users.toString())
                .child(mCurrentUser.getUid())
                .child(MyFirebaseKeys.orders.toString());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                mAdapter.clear();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    MyOrder value = d.getValue(MyOrder.class);
                    assert value != null;
                    mAdapter.update(value);
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG_MAIN, "Failed to read value.", error.toException());
            }
        });
    }


    private void setupRecyclerView() {
        mAdapter.update(mData);
        // Attach the adapter to the recyclerview to populate items
        rvOrders.setAdapter(mAdapter);

        //set on itemClickListener
        mAdapter.setOnOrderClickedListener(this::openActivityUpdateOrder);

        // Set layout manager to position the items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvOrders.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(this, layoutManager.getOrientation());
        rvOrders.addItemDecoration(divider);
    }

    private void openActivityAddOrder() {
        Intent AddOrderIntent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(AddOrderIntent);
    }

    private void openActivityUpdateOrder(MyOrder orderObj) {
        Intent updateOrderIntent = new Intent(MainActivity.this, UpdateActivity.class);
        updateOrderIntent.putExtra("orderObj", orderObj);
        startActivity(updateOrderIntent);
    }
}