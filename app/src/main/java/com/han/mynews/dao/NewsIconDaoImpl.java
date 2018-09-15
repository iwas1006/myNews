package com.han.mynews.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.han.mynews.db.DBHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NewsIconDaoImpl {
    private DBHelper dBHelper;

    public NewsIconDaoImpl(Context c) {
        dBHelper = new DBHelper(c);
    }

    public void insert(String url, String imgUrl) {
        try {
            url = URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d("_______insert_____",url+"__"+imgUrl);
        SQLiteDatabase db = dBHelper.getDB();
        db.execSQL("INSERT INTO "+DBHelper.TBL_NAME_OF_ICON+" VALUES('" + url + "','" + imgUrl + "');");
        db.close();
    }

   /* public void update(String imgUrl, int id) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("UPDATE icon SET imgUrl =" + imgUrl+ " WHERE _id = '" + id + "';");
        db.close();
    }*/

    public void delete(String url) {
        SQLiteDatabase db = dBHelper.getDB();
        db.execSQL("DELETE FROM "+DBHelper.TBL_NAME_OF_ICON+" WHERE url='" + url + "';");
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = dBHelper.getDB();
        db.execSQL("DELETE FROM "+DBHelper.TBL_NAME_OF_ICON+";");
        db.close();
    }

    public String getResultRaw() {
        SQLiteDatabase db = dBHelper.getDB();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM "+DBHelper.TBL_NAME_OF_ICON+"", null);
        while (cursor.moveToNext()) {
            result += ", url : "
                    + cursor.getString(0)
                    + ", imgUrl : "
                    + cursor.getString(1)
                    + "\n";
        }
        cursor.close();
        db.close();
        return result;
    }

    public String getImageUrl(String url) {
        SQLiteDatabase db = dBHelper.getDB();
        try {
            url = URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        String sql = "select * from "+DBHelper.TBL_NAME_OF_ICON+" where url='"+url+"'";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            result = cursor.getString(1);
        }

        cursor.close();
        db.close();
        return result;
    }
}