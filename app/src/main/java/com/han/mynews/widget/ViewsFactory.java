package com.han.mynews.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.han.mynews.R;
import com.han.mynews.dao.NewsDaoImpl;
import com.han.mynews.dto.Book;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ViewsFactory implements ListViewWidgetService.RemoteViewsFactory {

    private Context ctxt=null;
    private int appWidgetId;
    private List<Book> list;

    public ViewsFactory(Context ctxt, Intent intent) {
        Log.d("________________________________ViewsFactory___","ViewsFactory");
        this.ctxt = ctxt;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        final NewsDaoImpl news = new NewsDaoImpl(ctxt);
        list = news.getItems();

        Log.d("________________________________ViewsFactory__list_",list.size()+"");
    }

    @Override
    public void onCreate() {
        // no-op
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        return(list.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d("________________________________ViewsFactory__getViewAt_",position+"");
        Book book = list.get(position);

        RemoteViews row = new RemoteViews(ctxt.getPackageName(), R.layout.widget_row_book_item_view);
        row.setImageViewUri(R.id.row_image, Uri.parse(""));
        row.setTextViewText(R.id.row_title, book.getTitle());
        row.setTextViewText(R.id.row_content, book.getContent());

       // Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com"));
        //intent2.setAction(ACTION_CLICK+"111");
        //  intent2.setAction("jh.project.widget.digital.third.action.CLICK");
        //PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, intent2, 0);
       // PendingIntent pendingIntent = PendingIntent.getBroadcast(ctxt, 0, intent2, 0);


       // row.setOnClickPendingIntent(R.id.row_image, pendingIntent);

        if(book.getImageUrl() != null && book.getImageUrl().trim().length() > 0) {
            if (book.getImageUrl().startsWith("//")) {
                book.setImageUrl("http:" + book.getImageUrl());
            }

            if (book.getImageBitmap() == null) {
                HttpURLConnection conn = null;

                try {

                    URL u = new URL(book.getImageUrl());

                    conn = (HttpURLConnection) u.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap b = BitmapFactory.decodeStream(is);
                    book.setImageBitmap(b);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    conn.disconnect();
                }
            }

            row.setImageViewBitmap(R.id.row_image, book.getImageBitmap());
        }

        Log.d("________________________________ViewsFactory__getViewAt_book",book.toString());
        return(row);
    }



    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {
        // no-op
    }


}