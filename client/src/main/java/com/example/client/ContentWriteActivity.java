package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ContentWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_write);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save: {
                ContentValues values = new ContentValues();
                values.put(UserInfoContent.USER_NAME, et_name.getText().toString());
                values.put(UserInfoContent.USER_AGE, Integer.parseInt(et_age.getText().toString()));
                values.put(UserInfoContent.USER_HEIGHT, Float.parseFloat(et_height.getText().toString()));
                values.put(UserInfoContent.USER_WEIGHT, Float.parseFloat(et_weight.getText().toString()));
                values.put(UserInfoContent.USER_MARRIED, ck_married.isChecked());
                getContentResolver().insert(UserInfoContent.CONTENT_URI,  values);
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_query:{
                Cursor cursor = getContentResolver().query(UserInfoContent.CONTENT_URI, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                        String name = cursor.getString(cursor.getColumnIndex(UserInfoContent.USER_NAME));
                        int age = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_AGE));
                        float height = cursor.getFloat(cursor.getColumnIndex(UserInfoContent.USER_HEIGHT));
                        float weight = cursor.getFloat(cursor.getColumnIndex(UserInfoContent.USER_WEIGHT));
                        boolean married = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_MARRIED)) == 1;
                        Log.d("gq", "id=" + id + ", name=" + name + ", age=" + age + ", height=" + height + ", weight=" + weight + ", married=" + married);
                    }
                    cursor.close();
                }
                break;
            }
            case R.id.btn_delete: {
                //content://com.example.server.provider.UserInfoProvider/user/2
                Uri uri = ContentUris.withAppendedId(UserInfoContent.CONTENT_URI, 8);
                int count = getContentResolver().delete(uri, null, null);

                //content://com.example.server.provider.UserInfoProvider/user
                //int count = getContentResolver().delete(UserInfoContent.CONTENT_URI, "name=?", new String[]{"Jack"});
                Toast.makeText(this, "删除了"+count+"条数据", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}