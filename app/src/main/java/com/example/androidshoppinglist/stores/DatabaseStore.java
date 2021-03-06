package com.example.androidshoppinglist.stores;

import com.example.androidshoppinglist.actions.ActionTypes;
import com.example.androidshoppinglist.data.ActionEvent;
import com.example.androidshoppinglist.data.ActivityEvent;
import com.example.androidshoppinglist.data.ArchiveOrDeleteListEvent;
import com.example.androidshoppinglist.data.DeleteProductEvent;
import com.example.androidshoppinglist.data.GetListActionType;
import com.example.androidshoppinglist.data.ProductBoughtEvent;
import com.example.androidshoppinglist.data.ProductsListEvent;
import com.example.androidshoppinglist.data.ShoppingListEvent;
import com.example.androidshoppinglist.models.Product;
import com.example.androidshoppinglist.models.ShoppingListItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by joanna on 02.07.16.
 */
@Singleton
public class DatabaseStore {

    protected EventBus eventBus;
    protected Realm realm;

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
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(product));

        ShoppingListItem shoppingListItem =
                realm.where(ShoppingListItem.class).equalTo("createdAtTime", product.getListCreatedAtTime()).findFirst();
        List<Product> products = shoppingListItem.getProducts();

        realm.beginTransaction();
        products.add(product);
        realm.commitTransaction();

        eventBus.post(new ProductsListEvent(products));
    }

    @Subscribe
    public void returnShoppingListsFromDb(ActivityEvent activityEvent) {
        if (activityEvent.actionType == ActionTypes.GET_PRODUCTS_LIST_FROM_DATABASE) {
            List<Product> products = getProductsListFromDatabase(activityEvent.getListCreatedAtTime());
            eventBus.post(new ProductsListEvent(products));

        } else if (activityEvent.actionType == ActionTypes.GET_SHOPPING_LISTS_FROM_DATABASE) {
            List<ShoppingListItem> list =
                    new ArrayList<>(getShoppingListsFromDatabase(activityEvent.getGetListActionType()));
            eventBus.post(new ShoppingListEvent(list, activityEvent.getGetListActionType()));

        }
    }

    @Subscribe
    public void onShoppingListItemUpdate(ShoppingListItem shoppingListItem) {
        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(shoppingListItem));
    }

    @Subscribe
    public void onProductBoughtUpdate(ProductBoughtEvent productBoughtEvent) {
        realm.beginTransaction();
        productBoughtEvent.getProduct().setBought(productBoughtEvent.isBought());
        realm.commitTransaction();
    }

    @Subscribe
    public void onArchiveOrDeleteListEvent(ArchiveOrDeleteListEvent archiveOrDeleteListEvent) {
        ShoppingListItem shoppingListItem = realm.where(ShoppingListItem.class)
                .equalTo("createdAtTime", archiveOrDeleteListEvent.getListCreatedAtTime()).findFirst();

        if (archiveOrDeleteListEvent.getStatus().equals(ArchiveOrDeleteListEvent.EventStatus.ARCHIVE)) {

            realm.beginTransaction();
            shoppingListItem.setArchived(true);
            realm.commitTransaction();

            eventBus.post(new ActionEvent("List has been archived!"));

        } else if (archiveOrDeleteListEvent.getStatus().equals(ArchiveOrDeleteListEvent.EventStatus.DELETE)) {

            realm.executeTransaction(realm1 -> {
                shoppingListItem.getProducts().deleteAllFromRealm();
                shoppingListItem.deleteFromRealm();
            });

            eventBus.post(new ActionEvent("List has been deleted!"));
        }
    }

    @Subscribe
    public void onDeleteProductEvent(DeleteProductEvent deleteProductEvent) {
        Product product = realm.where(Product.class)
                .equalTo("createdAtTime", deleteProductEvent.getProduct().getCreatedAt().getTime()).findFirst();

        ShoppingListItem shoppingListItem = realm.where(ShoppingListItem.class)
                .equalTo("createdAtTime", deleteProductEvent.getProduct().getListCreatedAtTime()).findFirst();

        realm.executeTransaction(realm1 -> {
            shoppingListItem.getProducts().remove(product);
            product.deleteFromRealm();
        });

        eventBus.post(new ProductsListEvent(shoppingListItem.getProducts()));
    }

    private List<Product> getProductsListFromDatabase(long listCreatedAtTime) {
        ShoppingListItem shoppingListItem =
                realm.where(ShoppingListItem.class).equalTo("createdAtTime", listCreatedAtTime).findFirst();
        return shoppingListItem.getProducts();
    }

    private List<ShoppingListItem> getShoppingListsFromDatabase(GetListActionType listType) {
        List<ShoppingListItem> list = new ArrayList<>();

        if (listType.equals(GetListActionType.ARCHIVED)) {
            list = realm.where(ShoppingListItem.class).equalTo("archived", true)
                    .findAll().sort("createdAt", Sort.DESCENDING);

        } else if (listType.equals(GetListActionType.CURRENT)) {
            list = realm.where(ShoppingListItem.class).equalTo("archived", false)
                    .findAll().sort("createdAt", Sort.DESCENDING);

        }

        return list;
    }
}
