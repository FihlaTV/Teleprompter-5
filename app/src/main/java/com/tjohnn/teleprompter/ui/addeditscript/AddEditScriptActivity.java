package com.tjohnn.teleprompter.ui.addeditscript;

import android.os.Bundle;
import android.view.MenuItem;

import com.tjohnn.teleprompter.R;
import com.tjohnn.teleprompter.daggerjetifier.DaggerAppCompatActivityX;

import androidx.core.app.NavUtils;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class AddEditScriptActivity extends DaggerAppCompatActivityX {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_script);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupNavHost();

    }

    /**
     * Sets up the navhost fragment to allow sending args to the startDestination fragment
     */
    private void setupNavHost() {

        long scriptId = getIntent().getLongExtra(AddEditScriptFragment.SCRIPT_ID_KEY, 0);

        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.activity_add_edit_script_nav_host_fragment);
        navController = hostFragment.getNavController();

        NavInflater inflater = navController.getNavInflater();
        NavGraph graph = inflater.inflate(R.navigation.add_edit_script_nav_graph);
        NavArgument scriptIdArgument = new NavArgument.Builder().setDefaultValue(scriptId).build();
        graph.addArgument(AddEditScriptFragment.SCRIPT_ID_KEY, scriptIdArgument);


        hostFragment.getNavController().setGraph(graph);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
