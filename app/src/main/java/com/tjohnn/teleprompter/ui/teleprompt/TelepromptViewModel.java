package com.tjohnn.teleprompter.ui.teleprompt;

import android.app.Application;

import com.tjohnn.teleprompter.data.Script;
import com.tjohnn.teleprompter.data.repository.ScriptRepository;
import com.tjohnn.teleprompter.utils.AppSchedulers;
import com.tjohnn.teleprompter.utils.RxEventWrapper;

import java.net.HttpCookie;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;

public class TelepromptViewModel extends AndroidViewModel {

    private final ScriptRepository scriptRepository;
    private final AppSchedulers schedulers;
    private MutableLiveData<Script> script = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<RxEventWrapper<String>> snackBarMessage = new MutableLiveData<>();

    @Inject
    TelepromptViewModel(@NonNull Application application,
                        ScriptRepository scriptRepository, AppSchedulers schedulers) {
        super(application);
        this.scriptRepository = scriptRepository;
        this.schedulers = schedulers;
    }


    void loadScript(long mScriptId) {
        compositeDisposable.add(
                scriptRepository.getScriptByIdSingle(mScriptId)
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.main())
                        .subscribe(response -> {
                            script.setValue(response);
                        }, throwable ->
                                snackBarMessage.setValue(new RxEventWrapper<>(throwable.getMessage())))
        );
    }

    public LiveData<Script> getScript() {
        return script;
    }

    LiveData<RxEventWrapper<String>> getSnackBarMessage() {
        return snackBarMessage;
    }

    void markComplete() {
        Script script = this.script.getValue();
        if (script != null) {
            script.setTeleprompted(true);
            compositeDisposable.add(
                    scriptRepository.saveScript(script)
                            .subscribeOn(schedulers.io())
                            .observeOn(schedulers.main())
                            .subscribe(
                                    () -> {
                                    },
                                    throwable -> snackBarMessage.setValue(new RxEventWrapper<>(throwable.getMessage()))
                            )
            );
        }

    }
}
