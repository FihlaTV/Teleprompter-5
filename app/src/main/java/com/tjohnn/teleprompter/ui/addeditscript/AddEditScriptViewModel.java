package com.tjohnn.teleprompter.ui.addeditscript;

import android.app.Application;

import com.tjohnn.teleprompter.R;
import com.tjohnn.teleprompter.data.Script;
import com.tjohnn.teleprompter.data.repository.ScriptRepository;
import com.tjohnn.teleprompter.utils.AppSchedulers;
import com.tjohnn.teleprompter.utils.RxEventWrapper;
import com.tjohnn.teleprompter.utils.TextUtils;

import java.util.Date;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;

public class AddEditScriptViewModel extends AndroidViewModel {

    private final ScriptRepository scriptRepository;
    private final AppSchedulers schedulers;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<RxEventWrapper<String>> snackBarMessage = new MutableLiveData<>();
    private MutableLiveData<RxEventWrapper<Boolean>> onScriptAdded = new MutableLiveData<>();
    private MutableLiveData<RxEventWrapper<String>> formError = new MutableLiveData<>();

    @Inject
    public AddEditScriptViewModel(@NonNull Application application,
                                  ScriptRepository scriptRepository, AppSchedulers schedulers) {
        super(application);
        this.scriptRepository = scriptRepository;
        this.schedulers = schedulers;
    }

    void saveScript(Script script){
        String error = "";
        if(TextUtils.isEmpty(script.getTitle())){
            error += getApplication().getString(R.string.title_may_not_be_empty) + "\n";
        }
        if(TextUtils.isEmpty(script.getText())){
            error += getApplication().getString(R.string.script_text_may_not_be_empty) + "\n";
        }

        if(script.getTelepromptingDate() == null || script.getTelepromptingDate().before(new Date())){
            error += getApplication().getString(R.string.teleprompting_date_must_be_after_now) + "\n";
        }

        formError.setValue(new RxEventWrapper<>(error));
        if(!TextUtils.isEmpty(error)){

            return;
        }

        compositeDisposable.add(
                scriptRepository.insertScript(script)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.main())
                .subscribe(
                        () -> onScriptAdded.setValue(new RxEventWrapper<>(true)),
                        throwable -> snackBarMessage.setValue(new RxEventWrapper<>(throwable.getMessage()))
                )
        );

    }


    LiveData<RxEventWrapper<String>> getSnackBarMessage() {
        return snackBarMessage;
    }

    LiveData<RxEventWrapper<String>> getFormError() {
        return formError;
    }

    LiveData<RxEventWrapper<Boolean>> getOnScriptAdded() {
        return onScriptAdded;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
