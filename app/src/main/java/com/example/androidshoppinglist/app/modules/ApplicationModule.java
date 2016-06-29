package com.example.androidshoppinglist.app.modules;

/**
 * Created by joanna on 29.06.16.
 */

import android.app.Application;
import android.content.Context;

import com.example.androidshoppinglist.actions.ActionCreator;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    Application application;
    Context context;

    public ApplicationModule(Application app) {
        this.application = app;
        this.context = app.getApplicationContext();
    }

    @Provides
    @Singleton
    ActionCreator provideActionCreator() {
        return new ActionCreator(provideEventBus());
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return EventBus.getDefault();
    }
}
