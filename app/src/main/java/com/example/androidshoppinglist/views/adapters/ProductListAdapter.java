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
import com.example.androidshoppinglist.data.GetListActionType;
import com.example.androidshoppinglist.models.Product;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

/**
 * Created by joanna on 03.07.16.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    private List<Product> products;
    private final Activity mActivity;
    private ActionCreator actionCreator;
    private GetListActionType listType;

    public ProductListAdapter(List<Product> products, Activity mActivity, GetListActionType listType) {
        this.products = products;
        this.mActivity = mActivity;
        this.actionCreator = new ActionCreator(EventBus.getDefault());
        this.listType = listType;
    }

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, viewGroup, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {
        Product product = products.get(position);

        holder.textViewProduct.setText(product.getName());
        holder.textViewQuantity.setText(getFormattedNumber(product.getQuantity()));
        holder.textViewUnit.setText(product.getUnit());

        if (product.isBought()) {
            holder.imageViewCheckbox.setImageDrawable(
                    ContextCompat.getDrawable(mActivity, R.drawable.checkbox_marked));

            if (listType.equals(GetListActionType.CURRENT)) {
                holder.imageViewCheckbox.setOnClickListener(view ->
                        actionCreator.createSetProductNotBoughtAction(product));
            }

        } else {
            holder.imageViewCheckbox.setImageDrawable(
                    ContextCompat.getDrawable(mActivity, R.drawable.checkbox_blank));

            if (listType.equals(GetListActionType.CURRENT)) {
                holder.imageViewCheckbox.setOnClickListener(view ->
                        actionCreator.createSetProductBoughtAction(product));
            }
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

    private String getFormattedNumber(double number) {
        return (number % 1 == 0)? String.format(Locale.UK, "%d", (int) number) : String.format(Locale.UK, "%.2f", number);
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
