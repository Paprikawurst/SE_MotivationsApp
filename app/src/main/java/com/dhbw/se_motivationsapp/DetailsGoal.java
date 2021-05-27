package com.dhbw.se_motivationsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Calendar;

public class DetailsGoal extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    private SharedPreferences sp;
    private EditText title, description;
    private CheckBox notification;
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    private ImageButton delete, save, done, back;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_goal);

        sp = this.getSharedPreferences("SP", 0);
        String goalstr;
        String key = "goal" + Home.id;

        goalstr = sp.getString(key, null);

        Goal goal = Home.jsonToObject(goalstr);


        title = findViewById(R.id.InputTitelId);
        description = findViewById(R.id.DescriptionInputId);
        notification = findViewById(R.id.NotifcationId);
        easy = findViewById(R.id.radioButtoneasy);
        medium = findViewById(R.id.radioButtonmedium);
        hard = findViewById(R.id.radioButtonhard);
        delete = findViewById(R.id.deletebtn);
        save = findViewById(R.id.savebtn);
        done = findViewById(R.id.doneBtn);
        back = findViewById(R.id.backBtn);


        initDatePicker();
        dateButton = findViewById(R.id.datePickerBtn);


        dateButton.setOnClickListener(this);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        save.setOnClickListener(this);
        done.setOnClickListener(this);


        title.setText(goal.getTitle());
        description.setText(goal.getDescription());
        dateButton.setText(goal.getEnd_date());
        notification.setActivated(goal.isNotification());
        switch (goal.getDifficulty()) {
            case 1:
                easy.setChecked(true);
                medium.setChecked(false);
                hard.setChecked(false);
                break;
            case 2:
                easy.setChecked(false);
                medium.setChecked(true);
                hard.setChecked(false);
                break;
            case 3:
                easy.setChecked(false);
                medium.setChecked(false);
                hard.setChecked(true);
                break;
            default:
                easy.setChecked(false);
                medium.setChecked(false);
                hard.setChecked(false);
                break;
        }


    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        month++;
        String date = makeDateString(year, month, day);


        return date;
    }

    private String makeDateString(int year, int month, int day) {
        return day + "." + getMonthFormat(month) + "." + year;
    }

    private String getMonthFormat(int month) {
        String s;
        switch (month) {
            case 1:
                s = "Jan";
                break;
            case 2:
                s = "Feb";
                break;
            case 3:
                s = "Mar";
                break;
            case 4:
                s = "Apr";
                break;
            case 5:
                s = "May";
                break;
            case 6:
                s = "Jun";
                break;
            case 7:
                s = "Jul";
                break;
            case 8:
                s = "Aug";
                break;
            case 9:
                s = "Sep";
                break;
            case 10:
                s = "Oct";
                break;
            case 11:
                s = "Nov";
                break;
            case 12:
                s = "Dec";
                break;
            default:
                s = "Jan";
                break;
        }
        return s;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String date = makeDateString(year, month, day);
                dateButton.setText(date);

            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }


    @Override
    public void onClick(View view) {
        if (view.equals(back)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        } else if (view.equals(delete)) {
            deleteGoalFromSp();
            Toast.makeText(this, "Goal deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        } else if (view.equals(save)) {
            saveGoalChanges();
            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
        } else if (view.equals(done)) {
            Toast.makeText(this, "Congrats! Goal achieved!", Toast.LENGTH_LONG).show();
            getReward();
            deleteGoalFromSp();
        }

    }

    private void getReward() {
    }

    private void saveGoalChanges() {
    }

    private void deleteGoalFromSp() {
    }
}