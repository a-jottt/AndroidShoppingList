package com.example.androidshoppinglist.actions;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by joanna on 29.06.16.
 */
public class ActionCreator {

    private EventBus eventBus;

    public ActionCreator(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public final void addNewShoppingListAction(final String title) {
        DataBundle<DataKeys> bundle = new DataBundle<>();
        bundle.put(DataKeys.SHOPPING_LIST_TITLE, title);
        eventBus.post(new ShoppingListAction(ActionTypes.ADD_NEW_SHOPPING_LIST, bundle));
    }
}
