package com.dhbw.se_motivationsapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private TextView points_text;
    private TextView title;
    private SharedPreferences spref;
    private int points;
    private int goalnumber;

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

        show_Notification();

        //SharedPreferences auslesen
        spref = getSharedPreferences("SP", 0);
        points = spref.getInt("points", 0);
        points_text.setText(String.valueOf(points));

        goalnumber = spref.getInt("goalnumber", 0);

        //test begin
        SharedPreferences.Editor editor = spref.edit();
        editor.putInt("points", 200);
        editor.commit();
        points_text.setText(String.valueOf(spref.getInt("points", 0)));
        //test ende


        //clear SP TODO
        /*editor.putBoolean("purchased_red",false);
        editor.putBoolean("purchased_blue",false);
        editor.putBoolea"purchased_black",false);
        editor.putBoolean("purchased_green",false);
        editor.putBoolean("purchased_cyan",false);
        editor.putBoolean("purchased_darkgray",false);
        editor.putBoolean("purchased_gray",false);

        editor.putBoolean("activated_red",false);
        editor.putBoolean("activated_blue",false);
        editor.putBoolean("activated_black",false);
        editor.putBoolean("activated_green",false);
        editor.putBoolean("activated_cyan",false);
        editor.putBoolean("activated_darkgray",false);
        editor.putBoolean("activated_gray",false);

        editor.putBoolean("Reward1redeemed",false);
        editor.putBoolean("Reward2redeemed",false);

        editor.commit();*/

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
                    selectedFragment = new AddGoal();
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

    public void show_Notification() {
        SharedPreferences sp;
        StringBuilder goalappendstr = new StringBuilder();
        String daysleft = null;
        LocalDate today = LocalDate.now();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        String CHANNEL_ID = "MYCHANNEL";
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        sp = this.getSharedPreferences("SP", 0);
        int gnum = sp.getInt("goalnumber", 0);

        for (int i = 0; i < gnum; i++) {
            int c = i + 1;
            String goalstr;
            String key = "goal" + c;

            goalstr = sp.getString(key, null);

            Goal goal = Home.jsonToObject(goalstr);

            assert goal != null;
            if(goal.isNotification()) {
                goalappendstr.append("\n").append(goal.getTitle());
            }

            System.out.println(goalappendstr);
        }

            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentTitle("Deine offenen Ziele:")
                    .setContentIntent(pendingIntent)
                    .setChannelId(CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_task_alt_24)
                    .setStyle(new Notification.BigTextStyle().bigText(goalappendstr.toString()))
                    .build();

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(1, notification);



    }


}