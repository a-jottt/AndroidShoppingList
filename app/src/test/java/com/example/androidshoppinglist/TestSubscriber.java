package com.example.androidshoppinglist;

import com.example.androidshoppinglist.actions.ShoppingListAction;

import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.CountDownLatch;

/**
 * Created by joanna on 13.09.16.
 */
public class TestSubscriber {

    protected ShoppingListAction resultAction;
    protected CountDownLatch signal;

    public TestSubscriber() {
    }

    public TestSubscriber(CountDownLatch signal) {
        this.signal = signal;
    }

    public ShoppingListAction getResultAction() {
        return resultAction;
    }

    @Subscribe
    public void getRepositoriesByLanguage(ShoppingListAction action) {
        resultAction = action;

        if (signal != null) {
            signal.countDown();
        }
    }
}
