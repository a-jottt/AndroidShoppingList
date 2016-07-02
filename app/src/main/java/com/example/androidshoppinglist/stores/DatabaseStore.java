package com.example.androidshoppinglist.stores;

import android.util.Log;

import com.example.androidshoppinglist.models.ShoppingListItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by joanna on 02.07.16.
 */
public class DatabaseStore {

    public EventBus eventBus;

    @Inject
    public DatabaseStore(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    public void onPause() {
        eventBus.unregister(this);
    }

    @Subscribe
    public void onShoppingListItemUpdate(ShoppingListItem shoppingListItem) {
        Realm.getDefaultInstance().executeTransaction(realm -> realm.copyToRealmOrUpdate(shoppingListItem));
    }
}
