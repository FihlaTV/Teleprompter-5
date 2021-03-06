package com.tjohnn.teleprompter.ui.scripts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tjohnn.teleprompter.AppWidgetIntentService;
import com.tjohnn.teleprompter.R;
import com.tjohnn.teleprompter.daggerjetifier.DaggerFragmentX;
import com.tjohnn.teleprompter.data.Script;
import com.tjohnn.teleprompter.ui.addeditscript.AddEditScriptFragment;
import com.tjohnn.teleprompter.ui.teleprompt.TelepromptFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScriptsFragment extends DaggerFragmentX implements ScriptAdapter.OnScriptItemListener {

    @Inject
    AppCompatActivity mActivity;

    @Inject
    ScriptsViewModel mViewModel;

    @Inject
    ScriptAdapter scriptAdapter;

    @BindView(R.id.rv_scripts)
    RecyclerView scriptList;
    @BindView(R.id.empty_list_wrapper)
    View emptyListMessage;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mActivity);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scripts, container, false);
        ButterKnife.bind(this, view);
        setupAdapterViews();
        subscribeToViewModel();
        return view;
    }

    private void subscribeToViewModel() {
        mViewModel.getScripts().observe(this, scriptList -> {
            if(scriptList == null || scriptList.isEmpty()){
                emptyListMessage.setVisibility(View.VISIBLE);
                return;
            }
            emptyListMessage.setVisibility(View.GONE);
            scriptAdapter.updateItems(scriptList);
        });

        mViewModel.getOnScriptDeleted().observe(this, r -> {
            if(r != null && r.getContentIfNotUsed() != null && r.peekContent()){
                // update widget
                AppWidgetIntentService.startActionUpdateScriptWidgets(mActivity.getApplicationContext());
            }
        });
    }

    private void setupAdapterViews() {
        scriptList.setLayoutManager(new LinearLayoutManager(mActivity));
        scriptList.setAdapter(scriptAdapter);
    }


    @OnClick(R.id.fab_add_script)
    void addScript(){
        Navigation.findNavController(mActivity, R.id.activity_scripts_nav_host_fragment)
                .navigate(R.id.action_scriptsFragment_to_addEditScriptActivity);
    }


    @Override
    public void onItemEditClicked(Script script) {
        Bundle bundle = new Bundle();
        bundle.putLong(AddEditScriptFragment.SCRIPT_ID_KEY, script.getId());
        Navigation.findNavController(mActivity, R.id.activity_scripts_nav_host_fragment)
                .navigate(R.id.action_scriptsFragment_to_addEditScriptActivity, bundle);
    }

    @Override
    public void onItemDeleteClicked(Script script) {
        new AlertDialog.Builder(mActivity)
                .setMessage(getString(R.string.are_you_sure_to_delete_script, script.getTitle()))
                .setPositiveButton(android.R.string.yes, (dialog1, which) -> {
                    mViewModel.deleteScript(script);
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public void onItemClicked(Script script) {
        Bundle bundle = new Bundle();
        bundle.putLong(TelepromptFragment.SCRIPT_ID_KEY, script.getId());
        Navigation.findNavController(mActivity, R.id.activity_scripts_nav_host_fragment)
                .navigate(R.id.action_scriptsFragment_to_telepromptActivity, bundle);

        logForAnalytics(script)
    }

    private void logForAnalytics(Script script) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "" + script.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, script.getTitle());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "script");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

    }
}
