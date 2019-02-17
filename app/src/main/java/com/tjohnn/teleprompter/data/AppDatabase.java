package com.tjohnn.teleprompter.data;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Script.class},
        version = 1, exportSchema = false)
@TypeConverters(RoomConverters.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "com_tjohnn_teleprompter";


    public abstract ScriptDao scriptDao();
}
