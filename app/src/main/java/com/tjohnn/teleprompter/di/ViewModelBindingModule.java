package com.tjohnn.teleprompter.di;

import com.tjohnn.teleprompter.di.annotations.ViewModelKey;
import com.tjohnn.teleprompter.ui.scripts.ScriptsViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface ViewModelBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(ScriptsViewModel.class)
    ViewModel scriptsViewModel(ScriptsViewModel questionnairesViewModel);


    @Binds
    ViewModelProvider.Factory factory(ViewModelFactory viewModelFactory);

}
