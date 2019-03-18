package com.tjohnn.teleprompter;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.tjohnn.teleprompter.data.Script;
import com.tjohnn.teleprompter.data.ScriptDao;

import java.util.List;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;

public class AppWidgetIntentService extends DaggerIntentService {

    public static final String ACTION_UPDATE_SCRIPT_WIDGETS = "com.tjohnn.teleprompter.ACTION_UPDATE_SCRIPT_WIDGETS";

    @Inject
    ScriptDao scriptDao;

    public AppWidgetIntentService() {
        super("NextScriptService");
    }

    /**
     * Starts this service to perform startActionUpdateScriptWidgets action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateScriptWidgets(Context context) {
        Intent intent = new Intent(context, AppWidgetIntentService.class);
        intent.setAction(ACTION_UPDATE_SCRIPT_WIDGETS);
        context.startService(intent);
    }

    /**
     * @param intent Service invocation intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_SCRIPT_WIDGETS.equals(action)) {
                handleActionUpdateScriptWidgets();
            }
        }
    }


    /**
     * Handle action UpdateScriptWidgets in the provided background thread
     */
    private void handleActionUpdateScriptWidgets() {

        List<Script> scripts = scriptDao.getNextScript();
        Script script = null;
        if(scripts.size() > 0){
            script = scripts.get(0);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ScriptAppWidgetProvider.class));


        ScriptAppWidgetProvider.updateScriptWidgets(this, appWidgetManager, appWidgetIds, script);
    }
}