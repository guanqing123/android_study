package com.example.simplecontrols;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button to_text_view = findViewById(R.id.to_text_view);
        to_text_view.setOnClickListener(tv -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, TextViewActivity.class);
            startActivity(intent);
        });
    }
}