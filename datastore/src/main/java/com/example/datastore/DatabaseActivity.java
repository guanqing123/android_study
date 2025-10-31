package com.example.datastore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DatabaseActivity extends AppCompatActivity implements View.OnClickListener {

    private String dbName;
    private TextView tv_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        findViewById(R.id.btn_create_database).setOnClickListener(this);
        findViewById(R.id.btn_delete_database).setOnClickListener(this);
        tv_database = findViewById(R.id.tv_database);
        // 生成一个测试数据库的完整路径
        dbName = getFilesDir() + "/test.db";
    }

    @Override
    public void onClick(View v) {
        String desc = "";
        switch (v.getId()) {
            case R.id.btn_create_database: {
                // 创建或打开数据库。数据库如果不存在就创建它，如果存在就打开它
                SQLiteDatabase db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
                desc = String.format("数据库%s创建%s", db.getPath(), (db != null) ? "成功" : "失败");
                tv_database.setText(desc);
                break;
            }
            case R.id.btn_delete_database: {
                // 删除数据库
                boolean result = deleteDatabase(dbName);
                desc = String.format("数据库%s删除%s", dbName, (result) ? "成功" : "失败");
                tv_database.setText(desc);
                break;
            }
        }
    }
}