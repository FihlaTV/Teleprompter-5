package com.tjohnn.teleprompter.data;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface ScriptDao {

    @Insert
    void insertScript(Script script);

}
