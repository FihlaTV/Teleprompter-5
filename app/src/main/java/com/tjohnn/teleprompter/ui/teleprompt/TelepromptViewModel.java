package com.tjohnn.teleprompter.ui.teleprompt;

import android.app.Application;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class TelepromptViewModel extends AndroidViewModel {

    @Inject
    public TelepromptViewModel(@NonNull Application application) {
        super(application);
    }



}
