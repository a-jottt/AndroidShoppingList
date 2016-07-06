package com.example.androidshoppinglist.views.adapters;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidshoppinglist.R;
import com.example.androidshoppinglist.actions.ActionCreator;
import com.example.androidshoppinglist.models.Product;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by joanna on 03.07.16.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    private List<Product> products;
    private final Activity mActivity;
    private ActionCreator actionCreator;

    public ProductListAdapter(List<Product> products, Activity mActivity) {
        this.products = products;
        this.mActivity = mActivity;
        this.actionCreator = new ActionCreator(EventBus.getDefault());
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
        holder.textViewQuantity.setText(String.valueOf(product.getQuantity()));
        holder.textViewUnit.setText(product.getUnit());

        if (product.isBought()) {
            holder.imageViewCheckbox.setImageDrawable(
                    ContextCompat.getDrawable(mActivity, R.drawable.checkbox_marked));
            holder.imageViewCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionCreator.createSetProductNotBoughtAction(product);
                }
            });
        }
        else {
            holder.imageViewCheckbox.setImageDrawable(
                    ContextCompat.getDrawable(mActivity, R.drawable.checkbox_blank));
            holder.imageViewCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionCreator.createSetProductBoughtAction(product);
                }
            });
        }

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
        public final TextView textViewUnit;

        public ProductListViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            imageViewCheckbox = (ImageView) itemView.findViewById(R.id.imageViewCheckbox);
            textViewProduct = (TextView) itemView.findViewById(R.id.textViewProduct);
            textViewQuantity = (TextView) itemView.findViewById(R.id.textViewQuantity);
            textViewUnit = (TextView) itemView.findViewById(R.id.textViewUnit);
        }
    }
}
