package com.dhbw.se_motivationsapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.Settings;


import androidx.core.app.NotificationCompat;

import java.time.LocalDate;

class NotificationHelper {

    private Context mContext;
    private static final String NOTIFICATION_CHANNEL_ID = "10001";

    NotificationHelper(Context context) {
        mContext = context;
    }

    //creates the notification
    void createNotification()
    {
        SharedPreferences sp;
        StringBuilder goalappendstr = new StringBuilder();
        sp = mContext.getSharedPreferences("SP",0);
        int gnum = sp.getInt("goalnumber", 0);

        for (int i = 0; i < gnum; i++) {
            int c = i + 1;
            String goalstr;
            String key = "goal" + c;

            goalstr = sp.getString(key, null);

            Goal goal = Home.jsonToObject(goalstr);
            assert goal != null;
            String enddate = goal.getEnd_date();
            if (goal.isNotification()) {
                goalappendstr.append("\n").append(goal.getTitle()).append(" days left: ").append(getDayDiff(enddate));
            }
            getDayDiff(enddate);
        }

        Intent intent = new Intent(mContext , MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
        mBuilder.setContentTitle("Your open goals:")
                .setContentIntent(resultPendingIntent)
                .setContentText(goalappendstr.toString())
                .setAutoCancel(false)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(goalappendstr.toString()))
                .setSmallIcon(R.drawable.baseline_task_alt_24)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(1, mBuilder.build());
    }

    //gets the day difference between now and end date
    private int getDayDiff(String end_date) {
        String start_date;
        LocalDate today = LocalDate.now();
        start_date = String.valueOf(today);

        int month_end = getMonthInt(end_date);
        char[] start = new char[start_date.length()];
        char[] end = new char[end_date.length()];
        for (int i = 0; i < start_date.length(); i++) {
            start[i] = start_date.charAt(i);
        }
        for (int i = 0; i < end_date.length(); i++) {
            end[i] = end_date.charAt(i);
        }

        String month = String.copyValueOf(start, 5, 2);
        int m = Integer.parseInt(month);

        String day = String.copyValueOf(start, 8, 2);
        int d = Integer.parseInt(day);

        String end_day;
        int endd;
        if (Character.isDigit(end[1])) {
            end_day = String.copyValueOf(end, 0, 2);
            endd = Integer.parseInt(end_day);
        } else {
            end_day = String.copyValueOf(end, 0, 1);
            endd = Integer.parseInt(end_day);
        }
        return getDayDif(endd, d, month_end, m);
    }

    //helper method for getButtonColor to calculate the days between now and deadline day
    private int getDayDif(int endd, int d, int month_end, int m) {
        if (month_end == m) {
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

    //String month transform into integer
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
