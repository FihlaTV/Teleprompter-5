package com.tjohnn.teleprompter.di.activitymodule;

import com.tjohnn.teleprompter.di.annotations.ActivityScoped;
import com.tjohnn.teleprompter.di.annotations.FragmentScoped;
import com.tjohnn.teleprompter.ui.addeditscript.AddEditScriptActivity;
import com.tjohnn.teleprompter.ui.addeditscript.AddEditScriptFragment;
import com.tjohnn.teleprompter.ui.addeditscript.AddEditScriptViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
interface AddEditScriptActivityModule {


    @FragmentScoped
    @ContributesAndroidInjector
    AddEditScriptFragment addEditScriptFragment();

    @ActivityScoped
    @Binds
    AppCompatActivity activity(AddEditScriptActivity addEditScriptActivity);

    @Module
    abstract class AddEditScriptFragmentModule{
        @Provides
        static AddEditScriptViewModel addEditScriptViewModel(ViewModelProvider.Factory factory, AddEditScriptFragment addEditScriptFragment){
            return ViewModelProviders.of(addEditScriptFragment, factory).get(AddEditScriptViewModel.class);
        }
    }

}
