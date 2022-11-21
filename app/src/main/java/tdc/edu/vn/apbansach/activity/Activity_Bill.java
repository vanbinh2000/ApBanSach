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

import java.text.DecimalFormat;
import java.util.ArrayList;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.adapter.RecyclerViewAdapter_Cart;
import tdc.edu.vn.apbansach.adapter.RecyclerviewPayment;
import tdc.edu.vn.apbansach.model.Payment;
import tdc.edu.vn.apbansach.model.Products;
import tdc.edu.vn.apbansach.model.User;

public class Activity_Bill extends AppCompatActivity {
    private TextView txtnameuser_bill, txtnampr_bill, txtprice_bill
            , txttotalprice_bill, txtdatebill, txtquantity_bill;
    private ArrayList<Payment> listPayment;
    private FirebaseAuth mAuth;
    private   FirebaseDatabase firebaseDatabase;
    private  ArrayList<Products> productsArrayList;
    private RecyclerView recyclerView;
    private RecyclerviewPayment recyclerviewPayment;
    private String total_price, datepayment, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        setControl();
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###Đ");


        total_price = getIntent().getStringExtra("totalprice");
        datepayment = getIntent().getStringExtra("datepayment");
        quantity = getIntent().getStringExtra("quantity");
        txttotalprice_bill.setText(decimalFormat.format(Integer.valueOf(total_price)));
        txtdatebill.setText(datepayment);
        txtquantity_bill.setText(quantity);

        loadUserName();
        loadBill();

    }
    public void setControl()
    {
        productsArrayList = new ArrayList<>();
        listPayment = new ArrayList<>();
        txtnameuser_bill = findViewById(R.id.idnameuser_bill);
        txtnampr_bill = findViewById(R.id.idnamepr_bill);
        txtprice_bill = findViewById(R.id.idprice_bill);
        txttotalprice_bill = findViewById(R.id.idtotalprice_bill);
        txtdatebill = findViewById(R.id.iddate_bill);
        txtquantity_bill = findViewById(R.id.idquantity_bill);


    }
    public void loadBill(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        DatabaseReference userID = reference.child("Payment").child(mAuth.getCurrentUser().getUid());
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
                                Products products = dataSnapshot.getValue(Products.class);
                                if (dataSnapshot.getKey().equals(productsID)) {

                                    String nameproducts = products.getProducts_name();
                                    String priceproducts = products.getPrice();
                                    DecimalFormat decimalFormat = new DecimalFormat("#,###,###Đ");
                                    txtnampr_bill.setText(nameproducts);
                                    txtprice_bill.setText(decimalFormat.format(Integer.valueOf(priceproducts.trim())));
                                    Log.d("TAG", dataSnapshot.getKey());
                                }
                            }

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
    public void loadUserName()
    {
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        setControl();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userModel = snapshot.getValue(User.class);
                if (userModel != null) {
                    String name = userModel.getFullname();
                    txtnameuser_bill.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}