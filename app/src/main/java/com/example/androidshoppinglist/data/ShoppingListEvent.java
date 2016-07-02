package com.example.androidshoppinglist.data;

import com.example.androidshoppinglist.models.ShoppingListItem;

import java.util.List;

/**
 * Created by joanna on 03.07.16.
 */
public class ShoppingListEvent {

    public final List<ShoppingListItem> list;

    public ShoppingListEvent(List<ShoppingListItem> list) {
        this.list = list;
    }

    public List<ShoppingListItem> getList() {
        return list;
    }
}
