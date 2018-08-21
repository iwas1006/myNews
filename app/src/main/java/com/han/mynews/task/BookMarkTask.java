package com.han.mynews.task;

import android.content.Context;
import android.os.AsyncTask;

import com.facebook.drawee.view.SimpleDraweeView;
import com.han.mynews.db.DBHelper;
import com.han.mynews.dto.Book;
import com.han.mynews.dto.OGTag;
import com.han.mynews.util.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

        final DBHelper dbHelper = new DBHelper(c, "newsbook.db", null, 1);
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        // 출력될 포맷 설정
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String title = book.getTitle().replaceAll("'","''");
        String content = book.getContent().replaceAll("'","''");

        // DB에 데이터 추가
        dbHelper.insert(simpleDateFormat.format(date), title, content, book.getUrl(), book.getImageUrl());
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
            this.e = e;
            return null;
        }
    }
}