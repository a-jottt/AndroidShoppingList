package com.example.androidshoppinglist.data;

import com.example.androidshoppinglist.models.ShoppingListItem;

import java.util.List;

/**
 * Created by joanna on 03.07.16.
 */
public class ShoppingListEvent {

    public final List<ShoppingListItem> list;
    public final GetListActionType listType;

    public ShoppingListEvent(List<ShoppingListItem> list, GetListActionType listType) {
        this.list = list;
        this.listType = listType;
    }

    public List<ShoppingListItem> getList() {
        return list;
    }

    public GetListActionType getListType() {
        return listType;
    }
}
