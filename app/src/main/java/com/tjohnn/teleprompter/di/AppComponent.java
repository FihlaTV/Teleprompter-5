package com.tjohnn.teleprompter.di;

import android.app.Application;

import com.tjohnn.teleprompter.App;
import com.tjohnn.teleprompter.di.activitymodule.ActivityBindingModule;
import com.tjohnn.teleprompter.di.servicemodule.ServiceBindingModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AppModule.class, AndroidSupportInjectionModule.class,
        RoomModule.class, ActivityBindingModule.class,
        ViewModelBindingModule.class, ServiceBindingModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        AppComponent.Builder application(Application app);

        AppComponent build();

    }

}
