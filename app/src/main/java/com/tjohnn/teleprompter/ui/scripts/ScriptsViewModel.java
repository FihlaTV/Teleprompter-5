package com.tjohnn.teleprompter.ui.scripts;

import android.app.Application;

import com.tjohnn.teleprompter.data.Script;
import com.tjohnn.teleprompter.data.repository.ScriptRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ScriptsViewModel extends AndroidViewModel {

    private LiveData<List<Script>> scripts;

    @Inject
    public ScriptsViewModel(@NonNull Application application, ScriptRepository repository) {
        super(application);
        scripts = repository.getScripts();
    }


    public LiveData<List<Script>> getScripts() {
        return scripts;
    }
}
