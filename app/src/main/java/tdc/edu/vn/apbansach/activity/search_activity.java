package tdc.edu.vn.apbansach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.adapter.RecylerViewAdapter_Books;
import tdc.edu.vn.apbansach.model.Products;

public class search_activity extends AppCompatActivity {

    RecyclerView recyclerViewSearch;
    ArrayList<Products>listProducts;
    RecylerViewAdapter_Books recylerViewAdapter_books;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

       FindProducts();



    }
    public void FindProducts()

    {
        searchView =(SearchView) findViewById(R.id.idsearchview);
        searchView.clearFocus();
        listProducts = new ArrayList<>();
        recyclerViewSearch =(RecyclerView) findViewById(R.id.id_search_items);
        recylerViewAdapter_books = new RecylerViewAdapter_Books(this,R.layout.layout_items_products,listProducts);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewSearch.setLayoutManager(layoutManager);
        recyclerViewSearch.setAdapter(recylerViewAdapter_books);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query databaseReference = firebaseDatabase.getReference().child("Products");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Products products = dataSnapshot.getValue(Products.class);
                    listProducts.add(products);
                }
                recylerViewAdapter_books.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filterList(newText);
                return false;
            }
        });
    }
    private void filterList(String text) {
        ArrayList<Products> filteredlist = new ArrayList<>();
        for(Products item : listProducts)
        {
            if(item.getProducts_name().toLowerCase().contains(text.toLowerCase()))
            {
                filteredlist.add(item);
            }
        }
        if(filteredlist.isEmpty())
        {
            // Toast.makeText(this, "Khong tim thay sach", Toast.LENGTH_SHORT).show();
        }
        else
        {

            recylerViewAdapter_books.setFilterList(filteredlist);
        }
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()){
            searchView.setIconified(true);
        }
        super.onBackPressed();
    }
}