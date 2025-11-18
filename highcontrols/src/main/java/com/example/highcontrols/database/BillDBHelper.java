package com.example.highcontrols.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.highcontrols.bean.BillInfo;

import java.util.ArrayList;
import java.util.List;

public class BillDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bill.db";

    // 账单信息表
    private static final String TABLE_BILLS_INFO = "bill_info";

    private static final int DB_VERSION = 1;
    private static BillDBHelper instance = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    public BillDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static BillDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new BillDBHelper(context);
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
        // 创建账单信息表
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_BILLS_INFO+" (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " date VARCHAR NOT NULL," +
                " type INTEGER NOT NULL," +
                " amount DOUBLE NOT NULL," +
                " remark VARCHAR NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 保存一条订单记录
    public long saveBillInfo(BillInfo billInfo) {
        return mWDB.insert(TABLE_BILLS_INFO, null, billInfo.toContentValues());
    }

    public List<BillInfo> getBillInfos(String begin, String end) {
        List<BillInfo> billInfos = new ArrayList<>();
        Cursor cursor = mRDB.rawQuery("SELECT * FROM " + TABLE_BILLS_INFO + " WHERE strftime('%Y-%m', date) BETWEEN ? AND ?", new String[]{begin, end});
        while (cursor.moveToNext()) {
            BillInfo billInfo = new BillInfo();
            billInfo.id = cursor.getInt(cursor.getColumnIndex("_id"));
            billInfo.date = cursor.getString(cursor.getColumnIndex("date"));
            billInfo.type = cursor.getInt(cursor.getColumnIndex("type"));
            billInfo.amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            billInfo.remark = cursor.getString(cursor.getColumnIndex("remark"));
            billInfos.add(billInfo);
        }
        return billInfos;
    }
}
