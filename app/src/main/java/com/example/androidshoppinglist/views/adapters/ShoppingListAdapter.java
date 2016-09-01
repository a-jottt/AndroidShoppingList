package com.example.androidshoppinglist.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidshoppinglist.R;
import com.example.androidshoppinglist.data.GetListActionType;
import com.example.androidshoppinglist.models.ShoppingListItem;
import com.example.androidshoppinglist.views.DetailsActivity_;

import java.util.List;

import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

/**
 * Created by joanna on 29.06.16.
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {

    private List<ShoppingListItem> shoppingListItems;
    private final Context mContext;
    private GetListActionType listType;

    public ShoppingListAdapter(List<ShoppingListItem> shoppingListItems, Context mContext, GetListActionType listType) {
        this.shoppingListItems = shoppingListItems;
        this.mContext = mContext;
        this.listType = listType;
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopping_list_item, viewGroup, false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingListAdapter.ShoppingListViewHolder holder, int position) {
        ShoppingListItem shoppingListItem = shoppingListItems.get(position);
        int productsBought = shoppingListItem.getProductsBoughtCount();
        int productsToBuy = shoppingListItem.getProducts().size();

        holder.circleProgressView.setText(String.valueOf(productsBought) + "/" + String.valueOf(productsToBuy));
        holder.circleProgressView.setTextMode(TextMode.TEXT);
        holder.circleProgressView.setValue(productsBought);
        holder.circleProgressView.setMaxValue(productsToBuy);
        holder.textViewTitle.setText(shoppingListItem.getTitle());
        holder.textViewDate.setText(shoppingListItem.getFormattedCreatedAt());
        holder.card.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, DetailsActivity_.class);
            intent.putExtra(mContext.getString(R.string.listCreatedAtTime), shoppingListItem.getCreatedAtTime());
            intent.putExtra(mContext.getString(R.string.list_type), listType);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (null != shoppingListItems ? shoppingListItems.size() : 0);
    }

    public void notifyData(List<ShoppingListItem> myList, GetListActionType listType) {
        this.shoppingListItems = myList;
        this.listType = listType;
        notifyDataSetChanged();
    }

    class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        public final CardView cardView;
        public final CircleProgressView circleProgressView;
        public final TextView textViewDate;
        public final TextView textViewTitle;
        public final ImageView imageViewDetails;
        public final LinearLayout card;

        public ShoppingListViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            circleProgressView = (CircleProgressView) itemView.findViewById(R.id.circleView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            imageViewDetails = (ImageView) itemView.findViewById(R.id.imageViewDetails);
            card = (LinearLayout) itemView.findViewById(R.id.card);
        }
    }
}
