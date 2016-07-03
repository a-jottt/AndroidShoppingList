package com.example.androidshoppinglist.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joanna on 29.06.16.
 */

public class Product extends RealmObject {

    String name;
    double quantity;
    boolean isBought;
    Date createdAt;
    @PrimaryKey long createdAtTime;
    long listCreatedAtTime;

    public Product() {
        super();
    }

    public Product(String name, Date createdAt, double quantity, long listCreatedAtTime) {
        this.name = name;
        this.createdAt = createdAt;
        this.quantity = quantity;
        this.listCreatedAtTime = listCreatedAtTime;
        createdAtTime = createdAt.getTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
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

    public long getListCreatedAtTime() {
        return listCreatedAtTime;
    }

    public void setListCreatedAtTime(long listCreatedAtTime) {
        this.listCreatedAtTime = listCreatedAtTime;
    }
}
