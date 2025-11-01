package com.example.datastore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.datastore.entity.LoginInfo;
import com.example.datastore.entity.User;

import java.util.ArrayList;
import java.util.List;

public class LoginDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "login.db";
    private static final String TABLE_NAME = "login_info";
    private static final int DB_VERSION = 1;
    private static LoginDBHelper instance = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    public LoginDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static LoginDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new LoginDBHelper(context);
        }
        return instance;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = instance.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = instance.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }
        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    // 创建数据库,执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " phone VARCHAR NOT NULL," +
                " password INTEGER NOT NULL," +
                " remember INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "alter table "+TABLE_NAME+" add column phone varchar;";
        db.execSQL(sql);
        sql = "alter table "+TABLE_NAME+" add column password varchar;";
        db.execSQL(sql);
    }

    public long save(LoginInfo info){
        // 如果存在则先删除,再添加
        try {
            mWDB.beginTransaction();
            delete(info);
            long row = insert(info);
            mWDB.setTransactionSuccessful();
            return row;
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }finally {
            mWDB.endTransaction();
        }
    }

    private long insert(LoginInfo info) {
        ContentValues values = new ContentValues();
        values.put("phone", info.phone);
        values.put("password", info.password);
        values.put("remember", info.remember ? 1 : 0);
        return mWDB.insert(TABLE_NAME, null, values);
    }

    private long delete(LoginInfo info) {
        return mWDB.delete(TABLE_NAME, "phone=?", new String[]{info.phone});
    }

    public LoginInfo queryTop(){
        LoginInfo info = null;
        String sql = "select * from "+ TABLE_NAME +" where remember=1 order by _id desc limit 1";
        Cursor cursor = mRDB.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            info = new LoginInfo();
            info.phone = cursor.getString(cursor.getColumnIndex("phone"));
            info.password = cursor.getString(cursor.getColumnIndex("password"));
            info.remember = cursor.getInt(cursor.getColumnIndex("remember")) == 1;
        }
        return info;
    }

    public LoginInfo queryByPhone(String phone) {
        LoginInfo info = null;
        String sql = "select * from "+ TABLE_NAME +" where phone=? order by _id desc limit 1";
        Cursor cursor = mRDB.rawQuery(sql, new String[]{phone});
        if (cursor.moveToNext()) {
            info = new LoginInfo();
            info.phone = cursor.getString(cursor.getColumnIndex("phone"));
            info.password = cursor.getString(cursor.getColumnIndex("password"));
            info.remember = cursor.getInt(cursor.getColumnIndex("remember")) == 1;
        }
        return info;
    }
}
