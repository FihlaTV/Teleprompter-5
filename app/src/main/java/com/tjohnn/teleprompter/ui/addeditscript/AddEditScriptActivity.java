package com.tjohnn.teleprompter.ui.addeditscript;

import android.os.Bundle;

import com.tjohnn.teleprompter.R;
import com.tjohnn.teleprompter.daggerjetifier.DaggerAppCompatActivityX;

public class AddEditScriptActivity extends DaggerAppCompatActivityX {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_script);
    }
}
