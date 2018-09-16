package com.han.mynews.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.han.mynews.R;

public class FirstWidget extends AppWidgetProvider {

   // static final String ACTION_CLICK = "com.han.mynews.widget.FirstWidget.CLICK";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("______aa________","");

        //Bundle b = intent.getExtras();

        Log.d("______bbbaa________",intent.getAction());

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("_____________________________________","onUpdate");
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            Log.d("_________________________________appWidgetId____",""+appWidgetId);
            Intent intent = new Intent(context, ListViewWidgetService.class);
           // intent.setAction(ACTION_CLICK);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));


            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_main);
            views.setRemoteAdapter(R.id.words, intent);

         //   views.setOnClickPendingIntent(R.id.button, pending);





            //views.setRemoteAdapter(R.id.recyclerWidget, intent);
            //Intent startActivityIntent = new Intent(context, FirstWidget.class);
            //PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //views.setPendingIntentTemplate(R.id.recyclerWidget, startActivityPendingIntent);

            //views.setEmptyView(R.id.recyclerWidget, R.id.recyclerWidget);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}