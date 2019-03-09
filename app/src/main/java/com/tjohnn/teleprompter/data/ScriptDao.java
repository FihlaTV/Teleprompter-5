package com.tjohnn.teleprompter.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ScriptDao {

    @Insert
    void insertScript(Script script);

    @Query("SELECT * FROM scripts")
    LiveData<List<Script>> getAll();

    @Query("SELECT * FROM scripts WHERE id = :id")
    LiveData<Script> getById(long id);

    @Update
    void updateScript(Script script);

    @Delete
    void deleteScript(Script script);
}
