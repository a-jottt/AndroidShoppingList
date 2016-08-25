package com.example.androidshoppinglist.data;

/**
 * Created by joanna on 25.08.16.
 */
public class ActionEvent {

    private final String message;

    public ActionEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
