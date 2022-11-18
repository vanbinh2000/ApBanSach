package tdc.edu.vn.apbansach.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.model.Products;

public class Activity_detail extends AppCompatActivity {
    ImageView imageView_detail;
    TextView txtname_detail;
    TextView txtdescription_detail;
    TextView txtprice_detail;
    ImageView imageView_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        loadDetail();
        ActionBack();
    }
    public void loadDetail()
    {

        imageView_detail = findViewById(R.id.idimage_detail);
        txtname_detail = findViewById(R.id.idname_detail);
        txtdescription_detail = findViewById(R.id.idDescription_detail);
        txtprice_detail = findViewById(R.id.idprice_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
        {
            return;
        }
        Products products = (Products) bundle.get("object_prodcts");
        Picasso.get().load(products.getImage())
                .fit()
                .centerCrop()
                .into(imageView_detail);
        txtname_detail.setText(products.getProducts_name());
        txtdescription_detail.setText(products.getDescription());
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");

        txtprice_detail.setText(decimalFormat.format(Integer.valueOf(products.getPrice().trim()))+ " Đ");
    }
    public void ActionBack()
    {
        imageView_back = findViewById(R.id.idImage_back);

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_detail.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}