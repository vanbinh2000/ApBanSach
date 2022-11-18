package tdc.edu.vn.apbansach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.adapter.RecylerViewAdapter_Books;
import tdc.edu.vn.apbansach.model.Products;

public class Activity_sachTH extends AppCompatActivity {
    ImageView imageView_back;
    RecyclerView recyclerView;
    ArrayList<Products> listProducts;
    RecylerViewAdapter_Books recylerViewAdapter_books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach_th);
        loadPoducts_categories();
        actionBack();

    }
    public void actionBack()
    {
        imageView_back = findViewById(R.id.idback_TH);

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_sachTH.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void loadPoducts_categories()
    {
        listProducts = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.idrecylerview_Tonghop);
        recylerViewAdapter_books = new RecylerViewAdapter_Books(this, R.layout.layout_items_products, listProducts);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recylerViewAdapter_books);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Products");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listProducts != null) {
                    listProducts.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Products products = dataSnapshot.getValue(Products.class);
                    if (products.getCategories_id().contains("CT01"))
                    {
                        listProducts.add(products);
                    }

                }
                recylerViewAdapter_books.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}