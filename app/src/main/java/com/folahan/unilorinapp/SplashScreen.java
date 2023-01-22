package com.folahan.unilorinapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.folahan.unilorinapp.Activity.LoginActivity;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {
    public static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(SPLASH_TIME_OUT);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });
        thread.start();
        finish();
    }
}