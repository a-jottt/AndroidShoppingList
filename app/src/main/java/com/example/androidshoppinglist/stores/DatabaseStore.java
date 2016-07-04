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
        switch (activityEvent.actionType) {
            case GET_PRODUCTS_LIST_FROM_DATABASE:
                List<Product> products = getProductsListFromDatabase(activityEvent.getListCreatedAtTime());
                eventBus.post(new ProductsListEvent(products));
                break;
            case GET_SHOPPING_LISTS_FROM_DATABASE:
                List<ShoppingListItem> list = new ArrayList<>(getShoppingListsFromDatabase());
                eventBus.post(new ShoppingListEvent(list));
                break;
        }
    }

    @Subscribe
    public void onShoppingListItemUpdate(ShoppingListItem shoppingListItem) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(shoppingListItem));
    }

    private List<Product> getProductsListFromDatabase(long listCreatedAtTime) {
        ShoppingListItem shoppingListItem =
                realm.where(ShoppingListItem.class).equalTo("createdAtTime", listCreatedAtTime).findFirst();
        return shoppingListItem.getProducts();

    }

    private List<ShoppingListItem> getShoppingListsFromDatabase() {
        return realm.where(ShoppingListItem.class).findAll();
    }
}
