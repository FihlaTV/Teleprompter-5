package com.tjohnn.teleprompter.daggerjetifier;

import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.internal.Beta;

/** Provides an {@link AndroidInjector} of {@link Fragment}s. */
@Beta
public interface HasSupportFragmentInjectorX {

    /** Returns an {@link AndroidInjector} of {@link Fragment}s. */
    AndroidInjector<Fragment> supportFragmentInjector();
}
