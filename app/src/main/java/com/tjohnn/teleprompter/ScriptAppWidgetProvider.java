package com.tjohnn.teleprompter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.tjohnn.teleprompter.data.Script;
import com.tjohnn.teleprompter.ui.scripts.ScriptsActivity;
import com.tjohnn.teleprompter.utils.DateUtils;

public class ScriptAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        // request update
        AppWidgetIntentService.startActionUpdateScriptWidgets(context);
    }


    public static void updateScriptWidgets(Context context,
                                           AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds,
                                           Script script) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, script);
        }
    }

    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appWidgetId,
                                Script nextScript) {

        // Create the Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, ScriptsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_next_script);


        views.setOnClickPendingIntent(R.id.widget_wrapper, pendingIntent);

        String title = nextScript != null ?
                nextScript.getTitle() : context.getString(R.string.no_unread_script_found);

        views.setTextViewText(R.id.tv_script_title, title);

        if(nextScript != null)
            views.setTextViewText(R.id.tv_read_date,
                    DateUtils.dateToDateTimeString(nextScript.getTelepromptingDate()));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


}
