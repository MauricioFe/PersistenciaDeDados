package com.example.persistenciadedados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        if (preferences.contains("ja_abriu_app")) {
            mostrarMain();
        } else {
            adicionarPreferenceJaAbriu(preferences);
            mostrarSplash();
        }
    }

    private void mostrarSplash() {
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMain();
            }
        }, 2000);
    }

    private void mostrarMain() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void adicionarPreferenceJaAbriu(SharedPreferences preferences){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("ja_abriu_app", true);
        editor.commit();
    }
}
