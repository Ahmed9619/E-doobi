package com.ahmed.e_doobi.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmed.e_doobi.R;

import java.util.ArrayList;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyViewHolder> {

    private OnOrderClickedListener mListener;
    private ArrayList<MyOrder> mOrderArrayList;


    public MyOrdersAdapter() {
        mOrderArrayList = new ArrayList<>();
    }

    public void update(ArrayList<MyOrder> newOrders) {
        mOrderArrayList = newOrders;
        notifyItemRangeChanged(0, newOrders.size());
    }

    public void update(MyOrder newOrderItem) {
        mOrderArrayList.add(newOrderItem);
        int position = mOrderArrayList.indexOf(newOrderItem);
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_order, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyOrder orderObj = mOrderArrayList.get(position);
        holder.tvClothType.setText(orderObj.getClothType());
        String count = orderObj.getClothQuantity() + "";
        holder.tvClothCount.setText(count);

        holder.itemView.setOnClickListener(v->{
            mListener.onOrderClicked(orderObj);
        });

    }

    @Override
    public int getItemCount() {
        return mOrderArrayList.size();
    }



    public void setOnOrderClickedListener(OnOrderClickedListener listener){
        mListener = listener;
    }

    public void clear() {
        int size = mOrderArrayList.size();
        mOrderArrayList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivClothIcon;
        TextView tvClothType;
        TextView tvClothCount;
        ImageView ivDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivClothIcon = itemView.findViewById(R.id.ivClothIcon);
            tvClothType = itemView.findViewById(R.id.tvClothType);
            tvClothCount = itemView.findViewById(R.id.tvClothCount);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
