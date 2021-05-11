package com.dhbw.se_motivationsapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Points extends AppCompatActivity {

    private static int points = 10;
    private TextView points_text;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_layout);
        points_text = findViewById(R.id.points);
        String p = String.valueOf(3);
        points_text.setText(points);
    }
}
