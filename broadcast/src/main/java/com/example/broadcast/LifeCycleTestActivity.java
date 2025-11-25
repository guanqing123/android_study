package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class LifeCycleTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_test);
        Log.d("gq", "LifeCycleTestActivity onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("gq", "LifeCycleTestActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("gq", "LifeCycleTestActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("gq", "LifeCycleTestActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("gq", "LifeCycleTestActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("gq", "LifeCycleTestActivity onDestroy");
    }
}