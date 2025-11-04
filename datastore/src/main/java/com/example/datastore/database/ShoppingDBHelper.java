package com.example.datastore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.datastore.entity.CartInfo;
import com.example.datastore.entity.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "shopping.db";

    // 商品信息表
    private static final String TABLE_GOODS_INFO = "goods_info";
    // 购物车信息表
    private static final String TABLE_CART_INFO = "cart_info";

    private static final int DB_VERSION = 1;
    private static ShoppingDBHelper instance = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    public ShoppingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static ShoppingDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ShoppingDBHelper(context);
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
        // 创建商品信息表
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_GOODS_INFO+" (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " description VARCHAR NOT NULL," +
                " price DOUBLE NOT NULL," +
                " pic_path VARCHAR NOT NULL);";
        db.execSQL(sql);
        // 创建购物车信息表
        sql = "CREATE TABLE IF NOT EXISTS "+TABLE_CART_INFO+" (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " goods_id INTEGER NOT NULL," +
                " count INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 添加多条商品信息
    public void insertGoodsInfos(List<GoodsInfo> list){
        // 插入多条记录,要么全部成功,要么全部失败
        try {
            mWDB.beginTransaction();
            for (GoodsInfo info : list) {
                ContentValues values = new ContentValues();
                values.put("name", info.getName());
                values.put("description", info.getDescription());
                values.put("price", info.getPrice());
                values.put("pic_path", info.getPicPath());
                mWDB.insert(TABLE_GOODS_INFO, null, values);
            }
            mWDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWDB.endTransaction();
        }
    }

    public List<GoodsInfo> queryGoodsInfos(){
        List<GoodsInfo> list = new ArrayList<>();
        // 执行记录查询动作,该语句返回结果集的游标
        Cursor cursor = mRDB.query(TABLE_GOODS_INFO, null, null, null, null, null, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            GoodsInfo info = new GoodsInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            info.setName(cursor.getString(cursor.getColumnIndex("name")));
            info.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            info.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
            info.setPicPath(cursor.getString(cursor.getColumnIndex("pic_path")));
            list.add(info);
        }
        return list;
    }

    public void addCarInfo(GoodsInfo info) {
        // 判断是否已经添加过
        Cursor cursor = mRDB.rawQuery("SELECT * FROM " + TABLE_CART_INFO + " WHERE goods_id = ?", new String[]{info.getId() + ""});
        if (cursor.moveToNext()) {
            // 已经添加过,则更新数量
            int count = cursor.getInt(cursor.getColumnIndex("count"));
            mWDB.execSQL("UPDATE " + TABLE_CART_INFO + " SET count = ? WHERE goods_id = ?", new Object[]{count + 1, info.getId()});
        } else {
            // 没有添加过,则插入一条新记录
            ContentValues values = new ContentValues();
            values.put("goods_id", info.getId());
            values.put("count", 1);
            mWDB.insert(TABLE_CART_INFO, null, values);
        }
    }

    public int getCartCount() {
        Cursor cursor = mRDB.rawQuery("SELECT SUM(count) FROM " + TABLE_CART_INFO, null);
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            return count;
        }
        return 0;
    }

    public List<CartInfo> queryAllCartInfo() {
        List<CartInfo> list = new ArrayList<>();
        Cursor cursor = mRDB.rawQuery("SELECT * FROM " + TABLE_CART_INFO + " AS cart INNER JOIN " + TABLE_GOODS_INFO + " AS goods ON cart.goods_id = goods._id", null);
        while (cursor.moveToNext()) {
            CartInfo info = new CartInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            info.setGoodsId(cursor.getInt(cursor.getColumnIndex("goods_id")));
            info.setCount(cursor.getInt(cursor.getColumnIndex("count")));
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
            goodsInfo.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            goodsInfo.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
            goodsInfo.setPicPath(cursor.getString(cursor.getColumnIndex("pic_path")));
            info.setGoodsInfo(goodsInfo);
            list.add(info);
        }
        return list;
    }
}
