package com.han.mynews.task;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.facebook.drawee.view.SimpleDraweeView;
import com.han.mynews.R;
import com.han.mynews.dao.NewsDaoImpl;
import com.han.mynews.dto.Book;
import com.han.mynews.dto.OGTag;
import com.han.mynews.util.Util;
import com.han.mynews.widget.FirstWidget;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookMarkTask extends AsyncTask<String, Void, Book> {

    private Exception e;
    private Context c;
    private SimpleDraweeView imageView;

    public BookMarkTask(Context c) {
        this.c = c;
    }

    @Override
    protected void onPostExecute(Book book) {
        if(book == null) return;

        final NewsDaoImpl news = new NewsDaoImpl(c);
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        // 출력될 포맷 설정
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String title = book.getTitle().replaceAll("'","''");
        String content = book.getContent().replaceAll("'","''");

        // DB에 데이터 추가
        news.insert(simpleDateFormat.format(date), title, content, book.getUrl(), book.getImageUrl());

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(c);
        ComponentName componentName = new ComponentName(c, FirstWidget.class);
        RemoteViews views = new RemoteViews(c.getPackageName(), R.layout.widget_main);
        appWidgetManager.updateAppWidget(componentName, views);
    }

    @Override
    protected Book doInBackground(String... strings) {
        Util u = new Util();
        try {
            OGTag og = u.getOGTag(strings[0]);

            Book b = new Book();
            if(og != null && og.getTitle() != null && og.getTitle().length() > 0) {
                b.setTitle(og.getTitle());
            } else {
                b.setTitle(strings[0]);
            }

            if(og != null && og.getDescription() != null && og.getDescription().length() > 0) {
                b.setContent(og.getDescription());
            } else {
                b.setContent(strings[0]);
            }

            if(og != null && og.getImage() != null && og.getImage().length() > 0) {
                b.setImageUrl(og.getImage());
            }

            b.setUrl(strings[0]);

            return b;
        } catch (Exception e) {

/*
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
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

                appWidgetManager.updateAppWidget(appWidgetId, views);
            }

            appWidgetManager.u*/
            this.e = e;
            return null;
        }
    }
}