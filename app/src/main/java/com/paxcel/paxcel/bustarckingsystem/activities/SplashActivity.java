package com.paxcel.paxcel.bustarckingsystem.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paxcel.paxcel.bustarckingsystem.R;
import com.paxcel.paxcel.bustarckingsystem.utils.SharedPreferencesUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferencesUtils utils=SharedPreferencesUtils.getInstance(SplashActivity.this);

        if(utils.getStringValue("username","").equals("")){
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            finish();
        }else {
            startActivity(new Intent(SplashActivity.this,TabActivity.class));
            finish();

        }
    }
}
