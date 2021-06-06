package com.dhbw.se_motivationsapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private TextView title;
    //functionality of navigationbar -> switchs fragments
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
                    selectedFragment = new AddGoal();
                    break;
                case R.id.reward:
                    title.setText("Rewards");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //default actionbar delete
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //layout and tools connecting to code
        setContentView(R.layout.activity_main);
        TextView points_text = findViewById(R.id.points);
        title = findViewById(R.id.variabel_text);
        //declaration
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        View view_toolbar = findViewById(R.id.toolbar);

        //starts first fragment home
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Home()).commit();

        //read SharedPreferences
        SharedPreferences spref = getSharedPreferences("SP", 0);
        int points = spref.getInt("points", 0);
        points_text.setText(String.valueOf(points));

        view_toolbar.setBackgroundColor(spref.getInt("color", Color.DKGRAY));

    }

    @Override
    protected void onStop() {
        super.onStop();
        setAlarm();
    }

    //set the alarm to specific time
    public void setAlarm() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Berlin")));
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.getTime());

        if (calendar.getTime().compareTo(new Date()) < 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
        }

    }


}
