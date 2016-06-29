package com.example.androidshoppinglist.stores;

import com.example.androidshoppinglist.actions.ActionTypes;
import com.example.androidshoppinglist.actions.DataBundle;
import com.example.androidshoppinglist.actions.DataKeys;
import com.example.androidshoppinglist.actions.ShoppingListAction;
import com.example.androidshoppinglist.models.ShoppingListItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by joanna on 29.06.16.
 */
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
        String title = (String) data.get(DataKeys.SHOPPING_LIST_TITLE, -1);
        ActionTypes actionType = action.getType();
        switch (actionType) {
            case ADD_NEW_SHOPPING_LIST:
                addNewShoppingList(title);
                break;
        }
    }

    private void addNewShoppingList(String title) {

    }
}
