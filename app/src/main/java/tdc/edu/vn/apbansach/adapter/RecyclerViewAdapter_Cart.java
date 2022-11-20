package tdc.edu.vn.apbansach.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.model.Products;

public class RecyclerViewAdapter_Cart extends RecyclerView.Adapter<RecyclerViewAdapter_Cart.MyViewHolder> {
    private Activity context;
    private int resource;
    private ArrayList<Products> bookslist;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    public RecyclerViewAdapter_Cart(Activity context, int resource, ArrayList<Products> bookslist) {
        this.context = context;
        this.resource = resource;
        this.bookslist = bookslist;
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
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
        holder.idPrice_books.setText(decimalFormat.format(Integer.valueOf(books.getPrice().trim())) + " Đ");
        Picasso.get()
                .load(books.getImage())
                .fit()
                .centerCrop()
                .into(holder.idimg_books);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task<Void> reference = firebaseDatabase.getReference("Cart").child(mAuth.getCurrentUser().getUid())
                        .child(books.getProducts_id()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    bookslist.remove(books);
                                    Toast.makeText(context, "thanh cong", Toast.LENGTH_SHORT).show();
                                }notifyDataSetChanged();
                            }
                        });
            }
        });
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
        TextView idPrice_books;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idimg_books = itemView.findViewById(R.id.idimg_books);
            idName_books = itemView.findViewById(R.id.idName_books);
            idPrice_books = itemView.findViewById(R.id.idPrice_books);
            btnDelete = itemView.findViewById(R.id.btnRemove);
        }
    }
}
