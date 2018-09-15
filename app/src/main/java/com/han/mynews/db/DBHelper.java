package com.han.mynews.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.han.mynews.dto.Book;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TBL_NAME_OF_NEWSBOOK    = "newsbook";
    public static final String TBL_NAME_OF_ICON        = "icon";

    private static final String DB_NAME = "newsbook.db";
    private static final int DB_VERSION = 2;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TBL_NAME_OF_NEWSBOOK+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, url TEXT, imgUrl TEXT, create_at TEXT);");
        db.execSQL("CREATE TABLE "+TBL_NAME_OF_ICON+" (url TEXT PRIMARY KEY, imgUrl TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public SQLiteDatabase getDB() {
        return getWritableDatabase();
    }
}