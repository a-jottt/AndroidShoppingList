package com.example.androidshoppinglist.actions;

/**
 * Created by joanna on 29.06.16.
 */
public class ShoppingListAction implements Action {

    private ActionTypes type;
    private DataBundle<DataKeys> bundle;

    public ShoppingListAction(ActionTypes type) {
        this.type = type;
        this.bundle = new DataBundle<>();
    }

    public ShoppingListAction(ActionTypes type, DataBundle<DataKeys> bundle) {
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
