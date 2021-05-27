package com.dhbw.se_motivationsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsGoal extends AppCompatActivity {
    TextView textView;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_goal);
        //textView = findViewById(R.id.test);
        sp = this.getSharedPreferences("SP", 0);
        String goalstr;
        String key = "goal" + Home.id;

        goalstr = sp.getString(key, null);

        Goal goal = Home.jsonToObject(goalstr);

        //textView.setText(goal.getTitle());

    }


}