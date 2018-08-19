package com.han.mynews.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.han.mynews.dto.NewsItem;

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
        db.execSQL("CREATE TABLE newsbook (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, url TEXT, create_at TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String creDt, String title, String content, String url) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO newsbook VALUES(null, '" + title + "', '" + content + "', '" + url + "', '" + creDt + "');");
        db.close();
    }

  /*  public void update(String item, int price) {
        SQLiteDatabase fragment_db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        fragment_db.execSQL("UPDATE newsbook SET price=" + price + " WHERE item='" + item + "';");
        fragment_db.close();
    }*/

    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM newsbook WHERE item='" + item + "';");
        db.close();
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM newsbook", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + ", title : "
                    + cursor.getString(1)
                    + ", content : "
                    + cursor.getInt(2)
                    + ", url : "
                    + cursor.getString(3)
                    + ", icon : "
                    + cursor.getString(4)
                    + "\n";
        }

        return result;
    }

    public List<NewsItem> getItems() {
        List<NewsItem> result = new ArrayList<NewsItem>();

        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        // adapter.addItem(new NewsItem(0, "네이버", "네이버신문", "https://m.news.naver.com", 0));

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM newsbook", null);
        while (cursor.moveToNext()) {
            result.add( new NewsItem(0, cursor.getString(1), cursor.getString(2), cursor.getString(3), 0));
        }

        return result;
    }
}