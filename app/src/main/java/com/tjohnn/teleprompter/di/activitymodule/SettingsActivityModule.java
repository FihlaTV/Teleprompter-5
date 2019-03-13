package com.tjohnn.teleprompter.di.activitymodule;

import com.tjohnn.teleprompter.di.annotations.ActivityScoped;
import com.tjohnn.teleprompter.di.annotations.FragmentScoped;
import com.tjohnn.teleprompter.ui.settings.SettingsActivity;
import com.tjohnn.teleprompter.ui.settings.SettingsFragment;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface SettingsActivityModule {


    @FragmentScoped
    @ContributesAndroidInjector
    SettingsFragment settingFragment();

    @ActivityScoped
    @Binds
    AppCompatActivity activity(SettingsActivity settingsActivity);


}
