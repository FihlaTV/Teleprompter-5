package com.tjohnn.teleprompter.di.activitymodule;

import com.tjohnn.teleprompter.di.annotations.ActivityScoped;
import com.tjohnn.teleprompter.di.annotations.FragmentScoped;
import com.tjohnn.teleprompter.ui.scripts.ScriptsActivity;
import com.tjohnn.teleprompter.ui.scripts.ScriptsFragment;
import com.tjohnn.teleprompter.ui.teleprompt.TelepromptActivity;
import com.tjohnn.teleprompter.ui.teleprompt.TelepromptFragment;
import com.tjohnn.teleprompter.ui.teleprompt.TelepromptViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
interface TelepromptActivityModule {


    @FragmentScoped
    @ContributesAndroidInjector
    TelepromptFragment telepromptFragment();

    @ActivityScoped
    @Binds
    AppCompatActivity activity(TelepromptActivity telepromptActivity);


    @Module
    abstract class TelepromptFragmentModule{
        @Provides
        static TelepromptViewModel telepromptViewModel(ViewModelProvider.Factory factory, TelepromptFragment telepromptFragment){
            return ViewModelProviders.of(telepromptFragment, factory).get(TelepromptViewModel.class);
        }
    }

}
