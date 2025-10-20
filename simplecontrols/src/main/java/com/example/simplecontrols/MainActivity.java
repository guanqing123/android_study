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
        // jump to button_view
        Button to_button_view = findViewById(R.id.to_button_view);
        to_button_view.setOnClickListener(tv -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ButtonStyleActivity.class);
            startActivity(intent);
        });
        // jump to button_click
        Button to_button_click = findViewById(R.id.to_button_click);
        to_button_click.setOnClickListener(tv -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ButtonClickActivity.class);
            startActivity(intent);
        });
        // jump to button_enable
        Button to_button_enable = findViewById(R.id.to_button_enable);
        to_button_enable.setOnClickListener(tv -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ButtonEnableActivity.class);
            startActivity(intent);
        });
        // jump to image_view
        Button to_image_view = findViewById(R.id.to_image_view);
        to_image_view.setOnClickListener(tv -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ImageScaleActivity.class);
            startActivity(intent);
        });
        // jump to image_text
        Button to_image_text = findViewById(R.id.to_image_text);
        to_image_text.setOnClickListener(tv -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ImageTextActivity.class);
            startActivity(intent);
        });
        // jump to calculator
        Button to_calculator = findViewById(R.id.to_calculator);
        to_calculator.setOnClickListener(tv -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, CalculatorActivity.class);
            startActivity(intent);
        });
    }
}