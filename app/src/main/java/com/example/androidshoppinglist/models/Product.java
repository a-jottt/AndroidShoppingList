package com.example.androidshoppinglist.models;

import io.realm.RealmObject;

/**
 * Created by joanna on 29.06.16.
 */

public class Product extends RealmObject {

    String name;
    Integer quantity;
    boolean isBought;

    public Product() {
        super();
    }

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        this.isBought = bought;
    }
}
