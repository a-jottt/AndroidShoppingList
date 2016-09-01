package com.example.androidshoppinglist.stores;

import com.example.androidshoppinglist.actions.ActionTypes;
import com.example.androidshoppinglist.actions.DataKeys;
import com.example.androidshoppinglist.actions.ShoppingListAction;
import com.example.androidshoppinglist.data.ActivityEvent;
import com.example.androidshoppinglist.data.ArchiveOrDeleteListEvent;
import com.example.androidshoppinglist.data.GetListActionType;
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

    protected ArrayList<ShoppingListItem> shoppingListItems;
    protected EventBus eventBus;

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
    public void addNewShoppingList(ShoppingListAction action) {
        if (action.getType().equals(ActionTypes.ADD_NEW_SHOPPING_LIST)) {
            String title = (String) action.getData().get(DataKeys.SHOPPING_LIST_TITLE, -1);
            ShoppingListItem shoppingListItem = new ShoppingListItem(title, new Date());
            eventBus.post(shoppingListItem);
        }
    }

    @Subscribe
    public void getShoppingListsFromDb(ShoppingListAction action) {
        ActionTypes actionType = ActionTypes.GET_SHOPPING_LISTS_FROM_DATABASE;
        if (action.getType().equals(actionType)) {
            GetListActionType listType = (GetListActionType) action.getData().get(DataKeys.GET_LIST_ACTION_TYPE, -1);
            ActivityEvent activityEvent = new ActivityEvent(actionType);
            activityEvent.setGetListActionType(listType);
            eventBus.post(activityEvent);
        }

    }

    @Subscribe
    public void archiveList(ShoppingListAction action) {
        if (action.getType().equals(ActionTypes.ARCHIVE_LIST)) {
            long archivedListCreatedAtTime = (long) action.getData().get(DataKeys.LIST_CREATED_AT_TIME, -1);
            eventBus.post(new ArchiveOrDeleteListEvent(archivedListCreatedAtTime,
                    ArchiveOrDeleteListEvent.EventStatus.ARCHIVE));
        }
    }

    @Subscribe
    public void deleteList(ShoppingListAction action) {
        if (action.getType().equals(ActionTypes.DELETE_LIST)) {
            long deletedListCreatedAtTime = (long) action.getData().get(DataKeys.LIST_CREATED_AT_TIME, -1);
            eventBus.post(new ArchiveOrDeleteListEvent(deletedListCreatedAtTime,
                    ArchiveOrDeleteListEvent.EventStatus.DELETE));
        }
    }
}
