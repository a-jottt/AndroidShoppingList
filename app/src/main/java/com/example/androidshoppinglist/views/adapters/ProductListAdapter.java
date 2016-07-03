package com.example.androidshoppinglist.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidshoppinglist.R;
import com.example.androidshoppinglist.models.Product;

import java.util.List;

/**
 * Created by joanna on 03.07.16.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    private List<Product> products;
    private final Activity mActivity;

    public ProductListAdapter(List<Product> products, Activity mActivity) {
        this.products = products;
        this.mActivity = mActivity;
    }

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item_content, viewGroup, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {
        Product product = products.get(position);

        holder.textViewProduct.setText(product.getName());
        holder.textViewQuantity.setText(product.getQuantity());

        if (product.isBought())
            holder.imageViewCheckbox.setImageDrawable(
                    ContextCompat.getDrawable(mActivity, R.drawable.checkbox_marked));
    }

    @Override
    public int getItemCount() {
        return (null != products ? products.size() : 0);
    }

    public void notifyData(List<Product> myList) {
        this.products = myList;
        notifyDataSetChanged();
    }

    class ProductListViewHolder extends RecyclerView.ViewHolder {

        public final CardView cardView;
        public final ImageView imageViewCheckbox;
        public final TextView textViewProduct;
        public final TextView textViewQuantity;

        public ProductListViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            imageViewCheckbox = (ImageView) itemView.findViewById(R.id.imageViewCheckbox);
            textViewProduct = (TextView) itemView.findViewById(R.id.textViewProduct);
            textViewQuantity = (TextView) itemView.findViewById(R.id.textViewQuantity);
        }
    }
}