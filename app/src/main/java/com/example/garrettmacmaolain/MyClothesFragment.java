package com.example.garrettmacmaolain;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.parse.*;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesper on 07/11/14.
 */
public class MyClothesFragment extends Fragment {
    private ArrayList<Product> products;
    GridView mGridView;
    private ProgressDialog progressDialog;
    ImageAdapter imageAdapter;
    private static final String TAG = "FullFeedFragment";
    ArrayAdapter<Product> adapter;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.full_feed_layout, container, false);
        products = new ArrayList<Product>();
        mGridView = (GridView) rootView.findViewById(R.id.gridview);
        getMyCloths();
        imageAdapter = new ImageAdapter(getActivity().getApplicationContext());
        mGridView.setAdapter(imageAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "TO ITEM VIEW", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public void getMyCloths() {
        Log.v("HEJ",""+getUser());
        if(getUser()==null)return;
        ParseQuery<ParseObject> productQuery = ParseQuery.getQuery(ParseStrings.PRODUCT);

        productQuery.whereEqualTo(ParseStrings.PRODUCT_OWNER, getUser().getObjectId());
        productQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> parseObjects, ParseException e) {

                for (ParseObject po : parseObjects) {
                    Product product;
                    product = new Product(po, new Product.ProductImageSetListener() {
                        @Override
                        public void onProductImageSet(final Product product) {

                            products.add(product);


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageAdapter.updateProduct(product);
                                    imageAdapter.notifyDataSetChanged();

                                }
                            });
                        }
                    });
                }
            }
        });
    }
}
