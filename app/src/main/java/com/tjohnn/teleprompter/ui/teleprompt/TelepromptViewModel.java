package com.tjohnn.teleprompter.ui.teleprompt;

import android.app.Application;

import com.tjohnn.teleprompter.data.Script;
import com.tjohnn.teleprompter.data.repository.ScriptRepository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TelepromptViewModel extends AndroidViewModel {

    private final ScriptRepository scriptRepository;
    private LiveData<Script> script;

    @Inject
    TelepromptViewModel(@NonNull Application application, ScriptRepository scriptRepository) {
        super(application);
        this.scriptRepository = scriptRepository;
    }


    void loadScript(long mScriptId) {
        script = scriptRepository.getScriptById(mScriptId);
    }

    public LiveData<Script> getScript() {
        return script;
    }

    public void markComplete(long mScriptId) {

    }
}
