package com.tjohnn.teleprompter.data.repository;

import com.tjohnn.teleprompter.data.Script;
import com.tjohnn.teleprompter.data.ScriptDao;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;

public class ScriptRepository {

    private final ScriptDao scriptDao;

    @Inject
    public ScriptRepository(ScriptDao scriptDao) {
        this.scriptDao = scriptDao;
    }

    public LiveData<List<Script>> getScripts(){
        return scriptDao.getAll();
    }


    public Completable insertScript(Script script){
        return Completable.fromAction(() -> scriptDao.insertScript(script));
    }
}
