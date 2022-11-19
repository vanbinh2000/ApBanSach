package tdc.edu.vn.apbansach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.adapter.RecyclerViewAdapter_Cart;
import tdc.edu.vn.apbansach.model.Products;

public class CartActivty extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Products> productsArrayList;
    RecyclerViewAdapter_Cart recyclerViewAdapter_cart;
    FirebaseAuth mAuth;
    Button btnThanhtoan;
    TextView tvThanhtien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_activty);
        mAuth = FirebaseAuth.getInstance();
        setControl();
        setEvent();
    }

    private void setEvent() {
        displayCartItem();
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
//                    Log.d("TAG", productsID);
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
    }
}