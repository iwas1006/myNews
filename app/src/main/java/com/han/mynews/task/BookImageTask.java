package com.han.mynews.task;

import android.content.Context;
import android.os.AsyncTask;

import com.facebook.drawee.view.SimpleDraweeView;
import com.han.mynews.dto.Book;
import com.han.mynews.dto.OGTag;
import com.han.mynews.util.Util;


import java.util.List;

public class BookImageTask extends AsyncTask<Book, Void, String> {

    private Exception e;
    private Context c;
    private SimpleDraweeView imageView;

    public BookImageTask(Context c, SimpleDraweeView imageView) {
        this.c = c;
        this.imageView = imageView;
    }

    @Override
    protected String doInBackground(Book... books) {
        Util u = new Util();
        try {
            OGTag og = u.getOGTag(books[0].getUrl());
            if(og != null && og.getImage() != null && og.getImage().length() > 0) {
                return og.getImage();
            }
        } catch (Exception e) {
            this.e = e;
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String string) {
        this.imageView.setImageURI(string);
    }
}