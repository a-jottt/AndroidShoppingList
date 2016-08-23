package com.example.androidshoppinglist.data;

import android.app.Activity;

import com.example.androidshoppinglist.actions.ActionTypes;

/**
 * Created by joanna on 03.07.16.
 */
public class ActivityEvent {

    private Activity activity;
    public final ActionTypes actionType;
    private long listCreatedAtTime;

    public ActivityEvent(ActionTypes actionType) {
        this.actionType = actionType;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public long getListCreatedAtTime() {
        return listCreatedAtTime;
    }

    public void setListCreatedAtTime(long listCreatedAtTime) {
        this.listCreatedAtTime = listCreatedAtTime;
    }

    public ActionTypes getActionType() {
        return actionType;
    }
}
