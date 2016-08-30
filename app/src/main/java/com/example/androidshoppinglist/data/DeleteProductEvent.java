package com.example.androidshoppinglist.data;

import com.example.androidshoppinglist.models.Product;

/**
 * Created by joanna on 30.08.16.
 */
public class DeleteProductEvent {

    private final Product product;

    public DeleteProductEvent(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
