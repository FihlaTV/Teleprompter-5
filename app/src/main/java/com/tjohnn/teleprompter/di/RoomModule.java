package com.tjohnn.teleprompter.di;

import android.content.Context;

import com.tjohnn.teleprompter.data.AppDatabase;
import com.tjohnn.teleprompter.data.ScriptDao;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RoomModule {

    @Singleton
    @Provides
    static public AppDatabase appDatabase(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    static public ScriptDao scriptDao(AppDatabase appDatabase){
        return appDatabase.scriptDao();
    }


}
