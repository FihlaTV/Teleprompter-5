package com.tjohnn.teleprompter.di.servicemodule;

import com.tjohnn.teleprompter.AppWidgetIntentService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ServiceBindingModule {

    @ContributesAndroidInjector
    AppWidgetIntentService  appWidgetIntentService();

}
