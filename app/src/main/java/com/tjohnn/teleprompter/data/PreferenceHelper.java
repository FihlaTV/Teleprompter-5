package com.tjohnn.teleprompter.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.tjohnn.teleprompter.R;
import timber.log.Timber;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferenceHelper {

    private  SharedPreferences defaultPreferences;
    private  Context mContext;

    @Inject
    public PreferenceHelper(Context context) {
        mContext = context;
        defaultPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public int getTelepromptTextSize(){
        Timber.d("normal_teleprompt_font_size: " + mContext.getString(R.string.normal_teleprompt_font_size));
        String value = defaultPreferences.getString(mContext.getString(R.string.pref_teleprompt_font_size_key),
                mContext.getString(R.string.normal_teleprompt_font_size));
        return Integer.valueOf(value);
    }


    public String getTelepromptTheme(){
        return defaultPreferences.getString(mContext.getString(R.string.pref_teleprompt_theme_key),
                mContext.getResources().getString(R.string.pref_theme_light));
    }


}
