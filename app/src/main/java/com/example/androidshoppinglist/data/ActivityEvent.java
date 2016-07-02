package com.example.androidshoppinglist.data;

import android.app.Activity;

/**
 * Created by joanna on 03.07.16.
 */
public class ActivityEvent {

    public final Activity activity;

    public ActivityEvent(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }
}
