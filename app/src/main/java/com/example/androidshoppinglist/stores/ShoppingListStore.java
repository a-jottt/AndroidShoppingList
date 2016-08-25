package com.example.androidshoppinglist.stores;

import android.app.Activity;

import com.example.androidshoppinglist.actions.ActionTypes;
import com.example.androidshoppinglist.actions.DataBundle;
import com.example.androidshoppinglist.actions.DataKeys;
import com.example.androidshoppinglist.actions.ShoppingListAction;
import com.example.androidshoppinglist.data.ActivityEvent;
import com.example.androidshoppinglist.data.ArchiveOrDeleteListEvent;
import com.example.androidshoppinglist.data.GetListActionType;
import com.example.androidshoppinglist.data.ProductBoughtEvent;
import com.example.androidshoppinglist.models.Product;
import com.example.androidshoppinglist.models.ShoppingListItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by joanna on 29.06.16.
 */
@Singleton
public class ShoppingListStore {

    public ArrayList<ShoppingListItem> shoppingListItems;
    public EventBus eventBus;

    @Inject
    public ShoppingListStore(EventBus eventBus) {
        this.shoppingListItems = new ArrayList<>();
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    public void onPause() {
        eventBus.unregister(this);
    }

    @Subscribe
    public final void onShoppingListAction(ShoppingListAction action) {
        DataBundle<DataKeys> data = action.getData();
        ActionTypes actionType = action.getType();
        switch (actionType) {
            case ADD_NEW_SHOPPING_LIST:
                String title = (String) data.get(DataKeys.SHOPPING_LIST_TITLE, -1);
                addNewShoppingList(title);
                break;
            case GET_SHOPPING_LISTS_FROM_DATABASE:
                GetListActionType listType = (GetListActionType) data.get(DataKeys.GET_LIST_ACTION_TYPE, -1);
                getShoppingListsFromDb(ActionTypes.GET_SHOPPING_LISTS_FROM_DATABASE, listType);
                break;
            case ADD_PRODUCT_TO_LIST:
                Product product = (Product) data.get(DataKeys.PRODUCT, -1);
                addProductToList(product);
                break;
            case GET_PRODUCTS_LIST_FROM_DATABASE:
                long listCreatedAtTime = (long) data.get(DataKeys.LIST_CREATED_AT_TIME, -1);
                getProductsListFromDb(listCreatedAtTime, ActionTypes.GET_PRODUCTS_LIST_FROM_DATABASE);
                break;
            case SET_PRODUCT_BOUGHT:
                Product productBought = (Product) data.get(DataKeys.PRODUCT, -1);
                setProductBought(productBought);
                break;
            case SET_PRODUCT_NOT_BOUGHT:
                Product productNotBought = (Product) data.get(DataKeys.PRODUCT, -1);
                setProductNotBought(productNotBought);
                break;
            case ARCHIVE_LIST:
                long archivedListCreatedAtTime = (long) data.get(DataKeys.LIST_CREATED_AT_TIME, -1);
                archiveList(archivedListCreatedAtTime);
                break;
            case DELETE_LIST:
                long deletedListCreatedAtTime = (long) data.get(DataKeys.LIST_CREATED_AT_TIME, -1);
                deleteList(deletedListCreatedAtTime);
                break;
        }
    }

    private void getShoppingListsFromDb(ActionTypes actionType, GetListActionType listType) {
        ActivityEvent activityEvent = new ActivityEvent(actionType);
        activityEvent.setGetListActionType(listType);
        eventBus.post(activityEvent);
    }

    private void addNewShoppingList(String title) {
        ShoppingListItem shoppingListItem = new ShoppingListItem(title, new Date());
        eventBus.post(shoppingListItem);
    }

    private void addProductToList(Product product) {
        eventBus.post(product);
    }

    private void getProductsListFromDb(long createdAtTime, ActionTypes actionType) {
        ActivityEvent activityEvent = new ActivityEvent(actionType);
        activityEvent.setListCreatedAtTime(createdAtTime);
        eventBus.post(activityEvent);
    }

    private void setProductBought(Product productBought) {
        eventBus.post(new ProductBoughtEvent(productBought, true));
    }

    private void setProductNotBought(Product productNotBought) {
        eventBus.post(new ProductBoughtEvent(productNotBought, false));
    }

    private void archiveList(long archivedListCreatedAtTime) {
        eventBus.post(new ArchiveOrDeleteListEvent(archivedListCreatedAtTime,
                ArchiveOrDeleteListEvent.EventStatus.ARCHIVE));
    }

    private void deleteList(long deletedListCreatedAtTime) {
        eventBus.post(new ArchiveOrDeleteListEvent(deletedListCreatedAtTime,
                ArchiveOrDeleteListEvent.EventStatus.DELETE));
    }
}
