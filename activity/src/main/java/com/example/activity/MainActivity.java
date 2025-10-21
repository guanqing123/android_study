package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.jump_to_actStart).setOnClickListener(this);
        findViewById(R.id.jump_to_first).setOnClickListener(this);
        findViewById(R.id.jump_to_login).setOnClickListener(this);
        findViewById(R.id.jump_to_action_uri).setOnClickListener(this);
        findViewById(R.id.jump_to_actSend).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jump_to_actStart:
                startActivity(new Intent(this, ActStartActivity.class));
                break;
            case R.id.jump_to_first:
                startActivity(new Intent(this, JumpFirstActivity.class));
                break;
            case R.id.jump_to_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.jump_to_action_uri:
                startActivity(new Intent(this, ActionUriActivity.class));
                break;
            case R.id.jump_to_actSend:
                startActivity(new Intent(this, ActSendActivity.class));
                break;
        }
    }
}