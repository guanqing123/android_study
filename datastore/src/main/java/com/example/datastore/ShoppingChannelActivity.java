package com.example.datastore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.datastore.database.ShoppingDBHelper;

public class ShoppingChannelActivity extends AppCompatActivity {

    // 声明一个商品数据库的帮助器对象
    private ShoppingDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);

        dbHelper = ShoppingDBHelper.getInstance(this);
        dbHelper.openWriteLink();
        dbHelper.openReadLink();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.closeLink();
    }
}