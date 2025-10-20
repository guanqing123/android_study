package com.example.simplecontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ButtonStyleActivity extends AppCompatActivity {

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_style);
        tv_result = findViewById(R.id.tv_result);
    }

    public void doClick(View view){
        String des = String.format("%s 您点击了按钮 %s", new SimpleDateFormat("HH:mm:ss").format(new Date()), ((Button)view).getText());
        tv_result.setText(des);
    }
}