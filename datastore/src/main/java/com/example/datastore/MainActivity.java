package com.example.datastore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("gq", "MainActivity onCreate");
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_share_write).setOnClickListener(this);
        findViewById(R.id.btn_database).setOnClickListener(this);
        findViewById(R.id.btn_sqlite_helper).setOnClickListener(this);
        findViewById(R.id.btn_login_sqlite).setOnClickListener(this);
        findViewById(R.id.btn_file_write).setOnClickListener(this);
        findViewById(R.id.btn_image_write).setOnClickListener(this);
        findViewById(R.id.btn_app_write).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share_write: {
                Intent intent = new Intent(this, ShareWriteActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_database: {
                Intent intent = new Intent(this, DatabaseActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_sqlite_helper: {
                Intent intent = new Intent(this, SQLiteHelperActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_login_sqlite: {
                Intent intent = new Intent(this, LoginSQLiteActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_file_write: {
                Intent intent = new Intent(this, FileWriteActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_image_write: {
                Intent intent = new Intent(this, ImageWriteActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_app_write: {
                Intent intent = new Intent(this, AppWriteActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}