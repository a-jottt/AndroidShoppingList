package com.example.androidshoppinglist.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by joanna on 29.06.16.
 */
public class ShoppingListItem {

    String title;
    Date createdAt;
    List<Product> products;
    boolean archived;
    int productsBought;

    public ShoppingListItem(String title, Date createdAt) {
        this.title = title;
        this.createdAt = createdAt;
        products = new ArrayList<>();
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
        productsBought = 0;
        for (Product product: products) {
            if (product.isBought())
                productsBought += 1;
        }
        return productsBought;
    }

    public int getProductsToBuy() {
        return products.size();
    }
}
