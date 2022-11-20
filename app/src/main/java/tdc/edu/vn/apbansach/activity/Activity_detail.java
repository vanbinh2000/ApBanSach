package tdc.edu.vn.apbansach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.model.Cart;
import tdc.edu.vn.apbansach.model.Payment;
import tdc.edu.vn.apbansach.model.Products;

public class Activity_detail extends AppCompatActivity {
   private ImageView imageView_detail;
   private TextView txtname_detail;
   private TextView txtdescription_detail;
   private TextView txtprice_detail;
   private ImageView imageView_back;
   private String productsID = "";
   private  String priceProducts= "";
   private Button btnAddCart;
   private FirebaseAuth mAuth;
   private Button btnBuynow;
   private FirebaseDatabase firebaseDatabase;
   private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        productsID = getIntent().getStringExtra("productsID");
        priceProducts = getIntent().getStringExtra("priceProducts");
        imageView_detail = findViewById(R.id.idimage_detail);
        txtname_detail = findViewById(R.id.idname_detail);
        txtdescription_detail = findViewById(R.id.idDescription_detail);
        txtprice_detail = findViewById(R.id.idprice_detail);
        btnAddCart = findViewById(R.id.btnAddCart);
        btnBuynow = findViewById(R.id.idbuynow);
        loadDetail(productsID);
        setgetSpinner();
        ActionBack();
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    addToCart();
            }
        });
        btnBuynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBuyNow();
            }
        });
    }

    private void addToCart() {
        String UserID = mAuth.getCurrentUser().getUid();
        Cart cartModel = new Cart(UserID, productsID);
        final  HashMap<String, Object>cart = new HashMap<>();
        cart.put("UserID", cartModel.getUserID());
        cart.put("ProductID", cartModel.getProductID());

        FirebaseDatabase.getInstance().getReference("Cart").child(mAuth.getCurrentUser().getUid()).child(productsID)
                .setValue(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Activity_detail.this, "ok", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Activity_detail.this, "ko dc", Toast.LENGTH_SHORT).show();
                    }
                });

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
    public void loadBuyNow()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String savedate = simpleDateFormat.format(calendar.getTime());

        int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
        long total_price = quantity *Integer.valueOf(priceProducts.trim());
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###Đ");


        String UserID = mAuth.getCurrentUser().getUid();
        Payment payment = new Payment(UserID, productsID, priceProducts, String.valueOf(quantity), savedate);
        final  HashMap<String, Object>paymentlist = new HashMap<>();
        paymentlist.put("userid", payment.getUserid());
        paymentlist.put("productid", payment.getProductsid());
        paymentlist.put("quantity", quantity);
        paymentlist.put("total_price",  decimalFormat.format(total_price));
        paymentlist.put("datepayment", savedate);


        FirebaseDatabase.getInstance().getReference("Payment").child(mAuth.getCurrentUser().getUid()).child(productsID)
                .setValue(paymentlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Activity_detail.this, "successfull buy", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Activity_detail.this, "Not successfull buy", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void  setgetSpinner()

    {
        spinner = findViewById(R.id.idSpinner);
        Integer [] total = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> lisintegers = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, total);
        spinner.setAdapter(lisintegers);

    }

}