package com.tjohnn.teleprompter.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public interface ScriptDao {

    @Insert
    void insertScript(Script script);

    @Query("SELECT * FROM scripts")
    LiveData<List<Script>> getAll();

    @Query("SELECT * FROM scripts WHERE id = :id")
    LiveData<Script> getById(long id);

    // Needed for teleprompting screen, we do not need ui to keep updating while teleprompting
    @Query("SELECT * FROM scripts WHERE id = :id")
    Single<Script> getScriptByIdSingle(long id);

    @Update
    void updateScript(Script script);

    @Delete
    void deleteScript(Script script);

    // get
    @Query("SELECT * FROM scripts WHERE isTeleprompted = 0 ORDER BY telepromptingDate ASC LIMIT 1")
    List<Script> getNextScript();
}
