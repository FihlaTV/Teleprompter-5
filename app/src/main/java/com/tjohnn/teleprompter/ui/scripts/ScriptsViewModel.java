package com.tjohnn.teleprompter.ui.scripts;

import android.app.Application;

import com.tjohnn.teleprompter.data.Script;
import com.tjohnn.teleprompter.data.repository.ScriptRepository;
import com.tjohnn.teleprompter.utils.AppSchedulers;
import com.tjohnn.teleprompter.utils.RxEventWrapper;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;

public class ScriptsViewModel extends AndroidViewModel {

    private final ScriptRepository scriptRepository;
    private final AppSchedulers schedulers;
    private LiveData<List<Script>> scripts;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<RxEventWrapper<String>> snackBarMessage = new MutableLiveData<>();

    @Inject
    ScriptsViewModel(@NonNull Application application,
                     ScriptRepository scriptRepository, AppSchedulers schedulers) {
        super(application);
        this.scriptRepository = scriptRepository;
        this.schedulers = schedulers;
        scripts = scriptRepository.getScripts();
    }


    public LiveData<List<Script>> getScripts() {
        return scripts;
    }

    void deleteScript(Script script) {
        compositeDisposable.add(
                scriptRepository.deleteScript(script)
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.main())
                        .subscribe(
                                () -> {},
                                throwable -> snackBarMessage.setValue(new RxEventWrapper<>(throwable.getMessage()))
                        )
        );

    }
}
