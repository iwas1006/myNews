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

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 newsbook이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE newsbook (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, url TEXT, imgUrl TEXT, create_at TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String creDt, String title, String content, String url, String imgUrl) {

        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        Log.d("____________1",title);
        Log.d("____________2",content);
        Log.d("____________3",url);
        Log.d("____________4",imgUrl);
        Log.d("____________5",creDt);

        db.execSQL("INSERT INTO newsbook VALUES(null, '" + title + "', '" + content + "', '" + url + "','" + imgUrl + "', '" + creDt + "');");
        db.close();
    }

    public void update(String imgUrl, int id) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("UPDATE newsbook SET imgUrl =" + imgUrl+ " WHERE _id = '" + id + "';");
        db.close();
    }

    public void delete(String id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM newsbook WHERE _id='" + id + "';");
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM newsbook;");
        db.close();
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM newsbook", null);
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

        return result;
    }

    public List<Book> getItems() {
        List<Book> result = new ArrayList<Book>();

        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM newsbook", null);
        while (cursor.moveToNext()) {
            result.add( new Book(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
        }

        return result;
    }
}