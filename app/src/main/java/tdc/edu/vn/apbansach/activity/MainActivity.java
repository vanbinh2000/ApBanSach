package tdc.edu.vn.apbansach.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.adapter.ListViewAdapter;
import tdc.edu.vn.apbansach.adapter.RecylerViewAdapter_Books;
import tdc.edu.vn.apbansach.model.Categories;
import tdc.edu.vn.apbansach.model.Products;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Products> listProducts;
    RecylerViewAdapter_Books recylerViewAdapter_books;
    ArrayList<Categories> listCategories;

    ViewFlipper viewFlipper;
    // ImageView imageView_viewlipper;
    Toolbar toolbar;
    NavigationView navigationView;
    ListView listView_menu;
    DrawerLayout drawerLayout;
    ListViewAdapter listviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControlEvent();
        displayProducts();
        listViewMenu();
        // displayCategories();
        RunWiewLipper();
        actionOnclickItem();


    }

    public void RunWiewLipper() {

        viewFlipper = findViewById(R.id.viewflipper);
        ArrayList<String> litsviewlippers = new ArrayList<>();
        litsviewlippers.add("https://firebasestorage.googleapis.com/v0/b/bookstoreapp-b0b38.appspot.com/o/qc1.jpg?alt=media&token=1dd1c8b2-ee28-47a4-9deb-051db1eccb2c");
        litsviewlippers.add("https://firebasestorage.googleapis.com/v0/b/bookstoreapp-b0b38.appspot.com/o/qc2.webp?alt=media&token=a28991f9-7414-443f-8e3e-1977c6c19b04");
        litsviewlippers.add("https://firebasestorage.googleapis.com/v0/b/bookstoreapp-b0b38.appspot.com/o/qc4.jpg?alt=media&token=52360472-2605-4206-a924-e7909d82838b");
        litsviewlippers.add("https://firebasestorage.googleapis.com/v0/b/bookstoreapp-b0b38.appspot.com/o/qc5.jpg?alt=media&token=7c8fdf14-f6ea-44cd-920d-2ccb4362c953");
        for (int i = 0; i < litsviewlippers.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(litsviewlippers.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_in_left);

    }

    public void displayProducts() {
        listProducts = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
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
                    listProducts.add(products);
                }
                recylerViewAdapter_books.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
//
//    public void displayCategories() {
//        listCategories = new ArrayList<>();
//        recyclerView = findViewById(R.id.recyclerview_categories);
//        adapterCategories = new RecylerViewAdapterCategories(this, R.layout.layout_items_categories, listCategories);
//
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
//        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        recyclerView.setAdapter(adapterCategories);
//        recyclerView.setLayoutManager(layoutManager);
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Categories");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Categories categories = dataSnapshot.getValue(Categories.class);
//                    listCategories.add(categories);
//                }
//                adapterCategories.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.idsearch) {
            Intent intent = new Intent(MainActivity.this, search_activity.class);
            this.startActivity(intent);
            return true;
        }
        else if(id == R.id.idperson)
        {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            this.startActivity(intent);
            return  true;
        }
        else if (id == R.id.idCart){
            Intent i = new Intent(MainActivity.this, CartActivty.class);
            this.startActivity(i);
            return true;
        }
        else if(id == R.id.idPayment)
        {
            Intent intent = new Intent (MainActivity.this, Activity_payment.class);
            this.startActivity(intent);
            return  true;
        }



        return super.onOptionsItemSelected(item);
    }

    public void setControlEvent() {
        drawerLayout = findViewById(R.id.iddrawer);
        navigationView = findViewById(R.id.idnavigation);

        toolbar = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }

    public void listViewMenu() {
        listCategories = new ArrayList<>();
        listView_menu = findViewById(R.id.idlistview_menu);
        listviewAdapter = new ListViewAdapter(listCategories, this, R.layout.listview_categories);
        listView_menu.setAdapter(listviewAdapter);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Categories");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Categories categories = dataSnapshot.getValue(Categories.class);
                    listCategories.add(categories);
                }
                listviewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void actionOnclickItem() {
        listView_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, Activity_sachTH.class);
                        intent.putExtra("id_categories_1", listCategories.get(position).getCategories_id());
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, Activity_KhoaHoc.class);
                        intent1.putExtra("id_categories_2", listCategories.get(position).getCategories_id());
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, Activity_KinhDoanh.class);
                        intent2.putExtra("id_categories_3", listCategories.get(position).getCategories_id());
                        startActivity(intent2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, Activity_truyen.class);
                        intent3.putExtra("id_categories_4", listCategories.get(position).getCategories_id());
                        startActivity(intent3);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }


        });
    }




}