package com.tjohnn.teleprompter;

import com.tjohnn.teleprompter.daggerjetifier.DaggerApplicationX;
import com.tjohnn.teleprompter.di.DaggerAppComponent;
import com.tjohnn.teleprompter.utils.TimberTree;

import dagger.android.AndroidInjector;
import timber.log.Timber;

public class App extends DaggerApplicationX {

    private AndroidInjector<? extends DaggerApplicationX> injector;

    @Override
    protected AndroidInjector<? extends DaggerApplicationX> applicationInjector() {
        return injector;
    }

    @Override
    public void onCreate() {
        injector =  DaggerAppComponent.builder().application(this).build();
        super.onCreate();

        if (BuildConfig.DEBUG)
            Timber.plant(new TimberTree());

    }
}
