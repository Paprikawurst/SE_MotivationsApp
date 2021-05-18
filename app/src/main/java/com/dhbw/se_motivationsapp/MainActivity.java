package com.dhbw.se_motivationsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private TextView points_text;
    private TextView title;
    private SharedPreferences sp;
    private int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        points_text = findViewById(R.id.points);
        title = findViewById(R.id.variabel_text);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);



        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Home()).commit();

        //SharedPreferences auslesen
        sp = getSharedPreferences("SP", 0);
        points = sp.getInt("points", 0);
        points_text.setText(String.valueOf(points));


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.home2:
                    title.setText("Your Goals");
                    selectedFragment = new Home();
                    break;
                case R.id.goal:
                    title.setText("Add New Goal");
                    selectedFragment = new Goal();
                    break;
                case R.id.reward:
                    title.setText("Achievements");
                    selectedFragment = new Reward();
                    break;
                case R.id.shop2:
                    title.setText("Shop");
                    selectedFragment = new Shop();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        }
    };


}