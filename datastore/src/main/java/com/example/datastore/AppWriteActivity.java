package com.example.datastore;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AppWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_write);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);
        findViewById(R.id.btn_save).setOnClickListener(this);

        app = MyApplication.getInstance();
        reload();
    }

    private void reload() {
        String name = app.infoMap.get("name");
        if (null != name) et_name.setText(name);
        String age = app.infoMap.get("age");
        if (null != age) et_age.setText(age);
        String height = app.infoMap.get("height");
        if (null != height) et_height.setText(height);
        String weight = app.infoMap.get("weight");
        if (null != weight) et_weight.setText(weight);
        String married = app.infoMap.get("married");
        if (null != married) ck_married.setChecked(married.equals("1"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save: {
                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String weight = et_weight.getText().toString();
                String married = ck_married.isChecked() ? "1" : "0";

                app.infoMap.put("name", name);
                app.infoMap.put("age", age);
                app.infoMap.put("height", height);
                app.infoMap.put("weight", weight);
                app.infoMap.put("married", married);
                break;
            }
        }
    }
}