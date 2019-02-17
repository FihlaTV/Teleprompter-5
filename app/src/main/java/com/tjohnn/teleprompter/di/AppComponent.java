package com.tjohnn.teleprompter.di;

import android.app.Application;

import com.tjohnn.teleprompter.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AppModule.class, AndroidSupportInjectionModule.class,
        RoomModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        AppComponent.Builder application(Application app);

        AppComponent build();

    }

}
