package com.example.androidshoppinglist.stores;

import android.util.Log;

import com.example.androidshoppinglist.actions.ActionTypes;
import com.example.androidshoppinglist.actions.DataBundle;
import com.example.androidshoppinglist.actions.DataKeys;
import com.example.androidshoppinglist.actions.ShoppingListAction;
import com.example.androidshoppinglist.models.ShoppingListItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by joanna on 29.06.16.
 */
public class ShoppingListStore {

    @Inject DatabaseStore databaseStore;

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
        databaseStore.onPause();
    }

    @Subscribe
    public final void onShoppingListAction(ShoppingListAction action) {
        DataBundle<DataKeys> data = action.getData();
        String title = (String) data.get(DataKeys.SHOPPING_LIST_TITLE, -1);
        ActionTypes actionType = action.getType();
        switch (actionType) {
            case ADD_NEW_SHOPPING_LIST:
                addNewShoppingList(title);
                break;
        }
    }

    private void addNewShoppingList(String title) {
        ShoppingListItem shoppingListItem = new ShoppingListItem(title, new Date());
        eventBus.post(shoppingListItem);
    }
}
