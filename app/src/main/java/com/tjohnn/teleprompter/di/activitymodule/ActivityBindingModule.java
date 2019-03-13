package com.tjohnn.teleprompter.di.activitymodule;


import com.tjohnn.teleprompter.di.annotations.ActivityScoped;
import com.tjohnn.teleprompter.di.annotations.FragmentScoped;
import com.tjohnn.teleprompter.ui.addeditscript.AddEditScriptActivity;
import com.tjohnn.teleprompter.ui.scripts.ScriptsActivity;
import com.tjohnn.teleprompter.ui.settings.SettingsActivity;
import com.tjohnn.teleprompter.ui.settings.SettingsFragment;
import com.tjohnn.teleprompter.ui.teleprompt.TelepromptActivity;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {TelepromptActivityModule.class})
    TelepromptActivity telepromptActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {ScriptsActivityModule.class})
    ScriptsActivity scriptsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {AddEditScriptActivityModule.class})
    AddEditScriptActivity createQuestionnaireActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {SettingsActivityModule.class})
    SettingsActivity settingsActivity();


}
