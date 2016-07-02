package com.example.androidshoppinglist.stores;

import android.app.Activity;

import com.example.androidshoppinglist.actions.ActionTypes;
import com.example.androidshoppinglist.actions.DataBundle;
import com.example.androidshoppinglist.actions.DataKeys;
import com.example.androidshoppinglist.actions.ShoppingListAction;
import com.example.androidshoppinglist.data.ActivityEvent;
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
                Activity activity = (Activity) data.get(DataKeys.ACTIVITY_CONTEXT, -1);
                getShoppingListsFromDb(activity);
                break;
        }
    }

    private void getShoppingListsFromDb(Activity activity) {
        ActivityEvent activityEvent = new ActivityEvent(activity);
        eventBus.post(activityEvent);
    }

    private void addNewShoppingList(String title) {
        ShoppingListItem shoppingListItem = new ShoppingListItem(title, new Date());
        eventBus.post(shoppingListItem);
    }
}
