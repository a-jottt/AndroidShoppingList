package com.example.androidshoppinglist.stores;

import com.example.androidshoppinglist.actions.ActionTypes;
import com.example.androidshoppinglist.actions.DataBundle;
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

    public ArrayList<Product> products;
    public EventBus eventBus;

    @Inject
    public ProductsListStore(EventBus eventBus) {
        this.products = new ArrayList<>();
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    @Subscribe
    public final void onProductsListAction(ProductsListAction action) {
        DataBundle<DataKeys> data = action.getData();
        ActionTypes actionType = action.getType();
        switch (actionType) {
            case ADD_PRODUCT_TO_LIST:
                Product product = (Product) data.get(DataKeys.PRODUCT, -1);
                addProductToList(product);
                break;
            case GET_PRODUCTS_LIST_FROM_DATABASE:
                long listCreatedAtTime = (long) data.get(DataKeys.LIST_CREATED_AT_TIME, -1);
                getProductsListFromDb(listCreatedAtTime, ActionTypes.GET_PRODUCTS_LIST_FROM_DATABASE);
                break;
            case SET_PRODUCT_BOUGHT:
                Product productBought = (Product) data.get(DataKeys.PRODUCT, -1);
                setProductBought(productBought);
                break;
            case SET_PRODUCT_NOT_BOUGHT:
                Product productNotBought = (Product) data.get(DataKeys.PRODUCT, -1);
                setProductNotBought(productNotBought);
                break;
            case DELETE_PRODUCT:
                Product productToDelete = (Product) data.get(DataKeys.PRODUCT, -1);
                deleteProduct(productToDelete);
        }
    }

    private void addProductToList(Product product) {
        eventBus.post(product);
    }

    private void getProductsListFromDb(long createdAtTime, ActionTypes actionType) {
        ActivityEvent activityEvent = new ActivityEvent(actionType);
        activityEvent.setListCreatedAtTime(createdAtTime);
        eventBus.post(activityEvent);
    }

    private void setProductBought(Product productBought) {
        eventBus.post(new ProductBoughtEvent(productBought, true));
    }

    private void setProductNotBought(Product productNotBought) {
        eventBus.post(new ProductBoughtEvent(productNotBought, false));
    }

    private void deleteProduct(Product product) {
        eventBus.post(new DeleteProductEvent(product));
    }
}
