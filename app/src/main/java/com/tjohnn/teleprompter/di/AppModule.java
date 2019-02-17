package com.tjohnn.teleprompter.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
public interface AppModule {

    @Binds
    Context provideApplication(Application application);


}
