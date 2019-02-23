package com.tjohnn.teleprompter.ui.scripts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tjohnn.teleprompter.R;
import com.tjohnn.teleprompter.daggerjetifier.DaggerFragmentX;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScriptsFragment extends DaggerFragmentX {

    @Inject
    AppCompatActivity mActivity;

    @Inject
    ScriptsViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scripts, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.fab_add_script)
    void addScript(){
        Navigation.findNavController(mActivity, R.id.activity_scripts_nav_host_fragment)
                .navigate(R.id.action_scriptsFragment_to_addEditScriptActivity);
    }
}
