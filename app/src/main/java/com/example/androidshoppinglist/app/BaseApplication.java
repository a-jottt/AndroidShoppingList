package com.example.androidshoppinglist.app;

import android.app.Application;

import com.example.androidshoppinglist.app.modules.ApplicationModule;
import com.example.androidshoppinglist.views.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by joanna on 29.06.16.
 */
public class BaseApplication extends Application {

    @Singleton
    @Component(modules = {ApplicationModule.class})
    public interface ApplicationComponent {
        void inject(BaseApplication application);
        void inject (MainActivity mainActivity);
    }
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerBaseApplication_ApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        component().inject(this);
    }

    public ApplicationComponent component() {
        return component;
    }

}