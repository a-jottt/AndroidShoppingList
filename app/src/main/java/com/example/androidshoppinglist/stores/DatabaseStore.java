package com.example.androidshoppinglist.stores;

import com.example.androidshoppinglist.data.ActivityEvent;
import com.example.androidshoppinglist.data.ShoppingListEvent;
import com.example.androidshoppinglist.models.ShoppingListItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by joanna on 02.07.16.
 */
public class DatabaseStore {

    public EventBus eventBus;
    private Realm realm;

    @Inject
    public DatabaseStore(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
        realm = Realm.getDefaultInstance();
    }

    public void onPause() {
        eventBus.unregister(this);
    }

    @Subscribe
    public void returnShoppingListsFromDb(ActivityEvent activityEvent) {
        List<ShoppingListItem> list = new ArrayList<>(realm.where(ShoppingListItem.class).findAll());
        eventBus.post(new ShoppingListEvent(list));
    }

    @Subscribe
    public void onShoppingListItemUpdate(ShoppingListItem shoppingListItem) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(shoppingListItem));
    }
}
