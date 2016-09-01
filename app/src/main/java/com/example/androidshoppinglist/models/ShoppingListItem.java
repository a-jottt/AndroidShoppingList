package com.example.androidshoppinglist.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joanna on 29.06.16.
 */
public class ShoppingListItem extends RealmObject {

    @PrimaryKey protected long createdAtTime;
    protected String title;
    protected Date createdAt;
    protected boolean archived;
    protected RealmList<Product> products;

    public ShoppingListItem() {
    }

    public ShoppingListItem(String title, Date createdAt) {
        super();
        this.title = title;
        this.createdAt = createdAt;
        this.createdAtTime = createdAt.getTime();
        products = new RealmList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormattedCreatedAt() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

        return format.format(createdAt);
    }

    public RealmList<Product> getProducts() {
        return products;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public long getCreatedAtTime() {
        return createdAtTime;
    }

    public int getProductsBoughtCount() {
        int productsBought = 0;
        for (Product product: getProducts()) {
            if (product.isBought())
                productsBought += 1;
        }

        return productsBought;
    }
}
