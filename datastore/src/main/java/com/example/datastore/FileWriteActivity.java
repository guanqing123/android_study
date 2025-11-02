package com.example.datastore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datastore.util.FileUtil;

import java.io.File;
import java.util.logging.Logger;

public class FileWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private String path;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_write);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);

        tv_result = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save: {
                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String weight = et_weight.getText().toString();
                StringBuffer buffer = new StringBuffer();
                buffer.append("name=").append(name).append("\n");
                buffer.append("age=").append(age).append("\n");
                buffer.append("height=").append(height).append("\n");
                buffer.append("weight=").append(weight).append("\n");
                buffer.append("married=").append(ck_married.isChecked()).append("\n");

                String fileName = System.currentTimeMillis() + ".txt";
                String directory = null;
                // 外部存储的私有空间 /storage/emulated/0/Android/data/com.example.datastore/files/Download/1762002571872.txt
                directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();

                // 外部存储的公共空间 /storage/emulated/0/Download/1762002844520.txt
                //directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

                // 内部存储私有空间 /data/user/0/com.example.datastore/files/1762003044952.txt
                //directory = getFilesDir().toString();
                path = directory + File.separator + fileName;
                FileUtil.saveText(path, buffer.toString());
                Log.d("gq", path);
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_read: {
                String text = FileUtil.readText(path);
                tv_result.setText(text);
                break;
            }
        }
    }
}