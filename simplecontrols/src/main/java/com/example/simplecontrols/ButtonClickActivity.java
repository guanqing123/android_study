package com.example.simplecontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ButtonClickActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_click);
        tv_result = findViewById(R.id.tv_result);
        Button btn_click_single = findViewById(R.id.btn_click_single);
        btn_click_single.setOnClickListener(new MyBtnClickListener(tv_result));

        Button btn_click_public = findViewById(R.id.btn_click_public);
        btn_click_public.setOnClickListener(this);

        Button btn_long_click = findViewById(R.id.btn_long_click);
        btn_long_click.setOnLongClickListener(v -> {
            String desc = String.format("%s 您长按点击了按钮 %s", new SimpleDateFormat("HH:mm:ss").format(new Date()), ((Button)v).getText());
            tv_result.setText(desc);
            return true;
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_click_public) {
            String desc = String.format("%s 您点击了按钮 %s", new SimpleDateFormat("HH:mm:ss").format(new Date()), ((Button)v).getText());
            tv_result.setText(desc);
        }
    }

    static class MyBtnClickListener implements View.OnClickListener {
        private final TextView tv_result;

        public MyBtnClickListener(TextView tv_result) {
            this.tv_result = tv_result;
        }

        @Override
        public void onClick(View v) {
            String desc = String.format("%s 您点击了按钮 %s", new SimpleDateFormat("HH:mm:ss").format(new Date()), ((Button)v).getText());
            tv_result.setText(desc);
        }
    }
}