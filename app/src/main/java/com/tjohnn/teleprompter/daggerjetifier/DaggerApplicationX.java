package com.tjohnn.teleprompter.daggerjetifier;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;

/**
 * An {@link android.app.Application} that injects its members and can be used to inject {@link
 * android.app.Activity}s, {@linkplain android.app.Fragment framework fragments}, {@linkplain
 * Fragment support fragments}, {@link android.app.Service}s, {@link
 * android.content.BroadcastReceiver}s, and {@link android.content.ContentProvider}s attached to it.
 */
public abstract class DaggerApplicationX extends dagger.android.DaggerApplication
        implements HasSupportFragmentInjectorX {

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    @Override
    protected abstract AndroidInjector<? extends DaggerApplicationX> applicationInjector();

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }
}
