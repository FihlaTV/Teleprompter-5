package com.tjohnn.teleprompter.data.repository;

import com.tjohnn.teleprompter.data.Script;
import com.tjohnn.teleprompter.data.ScriptDao;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Single;

public class ScriptRepository {

    private final ScriptDao scriptDao;

    @Inject
    public ScriptRepository(ScriptDao scriptDao) {
        this.scriptDao = scriptDao;
    }

    public LiveData<List<Script>> getScripts(){
        return scriptDao.getAll();
    }

    public LiveData<Script> getScriptById(long id){
        return scriptDao.getById(id);
    }


    public Completable deleteScript(Script script){
        return Completable.fromAction(() -> scriptDao.deleteScript(script));
    }


    public Completable saveScript(Script script){
        // if script id is zero, created a new script
        if(script.getId() <= 0){
            return Completable.fromAction(() -> scriptDao.insertScript(script));
        } else {
            return Completable.fromAction(() -> scriptDao.updateScript(script));
        }
    }

    public Single<Script> getScriptByIdSingle(long scriptId) {
        return scriptDao.getScriptByIdSingle(scriptId);
    }
}
