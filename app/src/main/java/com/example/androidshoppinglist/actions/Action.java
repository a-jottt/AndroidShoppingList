package com.example.androidshoppinglist.actions;

/**
 * Created by joanna on 29.06.16.
 */
public interface Action {
    ActionType getType();
    DataBundle getData();
}
