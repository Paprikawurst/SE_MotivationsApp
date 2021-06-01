package com.dhbw.se_motivationsapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private TextView points_text;
    private TextView title;
    private SharedPreferences spref;
    private int points;
    private int goalnumber;

    Date currentTime = Calendar.getInstance().getTime();

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
        currentTime.getHours();
        //System.out.println(currentTime);
        show_Notification();

        //SharedPreferences auslesen
        System.out.println("Test123");
        spref = getSharedPreferences("SP", 0);
        points = spref.getInt("points", 200);
        points_text.setText(String.valueOf(points));

        goalnumber = spref.getInt("goalnumber", 0);

        //test begin
        /*SharedPreferences.Editor editor = spref.edit();
        editor.putInt("points", 200);
        editor.commit();
        points_text.setText(String.valueOf(spref.getInt("points", 0)));

         */
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
            String enddate = goal.getEnd_date();
            if(goal.isNotification()) {
                goalappendstr.append("\n").append(goal.getTitle()).append(" days left: ").append(getDayDiff(enddate));
            }

            getDayDiff(enddate);

            System.out.println(enddate);
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


    private int getDayDiff(String enddate) {
        //  System.out.println("Enddatum" + enddate);
        String startdate;
        LocalDate today = LocalDate.now();
        startdate = String.valueOf(today);

        int month_end = getMonthInt(enddate);
        char[] start = new char[startdate.length()];
        char[] end = new char[enddate.length()];
        //String convert to chararray
        for (int i = 0; i < startdate.length(); i++) {
            start[i] = startdate.charAt(i);
        }
        for (int i = 0; i < enddate.length(); i++) {
            end[i] = enddate.charAt(i);
        }
        //new Strings of chars (parts of the date)
        String year = String.copyValueOf(start, 0, 4);
        int y = Integer.parseInt(year);

        String month = String.copyValueOf(start, 5, 2);
        int m = Integer.parseInt(month);

        String day = String.copyValueOf(start, 8, 2);
        int d = Integer.parseInt(day);

        String endyear = String.copyValueOf(end, end.length - 4, 4);
        int endy = Integer.parseInt(endyear);
        String endday;
        int endd;
        if (Character.isDigit(end[1])) {
            endday = String.copyValueOf(end, 0, 2);
            endd = Integer.parseInt(endday);
        } else {
            endday = String.copyValueOf(end, 0, 1);
            endd = Integer.parseInt(endday);
        }
        return getDayDif(endd,d,month_end,m);
    }

    private int getDayDif(int endd, int d, int monthend, int m) {
        if (monthend == m) {
            return endd - d;
        } else {
            int i;
            switch (m) {
                case 1:
                    i = 31;
                    break;
                case 2:
                    i = 28;
                    break;
                case 3:
                    i = 31;
                    break;
                case 4:
                    i = 30;
                    break;
                case 5:
                    i = 31;
                    break;
                case 6:
                    i = 30;
                    break;
                case 7:
                    i = 31;
                    break;
                case 8:
                    i = 31;
                    break;
                case 9:
                    i = 30;
                    break;
                case 10:
                    i = 31;
                    break;
                case 11:
                    i = 30;
                    break;
                case 12:
                    i = 31;
                    break;
                default:
                    i = 30;
                    break;
            }
            return i - d + endd;
        }
    }

    private int getMonthInt(String month) {
        int i;
        if (month.contains("Jan")) {
            i = 1;
        } else if (month.contains("Feb")) {
            i = 2;
        } else if (month.contains("Mar")) {
            i = 3;
        } else if (month.contains("Apr")) {
            i = 4;
        } else if (month.contains("May")) {
            i = 5;
        } else if (month.contains("Jun")) {
            i = 6;
        } else if (month.contains("Jul")) {
            i = 7;
        } else if (month.contains("Aug")) {
            i = 8;
        } else if (month.contains("Sep")) {
            i = 9;
        } else if (month.contains("Oct")) {
            i = 10;
        } else if (month.contains("Nov")) {
            i = 11;
        } else if (month.contains("Dec")) {
            i = 12;
        } else {
            return 100;
        }


        return i;
    }

}