package com.han.mynews.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.RemoteViews;

import com.han.mynews.R;
import com.han.mynews.dao.NewsDaoImpl;
import com.han.mynews.dto.Book;

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

        row.setTextViewText(R.id.row_title, book.getTitle());
        row.setTextViewText(R.id.row_content, book.getContent());
        //if(book.getImageUrl() != null && book.getImageUrl().length() > 0) {
            row.setImageViewUri(R.id.row_image, Uri.parse(book.getImageUrl()));
        //}


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