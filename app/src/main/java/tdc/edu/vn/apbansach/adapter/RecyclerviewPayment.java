package tdc.edu.vn.apbansach.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.model.Cart;
import tdc.edu.vn.apbansach.model.Products;

public class RecyclerviewPayment extends RecyclerView.Adapter<RecyclerviewPayment.ViewHolder> {
    private ArrayList<Products> list;
    private Activity context;
    private int resource;

    public RecyclerviewPayment(ArrayList<Products> list, Activity context, int resource) {
        this.list = list;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardViewItem = (CardView) context.getLayoutInflater().
                inflate(viewType, parent, false);

        return new RecyclerviewPayment.ViewHolder(cardViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products books = list.get(position);
        holder.txtname_payment.setText(books.getProducts_name());
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        holder.txtprice_payment.setText(decimalFormat.format(Integer.valueOf(books.getPrice().trim())) + " Đ");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtname_payment;
        TextView txtprice_payment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname_payment = itemView.findViewById(R.id.idname_payment);
            txtprice_payment = itemView.findViewById(R.id.idprice_payment);
        }
    }
}
