package com.tjohnn.teleprompter.di.activitymodule;

import com.tjohnn.teleprompter.di.annotations.ActivityScoped;
import com.tjohnn.teleprompter.di.annotations.FragmentScoped;
import com.tjohnn.teleprompter.ui.scripts.ScriptAdapter;
import com.tjohnn.teleprompter.ui.scripts.ScriptsActivity;
import com.tjohnn.teleprompter.ui.scripts.ScriptsFragment;
import com.tjohnn.teleprompter.ui.scripts.ScriptsViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
interface ScriptsActivityModule {


    @FragmentScoped
    @ContributesAndroidInjector(modules = ScriptsFragmentModule.class)
    ScriptsFragment scriptsFragment();

    @ActivityScoped
    @Binds
    AppCompatActivity activity(ScriptsActivity scriptsActivity);

    @Module
    abstract class ScriptsFragmentModule{
        @Provides
        static ScriptsViewModel scriptsViewModel(ViewModelProvider.Factory factory, ScriptsFragment scriptsFragment){
            return ViewModelProviders.of(scriptsFragment, factory).get(ScriptsViewModel.class);
        }

        @FragmentScoped
        @Provides
        static ScriptAdapter scriptsAdapter(ScriptsFragment scriptsFragment){
            return new ScriptAdapter(scriptsFragment);
        }
    }

}
