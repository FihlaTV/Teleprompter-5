package com.tjohnn.teleprompter.ui.scripts;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tjohnn.teleprompter.R;
import com.tjohnn.teleprompter.daggerjetifier.DaggerAppCompatActivityX;

public class ScriptsActivity extends DaggerAppCompatActivityX {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scripts);
    }



}
