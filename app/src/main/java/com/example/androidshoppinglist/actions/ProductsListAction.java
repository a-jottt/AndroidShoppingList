package com.example.androidshoppinglist.actions;

/**
 * Created by joanna on 01.09.16.
 */
public class ProductsListAction implements Action {

    private ActionTypes type;
    private DataBundle<DataKeys> bundle;

    public ProductsListAction(ActionTypes type) {
        this.type = type;
        this.bundle = new DataBundle<>();
    }

    public ProductsListAction(ActionTypes type, DataBundle<DataKeys> bundle) {
        this.type = type;
        this.bundle = bundle;
    }

    @Override
    public ActionTypes getType() {
        return type;
    }

    @Override
    public DataBundle<DataKeys> getData() {
        return bundle;
    }
}