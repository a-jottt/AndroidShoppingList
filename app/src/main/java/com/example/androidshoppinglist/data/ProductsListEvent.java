package com.example.androidshoppinglist.data;

import com.example.androidshoppinglist.models.Product;

import java.util.List;

/**
 * Created by joanna on 03.07.16.
 */
public class ProductsListEvent {

    public final List<Product> products;

    public ProductsListEvent(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }
}
