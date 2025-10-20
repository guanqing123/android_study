package com.example.simplecontrols;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // jump to text_view
        Button to_text_view = findViewById(R.id.to_text_view);
        to_text_view.setOnClickListener(tv -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, TextViewActivity.class);
            startActivity(intent);
        });
        // jump to view_border
        Button to_view_border = findViewById(R.id.to_view_border);
        to_view_border.setOnClickListener(tv -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ViewBorderActivity.class);
            startActivity(intent);
        });
        // jump to view_margin
        Button to_view_margin = findViewById(R.id.to_view_margin);
        to_view_margin.setOnClickListener(tv -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ViewMarginActivity.class);
            startActivity(intent);
        });
        // jump to scroll_view
        Button to_scroll_view = findViewById(R.id.to_scroll_view);
        to_scroll_view.setOnClickListener(tv -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ScrollViewActivity.class);
            startActivity(intent);
        });
    }
}