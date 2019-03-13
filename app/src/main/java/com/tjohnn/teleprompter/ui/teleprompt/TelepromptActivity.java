package com.tjohnn.teleprompter.ui.teleprompt;

import android.os.Bundle;
import android.view.MenuItem;

import com.tjohnn.teleprompter.R;
import com.tjohnn.teleprompter.daggerjetifier.DaggerAppCompatActivityX;
import com.tjohnn.teleprompter.ui.addeditscript.AddEditScriptFragment;

import androidx.core.app.NavUtils;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;

public class TelepromptActivity extends DaggerAppCompatActivityX {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleprompt);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            inflateNavHost();
        }
    }

    /**
     * Sets up the navhost fragment to allow sending args to the startDestination fragment
     */
    private void inflateNavHost() {
        // get script id
        long scriptId = getIntent().getLongExtra(AddEditScriptFragment.SCRIPT_ID_KEY, 0);

        // get the nav host fragment, inflate its navgraph and args to the fragment
        NavHostFragment hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.activity_teleprompt_nav_host_fragment);
        NavController navController = hostFragment.getNavController();

        NavInflater inflater = navController.getNavInflater();
        NavGraph graph = inflater.inflate(R.navigation.teleprompt_nav_graph);
        NavArgument scriptIdArgument = new NavArgument.Builder().setDefaultValue(scriptId).build();
        graph.addArgument(AddEditScriptFragment.SCRIPT_ID_KEY, scriptIdArgument);

        hostFragment.getNavController().setGraph(graph);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
