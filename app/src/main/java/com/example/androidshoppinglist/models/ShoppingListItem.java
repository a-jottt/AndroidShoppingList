package com.example.androidshoppinglist.models;

import java.sql.Time;
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

    String title;
    Date createdAt;
    @PrimaryKey long createdAtTime;
    boolean archived;
    RealmList<Product> products;

    public ShoppingListItem() {
        super();
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getFormattedCreatedAt() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        return format.format(createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products.addAll(products);
    }

    public void addToProducts(Product product) {
        this.products.add(product);
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public int getProductsBought() {
        int productsBought = 0;
        for (Product product: getProducts()) {
            if (product.isBought())
                productsBought += 1;
        }
        return productsBought;
    }

    public int getProductsToBuy() {
        return products.size();
    }
}
