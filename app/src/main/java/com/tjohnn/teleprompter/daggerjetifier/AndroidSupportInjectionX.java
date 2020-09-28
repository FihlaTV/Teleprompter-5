package com.tjohnn.teleprompter.daggerjetifier;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
//import dagger.android.support.HasSupportFragmentInjector;
import dagger.internal.Beta;

import static android.util.Log.DEBUG;
import static dagger.internal.Preconditions.checkNotNull;

/** Injects core Android types from support libraries. */
@Beta
public final class AndroidSupportInjectionX {
    private static final String TAG = "dagger.android.support";

    /**
     * Injects {@code fragment} if an associated {@link dagger.android.AndroidInjector} implementation
     * can be found, otherwise throws an {@link IllegalArgumentException}.
     *
     * <p>Uses the following algorithm to find the appropriate {@code AndroidInjector<Fragment>} to
     * use to inject {@code fragment}:
     *
     * <ol>
     *   <li>Walks the parent-fragment hierarchy to find the a fragment that implements {@link
     *       HasSupportFragmentInjector}, and if none do
     *   <li>Uses the {@code fragment}'s {@link Fragment#getActivity() activity} if it implements
     *       {@link HasSupportFragmentInjector}, and if not
     *   <li>Uses the {@link android.app.Application} if it implements {@link
     *       HasSupportFragmentInjector}.
     * </ol>
     *
     * If none of them implement {@link HasSupportFragmentInjector}, a {@link
     * IllegalArgumentException} is thrown.
     *
     * @throws IllegalArgumentException if no parent fragment, activity, or application implements
     *     {@link HasSupportFragmentInjector}.
     */
    public static void inject(Fragment fragment) {
        checkNotNull(fragment, "fragment");
        HasSupportFragmentInjectorX hasSupportFragmentInjector = findHasSupportFragmentInjector(fragment);
        if (Log.isLoggable(TAG, DEBUG)) {
            Log.d(
                    TAG,
                    String.format(
                            "An injector for %s was found in %s",
                            fragment.getClass().getCanonicalName(),
                            hasSupportFragmentInjector.getClass().getCanonicalName()));
        }

        AndroidInjector<Fragment> fragmentInjector =
                hasSupportFragmentInjector.supportFragmentInjector();
        checkNotNull(
                fragmentInjector,
                "%s.supportFragmentInjector() returned null",
                hasSupportFragmentInjector.getClass());

        fragmentInjector.inject(fragment);
    }

    private static HasSupportFragmentInjectorX findHasSupportFragmentInjector(Fragment fragment) {
        Fragment parentFragment = fragment;
        while ((parentFragment = parentFragment.getParentFragment()) != null) {
            if (parentFragment instanceof HasSupportFragmentInjectorX) {
                return (HasSupportFragmentInjectorX) parentFragment;
            }
        }
        Activity activity = fragment.getActivity();
        if (activity instanceof HasSupportFragmentInjectorX) {
            return (HasSupportFragmentInjectorX) activity;
        }
        if (activity.getApplication() instanceof HasSupportFragmentInjectorX) {
            return (HasSupportFragmentInjectorX) activity.getApplication();
        }
        throw new IllegalArgumentException(
                String.format("No injector was found for %s", fragment.getClass().getCanonicalName()));
    }

    private AndroidSupportInjectionX() {}
}

