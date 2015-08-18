package com.example.garrettmacmaolain;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by jesper on 07/11/14.
 */
public class ProductArrayAdapter extends ArrayAdapter<Product> {
    private ArrayList<Product> products;
    Context mContext;
    public ProductArrayAdapter(Context context, int textViewResourceId, ArrayList<Product> products) {
        super(context, textViewResourceId, products);
        mContext = context;
        this.products = products;

    }

    public int getCount() {
        return products.size();
    }

    public Product getItem(int position) {
        return products.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }
    public void updateProducts(ArrayList<Product> products){
        this.products = products;
    }
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("DEGUBBING",""+position);
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(products.get(position).getImage());
        return imageView;
    }


}