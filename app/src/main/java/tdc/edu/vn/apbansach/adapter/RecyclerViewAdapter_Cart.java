package tdc.edu.vn.apbansach.adapter;

import android.app.Activity;
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
import tdc.edu.vn.apbansach.model.Products;

public class RecyclerViewAdapter_Cart extends RecyclerView.Adapter<RecyclerViewAdapter_Cart.MyViewHolder> {
    private Activity context;
    private int resource;
    private ArrayList<Products> bookslist;

    public RecyclerViewAdapter_Cart(Activity context, int resource, ArrayList<Products> bookslist) {
        this.context = context;
        this.resource = resource;
        this.bookslist = bookslist;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);
        return new RecyclerViewAdapter_Cart.MyViewHolder(cardViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products books = bookslist.get(position);
        holder.idName_books.setText(books.getProducts_name());
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");

        holder.idPrice_books.setText(decimalFormat.format(Integer.valueOf(books.getPrice().trim()))+ " Đ");
        Picasso.get()
                .load(books.getImage())
                .fit()
                .centerCrop()
                .into(holder.idimg_books);
    }

    @Override
    public int getItemViewType(int position) {
        return resource;
    }

    @Override
    public int getItemCount() {
        return bookslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView idimg_books;
        TextView idName_books;
        TextView  idPrice_books;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idimg_books = itemView.findViewById(R.id.idimg_books);
            idName_books = itemView.findViewById(R.id.idName_books);
            idPrice_books = itemView.findViewById(R.id.idPrice_books);


        }
    }
}
