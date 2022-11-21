package tdc.edu.vn.apbansach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.adapter.RecyclerViewAdapter_Cart;
import tdc.edu.vn.apbansach.model.Cart;
import tdc.edu.vn.apbansach.model.Payment;
import tdc.edu.vn.apbansach.model.Products;

public class CartActivty extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Products> productsArrayList;
    RecyclerViewAdapter_Cart recyclerViewAdapter_cart;
    FirebaseAuth mAuth;
    Button btnThanhtoan;
    TextView tvThanhtien, tvEmpty;
    int amount = 0;

    DatabaseReference fbProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_activty);
        mAuth = FirebaseAuth.getInstance();
        setControl();
        displayCartItem();
        totalAmount();
        setBtnAddPayment();
    }

    private void totalAmount() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        DatabaseReference userID = reference.child("Cart").child(mAuth.getCurrentUser().getUid());
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        userID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    amount = 0;
                    btnThanhtoan.setClickable(false);
                    tvThanhtien.setText(decimalFormat.format(Integer.valueOf(amount)) + " Đ");
                } else {
                    for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                        amount = 0;
                        String productID = productSnapshot.getKey().trim();
                        DatabaseReference productRef = reference.child("Products");
                        productRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.getKey().equals(productID)) {
                                        Products products = dataSnapshot.getValue(Products.class);
                                        amount = amount + Integer.parseInt(products.getPrice().trim());
                                    }
                                    tvThanhtien.setText(decimalFormat.format(Integer.valueOf(amount)) + " Đ");
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void displayCartItem() {
        productsArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerCartView);
        recyclerViewAdapter_cart = new RecyclerViewAdapter_Cart(this, R.layout.layout_item_in_cart, productsArrayList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter_cart);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        DatabaseReference userID = reference.child("Cart").child(mAuth.getCurrentUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    String productsID = productSnapshot.getKey().trim();
                    DatabaseReference productRef = reference.child("Products");
                    productRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.getKey().equals(productsID)) {
                                    Products products = dataSnapshot.getValue(Products.class);
                                    productsArrayList.add(products);
                                    Log.d("TAG", dataSnapshot.getKey());
                                }
                            }
                            recyclerViewAdapter_cart.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        userID.addListenerForSingleValueEvent(valueEventListener);
    }

    private void setControl() {
        tvThanhtien = findViewById(R.id.tvThanhtien);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnThanhtoan = findViewById(R.id.btnThanhToan);


    }
    public void setBtnAddPayment()
    {
      btnThanhtoan.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(CartActivty.this, Activity_payment.class);
              startActivity(intent);

            }
        });

    }


}