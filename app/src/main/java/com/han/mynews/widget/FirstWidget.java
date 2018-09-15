package com.han.mynews.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import com.han.mynews.R;

public class FirstWidget extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("_____________________________________","onUpdate");
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            Log.d("_________________________________appWidgetId____",""+appWidgetId);
            Intent intent = new Intent(context, ListViewWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_main);
            views.setRemoteAdapter(R.id.words, intent);


            Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com"));
            //  intent2.setAction("jh.project.widget.digital.third.action.CLICK");
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, intent2, 0);

            views.setOnClickPendingIntent(R.id.row_layout, pendingIntent2);


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