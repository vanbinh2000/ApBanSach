package tdc.edu.vn.apbansach.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tdc.edu.vn.apbansach.R;
import tdc.edu.vn.apbansach.model.Categories;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<Categories> datalist;
    private Context context;
    private int layout;

    public ListViewAdapter(ArrayList<Categories> datalist, Context context, int layout) {
        this.datalist = datalist;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < 0)
            return null;

        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtname_categories;
        ImageView imageView_cate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.txtname_categories = (TextView) convertView.findViewById(R.id.idname_listview);
            viewHolder.imageView_cate = (ImageView) convertView.findViewById(R.id.image_listview);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.txtname_categories.setText(datalist.get(position).getCategories_name());
        Picasso.get().load(datalist.get(position).getCategories_icon())
                .fit()
                .centerCrop()
                .into(viewHolder.imageView_cate);


        return convertView;
    }
}

