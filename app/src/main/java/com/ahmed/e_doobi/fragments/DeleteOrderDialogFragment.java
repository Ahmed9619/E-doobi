package com.ahmed.e_doobi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.ahmed.e_doobi.R;
import com.ahmed.e_doobi.models.ClothTypesArray;
import com.ahmed.e_doobi.models.MyFirebaseKeys;
import com.ahmed.e_doobi.models.MyOrder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DeleteOrderDialogFragment extends DialogFragment {
    public static final String TAG = "DeleteOrderDialogFragment";
    public static final String ORDER_OBJ = "order_obj";
    private static final String TAG_ORDER = "TAG_ORDER";

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private MyOrder mOrderObj;
    private Context mContext;

    public DeleteOrderDialogFragment() {
        // Required empty public constructor
    }

    public static DeleteOrderDialogFragment newInstance(MyOrder mOrderObj) {
        DeleteOrderDialogFragment fragment = new DeleteOrderDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ORDER_OBJ, mOrderObj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrderObj = (MyOrder) getArguments().getSerializable(ORDER_OBJ);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_delete_order_dialog, container, false);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        TextView tvClothType = parentView.findViewById(R.id.tvClothType);
        TextView tvClothCount = parentView.findViewById(R.id.tvClothCount);
        ImageView ivClothIcon = parentView.findViewById(R.id.ivClothIcon);

        tvClothType.setText(mOrderObj.getClothType());
        String count = mOrderObj.getClothQuantity() + "";
        tvClothCount.setText(count);

        ClothTypesArray clothTypesArray = new ClothTypesArray();
        int icon = clothTypesArray.getClothTypesMap().get(mOrderObj.getClothType());
        ivClothIcon.setImageResource(icon);


        Button btnDelete = parentView.findViewById(R.id.btnDelete);
        Button btnCancel = parentView.findViewById(R.id.btnCancel);

        btnDelete.setOnClickListener(view -> {
            deleteDBOrder();
        });

        btnCancel.setOnClickListener(view -> {
            dismiss();
        });

        return parentView;
    }


    private void deleteDBOrder() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(MyFirebaseKeys.users.toString())
                .child(mCurrentUser.getUid())
                .child(MyFirebaseKeys.orders.toString())
                .child(mOrderObj.getId());


        myRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG_ORDER, "Order Updated Successfully");
                dismiss();

            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG_ORDER, "Order Update:failure", task.getException());
                Toast.makeText(mContext, "Order Update failed.",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

}