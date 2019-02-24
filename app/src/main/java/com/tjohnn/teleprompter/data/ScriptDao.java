package com.tjohnn.teleprompter.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ScriptDao {

    @Insert
    void insertScript(Script script);

    @Query("SELECT * FROM scripts")
    LiveData<List<Script>> getAll();
}
