package com.tjohnn.teleprompter.daggerjetifier;

import android.content.Context;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.internal.Beta;

/**
 * A {@link Fragment} that injects its members in {@link #onAttach(Context)} and can be used to
 * inject child {@link Fragment}s attached to it. Note that when this fragment gets reattached, its
 * members will be injected again.
 */
@Beta
public abstract class DaggerFragmentX extends Fragment implements HasSupportFragmentInjectorX {

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjectionX.inject(this);
        super.onAttach(context);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }
}
