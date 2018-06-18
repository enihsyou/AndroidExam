package com.enihsyou.androidexamination.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchDatabaseHelper extends SQLiteOpenHelper {

    private final static String TAG = MovieSearchDatabaseHelper.class.getSimpleName();

    private Context context;

    public MovieSearchDatabaseHelper(Context context) {
        super(context, "exam.db", null, 1);
        this.context = context;
    }

    private static final String TABLE_NAME = "movie_search";

    private static final String SQL_DROP_MOVIE_SEARCH = "DROP TABLE IF EXISTS " + TABLE_NAME + " ;";

    private static final String SQL_CREATE_MOVIE_SEARCH = "CREATE TABLE " + TABLE_NAME + "(\n" +
                                                          "  id INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                                                          "  keyword TEXT NOT NULL UNIQUE\n" +
                                                          ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVIE_SEARCH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 暂时没用  测试语句
        if (newVersion > 1) {
            db.execSQL(SQL_DROP_MOVIE_SEARCH);
            db.execSQL(SQL_CREATE_MOVIE_SEARCH);
        }
    }

    /**向数据库插入一条记录*/
    public void insertRecord(String keyword) {
        try (final SQLiteDatabase database = getWritableDatabase()) {
            final ContentValues values = new ContentValues();
            values.put("keyword", keyword);
            database.insert(TABLE_NAME, null, values);
        } catch (SQLiteConstraintException ignore) {

            // UNIQUE限制的错误 被忽略
        }
    }

    /** 获取所有的搜索记录*/
    public List<String> getRecords() {
        try (final SQLiteDatabase database = getReadableDatabase()) {

            try (Cursor cursor = database.query(TABLE_NAME, new String[]{"keyword"}, null, null, null, null, null)) {
                List<String> keywords = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    keywords.add(cursor.getString(0));
                }
                return keywords;
            }
        }
    }
}
