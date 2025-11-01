package com.example.datastore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datastore.database.UserDBHelper;
import com.example.datastore.entity.User;

import java.util.List;

public class SQLiteHelperActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private UserDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_helper);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
        findViewById(R.id.btn_query_name).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器的实例
        dbHelper = UserDBHelper.getInstance(this);
        // 打开数据库的读写连接
        dbHelper.openWriteLink();
        dbHelper.openReadLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 关闭数据库连接
        dbHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save: {
                User user = new User(et_name.getText().toString(),
                        Integer.parseInt(et_age.getText().toString()),
                        Float.parseFloat(et_height.getText().toString()),
                        Float.parseFloat(et_weight.getText().toString()),
                        ck_married.isChecked());
                if (dbHelper.insert(user)>0) {
                    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btn_delete: {
                if (dbHelper.deleteByName(et_name.getText().toString())>0) {
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btn_update: {
                User user = new User(et_name.getText().toString(),
                        Integer.parseInt(et_age.getText().toString()),
                        Float.parseFloat(et_height.getText().toString()),
                        Float.parseFloat(et_weight.getText().toString()),
                        ck_married.isChecked());
                if (dbHelper.update(user)>0) {
                    Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btn_query: {
                List<User> list = dbHelper.queryAll();
                if (list.size()>0) {
                    User user = list.get(0);
                    et_name.setText(user.name);
                    et_age.setText(String.valueOf(user.age));
                    et_height.setText(String.valueOf(user.height));
                    et_weight.setText(String.valueOf(user.weight));
                    ck_married.setChecked(user.married);
                }
            }
            case R.id.btn_query_name: {
                if (et_name.getText().toString().isEmpty()) {
                    Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<User> list = dbHelper.queryByName(et_name.getText().toString());
                if (list.size()>0) {
                    User user = list.get(0);
                    Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}