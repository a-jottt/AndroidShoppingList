package com.example.androidshoppinglist.views.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidshoppinglist.R;
import com.example.androidshoppinglist.models.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

/**
 * Created by joanna on 29.06.16.
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private List<ShoppingListItem> shoppingListItems;
    private final Context mContext;

    public ShoppingListAdapter(List<ShoppingListItem> shoppingListItems, Context mContext) {
        this.shoppingListItems = shoppingListItems;
        this.mContext = mContext;
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopping_list_item, viewGroup, false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingListAdapter.ShoppingListViewHolder holder, int position) {
        ShoppingListItem shoppingListItem = shoppingListItems.get(position);
        int productsBought = shoppingListItem.getProductsBought();
        int productsToBuy = shoppingListItem.getProductsToBuy();

        holder.circleProgressView.setText(String.valueOf(productsBought) + "/" + String.valueOf(productsToBuy));
        holder.circleProgressView.setTextMode(TextMode.TEXT);
        holder.circleProgressView.setValue(productsBought);
        holder.circleProgressView.setMaxValue(productsToBuy);
        holder.textViewTitle.setText(shoppingListItem.getTitle());
        holder.textViewDate.setText(shoppingListItem.getFormattedCreatedAt());
    }

    @Override
    public int getItemCount() {
        return (null != shoppingListItems ? shoppingListItems.size() : 0);
    }

    public void notifyData(List<ShoppingListItem> myList) {
        this.shoppingListItems = myList;
        notifyDataSetChanged();
    }

    class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        public final CardView cardView;
        public final CircleProgressView circleProgressView;
        public final TextView textViewDate;
        public final TextView textViewTitle;

        public ShoppingListViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            circleProgressView = (CircleProgressView) itemView.findViewById(R.id.circleView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
        }
    }
}
