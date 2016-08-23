package com.example.androidshoppinglist.actions;

/**
 * Created by joanna on 29.06.16.
 */
public enum ActionTypes implements ActionType {

    SET_PRODUCT_NOT_BOUGHT,
    SET_PRODUCT_BOUGHT,
    GET_PRODUCTS_LIST_FROM_DATABASE,
    GET_SHOPPING_LISTS_FROM_DATABASE,
    ADD_NEW_SHOPPING_LIST,
    ADD_PRODUCT_TO_LIST,
    ARCHIVE_LIST,
    DELETE_LIST
}
