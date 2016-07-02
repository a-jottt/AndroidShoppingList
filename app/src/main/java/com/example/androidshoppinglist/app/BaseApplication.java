package com.example.androidshoppinglist.app;

import android.app.Application;

import com.example.androidshoppinglist.BuildConfig;
import com.example.androidshoppinglist.app.modules.ApplicationModule;
import com.example.androidshoppinglist.views.MainActivity;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;
import io.realm.RealmConfiguration;

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

        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);

        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build());
        }
    }

    public ApplicationComponent component() {
        return component;
    }

}