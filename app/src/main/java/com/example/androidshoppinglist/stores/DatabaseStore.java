package com.example.androidshoppinglist.stores;

import com.example.androidshoppinglist.data.ActivityEvent;
import com.example.androidshoppinglist.data.ProductsListEvent;
import com.example.androidshoppinglist.data.ShoppingListEvent;
import com.example.androidshoppinglist.models.Product;
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
    public void onProductUpdate(Product product) {
        ShoppingListItem shoppingListItem =
                realm.where(ShoppingListItem.class).equalTo("createdAtTime", product.getListCreatedAtTime()).findFirst();

        realm.beginTransaction();
        shoppingListItem.addToProducts(product);
        realm.commitTransaction();

        List<Product> products = new ArrayList<>(shoppingListItem.getProducts());
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(product));

        eventBus.post(shoppingListItem);
        eventBus.post(new ProductsListEvent(products));
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
