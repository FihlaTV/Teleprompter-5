package com.tjohnn.teleprompter.di.activitymodule;

import com.tjohnn.teleprompter.di.annotations.ActivityScoped;
import com.tjohnn.teleprompter.di.annotations.FragmentScoped;
import com.tjohnn.teleprompter.ui.addeditscript.AddEditScriptActivity;
import com.tjohnn.teleprompter.ui.addeditscript.AddEditScriptFragment;
import com.tjohnn.teleprompter.ui.scripts.ScriptsActivity;
import com.tjohnn.teleprompter.ui.scripts.ScriptsFragment;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface AddEditScriptActivityModule {


    @FragmentScoped
    @ContributesAndroidInjector
    AddEditScriptFragment addEditScriptFragment();

    @ActivityScoped
    @Binds
    AppCompatActivity activity(AddEditScriptActivity addEditScriptActivity);

}
