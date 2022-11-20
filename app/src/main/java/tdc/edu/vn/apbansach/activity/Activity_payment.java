package tdc.edu.vn.apbansach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.adapter.RecyclerViewAdapter_Cart;
import tdc.edu.vn.apbansach.adapter.RecyclerviewPayment;
import tdc.edu.vn.apbansach.model.Products;

public class Activity_payment extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerviewPayment recyclerviewPayment;
    TextView txtfullname;
    TextView txtTotal_price;
    TextView txtDatepayment;
    FirebaseAuth mAuth;
    ArrayList<Products> productsArrayList;
    RecyclerviewPayment recyclerViewAdapterpayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mAuth = FirebaseAuth.getInstance();
        displayCartItem();
        setControl();

    }
    public void setControl()
    {
        txtfullname = findViewById(R.id.idname_payment);
        txtTotal_price = findViewById(R.id.idtotalprice_payment);
        txtDatepayment = findViewById(R.id.iddate_payment);
    }
    private void displayCartItem() {
        productsArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerCartView);
        recyclerViewAdapterpayment = new RecyclerviewPayment(productsArrayList, this, R.layout.listview_payment);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapterpayment);
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
                            recyclerViewAdapterpayment.notifyDataSetChanged();
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
}