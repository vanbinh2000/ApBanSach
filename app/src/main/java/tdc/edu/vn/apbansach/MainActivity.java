package tdc.edu.vn.apbansach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import tdc.edu.vn.apbansach.adapter.BookRecyclerAdapter;
import tdc.edu.vn.apbansach.model.Books;

public class MainActivity extends AppCompatActivity {
    private BookRecyclerAdapter adapter;
    private ArrayList<Books> data = new ArrayList<>();
    private String selectedFilter = "all";
    Button btnAllTag, btnAmeTag, btnAsdTag, btnMonkeTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add data to books list
        data.add(new Books("asd", "2000vnd", R.drawable.dunno, " How can we handle show more and show less functionality in main activity? i.e through on click listene"));
        data.add(new Books("ame", "2000vnd", R.drawable.icantakeitanymore, " How can we handle show more and show less functionality in main activity? i.e through on click listene"));
        data.add(new Books("monke", "2000vnd", R.drawable.monke, " How can we handle show more and show less functionality in main activity? i.e through on click listene"));
        data.add(new Books("asd", "2000asdvnd", R.drawable.dunno, " How can we handle show more and show less functionality in main activity? i.e through on click listene"));
        data.add(new Books("asd", "2000vnd", R.drawable.dunno, " How can we handle show more and show less functionality in main activity? i.e through on click listene"));
        data.add(new Books("ame", "2000vnd", R.drawable.icantakeitanymore, " How can we handle show more and show less functionality in main activity? i.e through on click listene"));
        data.add(new Books("asd", "2000vnd", R.drawable.dunno, " How can we handle show more and show less functionality in main activity? i.e through on click listene"));
        data.add(new Books("monke", "2000vnd", R.drawable.monke, " How can we handle show more and show less functionality in main activity? i.e through on click listene"));
        RecyclerView listIttem = (RecyclerView) findViewById(R.id.listItem);
        adapter = new BookRecyclerAdapter(this, R.layout.item_layout, data);

        //manager to recyclerview
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        listIttem.setLayoutManager(layoutManager);
        listIttem.setAdapter(adapter);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnAllTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView listIttem = (RecyclerView) findViewById(R.id.listItem);
                adapter = new BookRecyclerAdapter(MainActivity.this, R.layout.item_layout, data);
                listIttem.setAdapter(adapter);
            }
        });
        btnAsdTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillterList("asd");
            }
        });
        btnAmeTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillterList("ame");
            }
        });
        btnMonkeTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillterList("monke");
            }
        });
    }

    private void setControl() {
        btnAllTag = findViewById(R.id.btnAllTap);
        btnAsdTag = findViewById(R.id.btnasdTap);
        btnAmeTag = findViewById(R.id.btnAmeTap);
        btnMonkeTag = findViewById(R.id.btnMonkeTap);
    }

    private void fillterList(String Categories) {
        selectedFilter = Categories;
        ArrayList<Books> tag = new ArrayList<>();
        for (Books books : data) {
            if (books.getBookName().toLowerCase().contains(selectedFilter)) {
                tag.add(books);
            }
        }
        RecyclerView listIttem = (RecyclerView) findViewById(R.id.listItem);
        adapter = new BookRecyclerAdapter(this, R.layout.item_layout, tag);
        listIttem.setAdapter(adapter);
    }

}