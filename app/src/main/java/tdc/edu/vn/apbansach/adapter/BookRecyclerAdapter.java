package tdc.edu.vn.apbansach.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.model.Books;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder> {
    private Activity context;
    private int layoutID;
    private ArrayList<Books> booksList;

    public BookRecyclerAdapter(Activity context, int layoutID, ArrayList<Books> booksList) {
        this.context = context;
        this.layoutID = layoutID;
        this.booksList = booksList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView viewItem = (CardView) context.getLayoutInflater().inflate(viewType, parent, false);
        return new BookViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Books books = booksList.get(position);
        holder.imgBook.setImageResource(books.getImageBook());
        holder.lbBookName.setText(books.getBookName());
        holder.lbBookPrice.setText(books.getBookPrice());
        holder.lbBookInfo.setText(books.getBookInfo().substring(0, 20) + "........");
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutID;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView lbBookName, lbBookPrice, lbBookInfo;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            lbBookName = itemView.findViewById(R.id.lbBookName);
            lbBookPrice = itemView.findViewById(R.id.lbBookPrice);
            lbBookInfo = itemView.findViewById(R.id.lbBookInfo);
        }
    }
}
