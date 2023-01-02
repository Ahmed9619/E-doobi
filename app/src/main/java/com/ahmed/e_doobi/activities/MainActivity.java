package com.ahmed.e_doobi.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.e_doobi.R;
import com.ahmed.e_doobi.models.MyOrder;
import com.ahmed.e_doobi.models.MyOrdersAdapter;
import com.ahmed.e_doobi.models.OnOrderClickedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvOrders;
    MyOrdersAdapter mAdapter;
    private ArrayList<MyOrder> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mData = new ArrayList<>();
        mAdapter = new MyOrdersAdapter();
        rvOrders = findViewById(R.id.rvOrders);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v->{
            openActivityAddOrder();
        });

        populateData();
        setupRecyclerView();



    }

    private void populateData() {

        MyOrder o;

        o = new MyOrder();
        o.setClothType("T-shirt");
        o.setClothCount("5");
        mData.add(o);

        o = new MyOrder();
        o.setClothType("Hoodie");
        o.setClothCount("1");
        mData.add(o);

        o = new MyOrder();
        o.setClothType("Dishdasha");
        o.setClothCount("2");
        mData.add(o);

        o = new MyOrder();
        o.setClothType("Koomah");
        o.setClothCount("3");
        mData.add(o);

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
        Intent AddOrderIntent = new Intent(MainActivity.this, UpdateActivity.class);
        startActivity(AddOrderIntent);
    }
}