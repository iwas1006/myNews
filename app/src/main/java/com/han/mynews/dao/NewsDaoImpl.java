package com.han.mynews.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.han.mynews.db.DBHelper;
import com.han.mynews.dto.Book;

import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl {
    DBHelper dBHelper;

    public NewsDaoImpl(Context c) {
        dBHelper = new DBHelper(c);
    }

    public void insert(String creDt, String title, String content, String url, String imgUrl) {
        SQLiteDatabase db = dBHelper.getDB();
        Log.d("____________1",title);
        Log.d("____________2",content);
        Log.d("____________3",url);
        Log.d("____________4",imgUrl);
        Log.d("____________5",creDt);

        db.execSQL("INSERT INTO "+dBHelper.TBL_NAME_OF_NEWSBOOK+" VALUES(null, '" + title + "', '" + content + "', '" + url + "','" + imgUrl + "', '" + creDt + "');");
        db.close();
    }

    public void update(String imgUrl, int id) {
        SQLiteDatabase db = dBHelper.getDB();
        db.execSQL("UPDATE "+dBHelper.TBL_NAME_OF_NEWSBOOK+" SET imgUrl =" + imgUrl+ " WHERE _id = '" + id + "';");
        db.close();
    }

    public void delete(String id) {
        SQLiteDatabase db = dBHelper.getDB();
        db.execSQL("DELETE FROM "+dBHelper.TBL_NAME_OF_NEWSBOOK+" WHERE _id='" + id + "';");
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = dBHelper.getDB();
        db.execSQL("DELETE FROM "+dBHelper.TBL_NAME_OF_NEWSBOOK+";");
        db.close();
    }

    public String getResult() {
        SQLiteDatabase db = dBHelper.getDB();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM "+dBHelper.TBL_NAME_OF_NEWSBOOK+"", null);
        while (cursor.moveToNext()) {
            result += "id : "
                    + cursor.getString(0)
                    + ", title : "
                    + cursor.getString(1)
                    + ", content : "
                    + cursor.getInt(2)
                    + ", url : "
                    + cursor.getString(3)
                    + ", imgUrl : "
                    + cursor.getString(4)
                    + "creDt : "
                    + cursor.getString(4)
                    + "\n";
        }
        cursor.close();
        db.close();
        return result;
    }

    public List<Book> getItems() {
        SQLiteDatabase db = dBHelper.getDB();
        List<Book> result = new ArrayList<Book>();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM "+dBHelper.TBL_NAME_OF_NEWSBOOK+"", null);
        while (cursor.moveToNext()) {
            result.add( new Book(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
        }
        cursor.close();
        db.close();
        return result;
    }
}