package com.tjohnn.teleprompter.utils;


/**
 * For wrapping livedata events that are being sent to view from viewmodel
 * Helps to avoid re-using of livedata content e.g after rotation etc
 * @param <T>
 */
public class RxEventWrapper<T> {

    private T content;
    private boolean contentUsed;

    public RxEventWrapper(T content) {
        this.content = content;
    }

    public T getContentIfNotUsed(){
        if(!contentUsed){
            contentUsed = true;
            return content;
        }

        return null;
    }

    public T peekContent(){
        return content;
    }

}
