package tdc.edu.vn.apbansach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.model.Products;

public class Activity_detail extends AppCompatActivity {
   private ImageView imageView_detail;
   private TextView txtname_detail;
   private TextView txtdescription_detail;
   private TextView txtprice_detail;
   private ImageView imageView_back;
   private String productsID = "";
    Button btnAddCart;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        productsID = getIntent().getStringExtra("productsID");
        imageView_detail = findViewById(R.id.idimage_detail);
        txtname_detail = findViewById(R.id.idname_detail);
        txtdescription_detail = findViewById(R.id.idDescription_detail);
        txtprice_detail = findViewById(R.id.idprice_detail);
        loadDetail(productsID);
        ActionBack();
    }

    public void loadDetail(String productsID)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");
        databaseReference.child(productsID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Products products = snapshot.getValue(Products.class);
                    Picasso.get().load(products.getImage())
                            .fit()
                            .centerCrop()
                            .into(imageView_detail);
                    txtname_detail.setText(products.getProducts_name());
                    txtdescription_detail.setText(products.getDescription());
                    DecimalFormat decimalFormat = new DecimalFormat("#,###,###");

                    txtprice_detail.setText(decimalFormat.format(Integer.valueOf(products.getPrice().trim())) + " Đ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void ActionBack()
    {
        imageView_back = findViewById(R.id.idImage_back);

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_detail.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}