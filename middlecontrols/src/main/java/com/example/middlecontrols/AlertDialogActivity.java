package com.example.middlecontrols;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AlertDialogActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
        findViewById(R.id.btn_alert_dialog).setOnClickListener(this);
        tv_result = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        // 创建提醒对话框的构造器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("尊敬的用户")   // 设置对话框的标题文本
                .setMessage("你真的要卸载我吗？")    // 设置对话框的内容文本
                .setPositiveButton("残忍卸载", (dialog, which) -> { // 设置对话框的肯定按钮
                    tv_result.setText("虽然依依不舍,但是只能离开了");
                })
                .setNegativeButton("我再想想", (dialog, which) -> { // 设置对话框的否定按钮
                    tv_result.setText("让我再陪你三百六十五个日夜");
                })
                .setNeutralButton("取消", (dialog, which) -> {
                    tv_result.setText("我已取消");
                })
                .create() // 创建提醒对话框
                .show(); // 显示提醒对话框
    }
}