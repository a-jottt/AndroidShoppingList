package com.example.androidshoppinglist.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joanna on 29.06.16.
 */

public class Product extends RealmObject {

    @PrimaryKey protected long createdAtTime;
    protected String name;
    protected double quantity;
    protected boolean isBought;
    protected Date createdAt;
    protected long listCreatedAtTime;
    protected String unit;

    public Product() {
    }

    public Product(String name, Date createdAt, double quantity, long listCreatedAtTime, String unit) {
        this.name = name;
        this.createdAt = createdAt;
        this.quantity = quantity;
        this.listCreatedAtTime = listCreatedAtTime;
        this.unit = unit;
        isBought = false;
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

    public String getUnit() {
        return unit;
    }
}
