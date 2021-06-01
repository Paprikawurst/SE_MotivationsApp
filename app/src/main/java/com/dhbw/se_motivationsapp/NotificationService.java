package com.dhbw.se_motivationsapp;

import android.app.NotificationChannel ;
import android.app.NotificationManager ;
import android.app.PendingIntent;
import android.app.Service ;
import android.content.Context;
import android.content.Intent ;
import android.content.SharedPreferences;
import android.os.Handler ;
import android.os.IBinder ;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.util.Log ;

import androidx.core.app.NotificationCompat;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Timer ;
import java.util.TimerTask ;
public class NotificationService extends Service {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    Timer timer ;
    TimerTask timerTask ;
    String TAG = "Timers" ;

    @Override
    public IBinder onBind (Intent arg0) {
        return null;
    }
    @Override
    public int onStartCommand (Intent intent , int flags , int startId) {
        Log. e ( TAG , "onStartCommand" ) ;
        super .onStartCommand(intent , flags , startId) ;
        startTimer() ;
        return START_STICKY ;
    }
    @Override
    public void onCreate () {
        Log. e ( TAG , "onCreate" ) ;
    }
    @Override
    public void onDestroy () {
        Log. e ( TAG , "onDestroy" ) ;
        stopTimerTask() ;
        super .onDestroy() ;
    }
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler() ;
    public void startTimer () {
        OffsetDateTime currentTime = OffsetDateTime.now(ZoneId.of("Europe/Berlin"));
        int current_hour = currentTime.getHour();
        int current_min = currentTime.getMinute();
        int hour_diff = current_hour - 6;
        hour_diff = hour_diff - 24;
        int min_diff = current_min - 60;

        if (hour_diff < 0){
            hour_diff = hour_diff * (-1);
        }
        if (min_diff < 0){
            min_diff = min_diff * (-1);
        }
        if (min_diff == 60){
            min_diff = 0;
        }

        int seconds_untill_next_notification = hour_diff * 3600 + min_diff * 60;


        System.out.println(seconds_untill_next_notification);
        System.out.println(hour_diff );
        System.out.println(min_diff);
        timer = new Timer() ;
        initializeTimerTask() ;
        timer .schedule( timerTask , 5000 , seconds_untill_next_notification * 1000 ) ; //
    }
    public void stopTimerTask () {
        if ( timer != null ) {
            timer .cancel() ;
            timer = null;
        }
    }
    public void initializeTimerTask () {
        timerTask = new TimerTask() {
            public void run () {
                handler .post( new Runnable() {
                    public void run () {
                        createNotification() ;
                    }
                }) ;
            }
        } ;
    }
    private void createNotification () {
        SharedPreferences sp;
        StringBuilder goalappendstr = new StringBuilder();
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
                .setContentTitle("Your open goals:")
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