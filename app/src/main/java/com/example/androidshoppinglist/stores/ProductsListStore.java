package com.example.androidshoppinglist.stores;

import com.example.androidshoppinglist.actions.ActionTypes;
import com.example.androidshoppinglist.actions.DataKeys;
import com.example.androidshoppinglist.actions.ProductsListAction;
import com.example.androidshoppinglist.data.ActivityEvent;
import com.example.androidshoppinglist.data.DeleteProductEvent;
import com.example.androidshoppinglist.data.ProductBoughtEvent;
import com.example.androidshoppinglist.models.Product;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by joanna on 01.09.16.
 */
@Singleton
public class ProductsListStore {

    protected ArrayList<Product> products;
    protected EventBus eventBus;

    @Inject
    public ProductsListStore(EventBus eventBus) {
        this.products = new ArrayList<>();
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    @Subscribe
    public void addProductToList(ProductsListAction action) {
        if (action.getType().equals(ActionTypes.ADD_PRODUCT_TO_LIST)) {
            eventBus.post(action.getData().get(DataKeys.PRODUCT, -1));
        }
    }

    @Subscribe
    public void getProductsListFromDb(ProductsListAction action) {
        if (action.getType().equals(ActionTypes.GET_PRODUCTS_LIST_FROM_DATABASE)) {
            ActivityEvent activityEvent = new ActivityEvent(action.getType());
            long listCreatedAtTime = (long) action.getData().get(DataKeys.LIST_CREATED_AT_TIME, -1);
            activityEvent.setListCreatedAtTime(listCreatedAtTime);
            eventBus.post(activityEvent);
        }
    }

    @Subscribe
    public void setProductBought(ProductsListAction action) {
        if (action.getType().equals(ActionTypes.SET_PRODUCT_BOUGHT)) {
            Product productBought = (Product) action.getData().get(DataKeys.PRODUCT, -1);
            eventBus.post(new ProductBoughtEvent(productBought, true));
        }
    }

    @Subscribe
    public void setProductNotBought(ProductsListAction action) {
        if (action.getType().equals(ActionTypes.SET_PRODUCT_NOT_BOUGHT)) {
            Product productNotBought = (Product) action.getData().get(DataKeys.PRODUCT, -1);
            eventBus.post(new ProductBoughtEvent(productNotBought, false));
        }
    }

    @Subscribe
    public void deleteProduct(ProductsListAction action) {
        if (action.getType().equals(ActionTypes.DELETE_PRODUCT)) {
            Product productToDelete = (Product) action.getData().get(DataKeys.PRODUCT, -1);
            eventBus.post(new DeleteProductEvent(productToDelete));
        }
    }
}
