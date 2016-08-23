package com.example.androidshoppinglist.actions;

import android.app.Activity;

import com.example.androidshoppinglist.models.Product;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by joanna on 29.06.16.
 */
public class ActionCreator {

    private EventBus eventBus;

    public ActionCreator(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public final void addNewShoppingListAction(final String title) {
        DataBundle<DataKeys> bundle = new DataBundle<>();
        bundle.put(DataKeys.SHOPPING_LIST_TITLE, title);
        eventBus.post(new ShoppingListAction(ActionTypes.ADD_NEW_SHOPPING_LIST, bundle));
    }

    public final void createGetShoppingListsFromDbAction(final Activity activity) {
        DataBundle<DataKeys> bundle = new DataBundle<>();
        bundle.put(DataKeys.ACTIVITY_CONTEXT, activity);
        eventBus.post(new ShoppingListAction(ActionTypes.GET_SHOPPING_LISTS_FROM_DATABASE, bundle));
    }

    public final void createGetProductsListFromDbAction(final long listCreatedAtTime) {
        DataBundle<DataKeys> bundle = new DataBundle<>();
        bundle.put(DataKeys.LIST_CREATED_AT_TIME, listCreatedAtTime);
        eventBus.post(new ShoppingListAction(ActionTypes.GET_PRODUCTS_LIST_FROM_DATABASE, bundle));
    }

    public final void createAddProductToListsAction(final Product product) {
        DataBundle<DataKeys> bundle = new DataBundle<>();
        bundle.put(DataKeys.PRODUCT, product);
        eventBus.post(new ShoppingListAction(ActionTypes.ADD_PRODUCT_TO_LIST, bundle));
    }

    public final void createSetProductBoughtAction(final Product product) {
        DataBundle<DataKeys> bundle = new DataBundle<>();
        bundle.put(DataKeys.PRODUCT, product);
        eventBus.post(new ShoppingListAction(ActionTypes.SET_PRODUCT_BOUGHT, bundle));
    }

    public final void createSetProductNotBoughtAction(final Product product) {
        DataBundle<DataKeys> bundle = new DataBundle<>();
        bundle.put(DataKeys.PRODUCT, product);
        eventBus.post(new ShoppingListAction(ActionTypes.SET_PRODUCT_NOT_BOUGHT, bundle));
    }

    public final void createArchiveShoppingListAction(final long listCreatedAtTime) {
        DataBundle<DataKeys> bundle = new DataBundle<>();
        bundle.put(DataKeys.LIST_CREATED_AT_TIME, listCreatedAtTime);
        eventBus.post(new ShoppingListAction(ActionTypes.ARCHIVE_LIST, bundle));
    }

    public final void createDeleteShoppingListAction(final long listCreatedAtTime) {
        DataBundle<DataKeys> bundle = new DataBundle<>();
        bundle.put(DataKeys.LIST_CREATED_AT_TIME, listCreatedAtTime);
        eventBus.post(new ShoppingListAction(ActionTypes.DELETE_LIST, bundle));
    }
}
