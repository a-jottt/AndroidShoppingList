package com.example.androidshoppinglist.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joanna on 29.06.16.
 */

public class Product extends RealmObject {

    String name;
    Integer quantity;
    boolean isBought;
    Date createdAt;
    @PrimaryKey long createdAtTime;

    public Product() {
        super();
    }

    public Product(String name, Date createdAt) {
        this.name = name;
        this.createdAt = createdAt;
        createdAtTime = createdAt.getTime();
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

    public Date getCreatedAt() {
        return createdAt;
    }
}
