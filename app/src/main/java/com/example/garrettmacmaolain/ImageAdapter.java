package com.example.garrettmacmaolain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jesper on 07/11/14.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Product>products;
    private LayoutInflater inflater;
    public ImageAdapter(Context c) {
        mContext = c;
        inflater = LayoutInflater.from(c);
        products = new ArrayList<Product>();
    }

    public int getCount() {
        return products.size();
    }

    public Object getItem(int position) {
        return products.get(position);

    }

    public long getItemId(int position) {
        return 0;
    }
    public void updateProduct(Product product){
        this.products.add(product);

    }
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ImageView picture;
        TextView name;

        if(v == null) {
            v = inflater.inflate(R.layout.grid_item, parent, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }
        picture = (ImageView)v.getTag(R.id.picture);
        name = (TextView)v.getTag(R.id.text);

        //Item item = (Item)getItem(i);

        picture.setImageBitmap(products.get(position).getImage());
        name.setText(products.get(position).getTitle());

        return v;

    }
}