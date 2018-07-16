package com.example.hp.task.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.hp.task.Model.Product;
import com.example.hp.task.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by hp on 7/16/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;

    ArrayList<Product> productArrayList;


    public ProductAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.desc.setHeight(productArrayList.get(position).getDescription().length());
        holder.desc.setText(productArrayList.get(position).getDescription());
        holder.price.setText(productArrayList.get(position).getPrice());
        holder.imageproduct.getLayoutParams().height = productArrayList.get(position).getImage().getHeight();
        holder.imageproduct.getLayoutParams().width = productArrayList.get(position).getImage().getWidth();
        Glide.with(context).load(
                productArrayList.get(position).getImage().getUrl()
        ).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);

                return false;
            }
        }).into(holder.imageproduct);
        Picasso.with(context).load(productArrayList.get(position).getImage().getUrl()).into(holder.imageproduct);

    }



    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageproduct;
        ProgressBar progressBar;
        TextView desc, price ;



        public ViewHolder(View itemView) {
            super(itemView);
            imageproduct = itemView.findViewById(R.id.product_cover);
            desc = itemView.findViewById(R.id.product_desc);
            price = itemView.findViewById(R.id.product_price);
            progressBar = itemView.findViewById(R.id.product_cover_loader);




        }
    }
}
