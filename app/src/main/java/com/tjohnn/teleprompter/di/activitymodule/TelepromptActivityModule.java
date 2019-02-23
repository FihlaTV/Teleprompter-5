package com.tjohnn.teleprompter.di.activitymodule;

import com.tjohnn.teleprompter.di.annotations.ActivityScoped;
import com.tjohnn.teleprompter.di.annotations.FragmentScoped;
import com.tjohnn.teleprompter.ui.scripts.ScriptsActivity;
import com.tjohnn.teleprompter.ui.scripts.ScriptsFragment;
import com.tjohnn.teleprompter.ui.teleprompt.TelepromptActivity;
import com.tjohnn.teleprompter.ui.teleprompt.TelepromptFragment;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface TelepromptActivityModule {


    @FragmentScoped
    @ContributesAndroidInjector
    TelepromptFragment telepromptFragment();

    @ActivityScoped
    @Binds
    AppCompatActivity activity(TelepromptActivity telepromptActivity);

}
