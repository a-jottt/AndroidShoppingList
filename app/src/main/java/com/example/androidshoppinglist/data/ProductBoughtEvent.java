package com.example.androidshoppinglist.data;

import com.example.androidshoppinglist.models.Product;

/**
 * Created by joanna on 04.07.16.
 */
public class ProductBoughtEvent {

    public final Product product;
    public final boolean bought;

    public ProductBoughtEvent(Product product, boolean bought) {
        this.product = product;
        this.bought = bought;
    }

    public Product getProduct() {
        return product;
    }

    public boolean isBought() {
        return bought;
    }
}
