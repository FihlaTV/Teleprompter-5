package com.tjohnn.teleprompter.utils;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class AppSchedulers {

    @Inject
    public AppSchedulers(){}

    public Scheduler io(){
        return Schedulers.io();
    }

    public Scheduler computation(){
        return Schedulers.computation();
    }

    public Scheduler trampoline(){
        return Schedulers.trampoline();
    }

    public Scheduler main(){
        return AndroidSchedulers.mainThread();
    }

}
