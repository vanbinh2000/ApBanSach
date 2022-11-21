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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.adapter.RecyclerviewPayment;
import tdc.edu.vn.apbansach.model.Products;
import tdc.edu.vn.apbansach.model.User;

public class Activity_payment extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerviewPayment recyclerviewPayment;
    TextView txtfullname;
    TextView txtTotal_price;
    TextView txtDatepayment;
    Button btnBack;
    FirebaseAuth mAuth;
    ArrayList<Products> productsArrayList;
    FirebaseDatabase firebaseDatabase;
    int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
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
                    txtfullname.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        displayCartItemPayment();

        txtTotal_price();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String saveCurrentDate = simpleDateFormat.format(calendar.getTime());
        txtDatepayment.setText(saveCurrentDate);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Task<Void> reference = firebaseDatabase.getReference("Cart").child(mAuth.getCurrentUser().getUid())
                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(Activity_payment.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    private void txtTotal_price() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        DatabaseReference userID = reference.child("Cart").child(mAuth.getCurrentUser().getUid());
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        userID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    txtTotal_price.setVisibility(View.VISIBLE);
                    amount = 0;
                    txtTotal_price.setText(decimalFormat.format(Integer.valueOf(amount)) + " Đ");
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
                                    txtTotal_price.setText(decimalFormat.format(Integer.valueOf(amount)) + " Đ");
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

    public void setControl() {
        txtfullname = findViewById(R.id.idname_payment);
        txtTotal_price = findViewById(R.id.idtotalprice_payment);
        txtDatepayment = findViewById(R.id.iddate_payment);
        btnBack = findViewById(R.id.btnBackPayment);
    }

    private void displayCartItemPayment() {
        productsArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.idRecyclerview_payment);
        recyclerviewPayment = new RecyclerviewPayment(productsArrayList, this, R.layout.listview_payment);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerviewPayment);
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
                            recyclerviewPayment.notifyDataSetChanged();
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
