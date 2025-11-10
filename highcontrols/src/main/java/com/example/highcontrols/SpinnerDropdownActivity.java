package com.example.highcontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SpinnerDropdownActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final static String[] startArray = {"水星", "金星", "地球", "火星", "木星", "土星", "天王星", "海王星"};
    private Spinner sp_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_dropdown);

        sp_dialog = findViewById(R.id.sp_dialog);
        // 声明一个下拉列表的数组适配器
        ArrayAdapter<String> startAdapter = new ArrayAdapter<String>(this, R.layout.item_select, startArray);
        sp_dialog.setAdapter(startAdapter);
        // 设置下拉框默认显示第一项
        sp_dialog.setSelection(0);
        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp_dialog.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "您选择的是：" + startArray[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}