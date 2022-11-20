package tdc.edu.vn.apbansach.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.activity.Activity_detail;
import tdc.edu.vn.apbansach.model.Products;

public class RecylerViewAdapter_Books extends RecyclerView.Adapter<RecylerViewAdapter_Books.MyViewHolder> {
    private Activity context;
    private int resource;
    private ArrayList<Products> bookslist;

    public void setFilterList(ArrayList<Products> filterlist) {
        this.bookslist = filterlist;
        notifyDataSetChanged();
    }

    public RecylerViewAdapter_Books(Activity context, int resource, ArrayList<Products> bookslist) {
        this.context = context;
        this.resource = resource;
        this.bookslist = bookslist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
        return new MyViewHolder(cardViewItem);
    }

    public int getItemViewType(int position) {
        return resource;
    }

    @Override
    public int getItemCount() {

        return bookslist.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products books = bookslist.get(position);
        holder.txtName_book.setText(books.getProducts_name());
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");

        holder.txtPrice_book.setText(decimalFormat.format(Integer.valueOf(books.getPrice().trim()))+ " Đ");
        holder.txtDescription_book.setText(books.getDescription().substring(0, 20) + "...");
        Picasso.get()
                .load(books.getImage())
                .fit()
                .centerCrop()
                .into(holder.image_book);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_detail.class);
                intent.putExtra("productsID", books.getProducts_id());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_book;
        TextView txtName_book;
        TextView txtDescription_book;
        TextView txtPrice_book;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_book = itemView.findViewById(R.id.idimg_books);
            txtName_book = itemView.findViewById(R.id.idName_books);
            txtPrice_book = itemView.findViewById(R.id.idPrice_books);
            txtDescription_book = itemView.findViewById(R.id.idDescription_books);

        }
    }
}

