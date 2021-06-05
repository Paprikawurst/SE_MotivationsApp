package com.dhbw.se_motivationsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailsGoal extends AppCompatActivity implements View.OnClickListener {

    //declaration
    private SharedPreferences sp;
    private EditText title, description, sub;
    private CheckBox notification;
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    private ImageButton delete, save, done, back, addSub;
    private DatePickerDialog date_picker_dialog;
    private Button date_button;
    private String key;
    private Goal goal;
    private ArrayList<String> sub_goals = new ArrayList<>();
    private LinearLayout sub_layout;

    TextView points_text;

    //on Create of DetailsGoal
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_details_goal);

        //read out the goal which was clicked
        sp = this.getSharedPreferences("SP", 0);
        String goalstr;
        //get Id from Home.class
        key = "goal" + Home.id;

        goalstr = sp.getString(key, null);

        goal = Home.jsonToObject(goalstr);

        //connect tools with code
        sub = findViewById(R.id.subInput);
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
        addSub = findViewById(R.id.addSubBtn);
        sub_layout = findViewById(R.id.subsublayout);

        initDatePicker();
        date_button = findViewById(R.id.datePickerBtn);

        //add Listeners to Buttons
        date_button.setOnClickListener(this);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        save.setOnClickListener(this);
        done.setOnClickListener(this);
        addSub.setOnClickListener(this);


        //fill fragment with goal data
        title.setText(goal.getTitle());
        description.setText(goal.getDescription());
        date_button.setText(goal.getEnd_date());
        notification.setChecked(goal.isNotification());
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

        createSubgoals();

        int points = sp.getInt("points", 0);

        View view_toolbar = this.findViewById(R.id.toolbar);
        points_text = view_toolbar.findViewById(R.id.points);
        points_text.setText(String.valueOf(points));

    }

    //if back key is pressed then stop activity and get back to MainActivity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    //create subgoals as checkboxes in fragment
    private void createSubgoals() {
        sub_goals = goal.getSubgoals();
        int sub_number = sub_goals.size();
        LinearLayout.LayoutParams layout_params_wrapper = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        sub_layout.setLayoutParams(layout_params_wrapper);

        View view_toolbar = findViewById(R.id.toolbar);
        view_toolbar.setBackgroundColor(sp.getInt("color", Color.DKGRAY));

        for (int i = 0; i < sub_number; i++) {
            try {
                int c = i + 1;
                //read out if subgoal is checked
                String sk = "sub" + String.valueOf(c);
                boolean done = sp.getBoolean(sk, false);

                CheckBox check_box = new CheckBox(this);
                check_box.setText(sub_goals.get(i));
                LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layout_params.gravity = Gravity.CENTER_HORIZONTAL;
                check_box.setLayoutParams(layout_params);
                check_box.setId(c);
                check_box.setChecked(done);
                sub_layout.addView(check_box);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    //returns european date format
    private String makeDateString(int year, int month, int day) {
        return day + "." + getMonthFormat(month) + "." + year;
    }

    //returns month as a String
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

    //open datepickerdialog for user when date is clicked
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker date_picker, int year, int month, int day) {
                month++;
                String date = makeDateString(year, month, day);
                date_button.setText(date);

            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        date_picker_dialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }


    //on Click Listener
    @Override
    public void onClick(View view) {
        if (view.equals(back)) {
            //back to MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        } else if (view.equals(delete)) {
            //if Confirmation is checked goal is getting deleted and Mainactivity starts
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Sure?")
                    .setMessage("Do you really want to delete this goal?")
                    .setCancelable(true)
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteGoalFromSp();
                            Toast.makeText(getBaseContext(), "Goal deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();


        } else if (view.equals(save)) {
            //method is called
            saveGoalChanges();

        } else if (view.equals(done)) {
            //if Confirmation is checked goal is getting deleted, user gets reward and Mainactivity starts
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Done?")
                    .setMessage("Did you achieve the goal?")
                    .setCancelable(true)
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), "Congrats! Goal achieved!", Toast.LENGTH_LONG).show();
                            getReward();
                            deleteGoalFromSp();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            //this.finish();

                        }

                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();


        } else if (view.equals((date_button))) {
            date_picker_dialog.show();
        } else if (view.equals((addSub))) {
            //if add btn is clicked subgoal is added to arraylist. goal adds updated arraylist
            int sub_number = goal.getSubgoals().size() + 1;//sp.getInt("subNumber", 0);
            LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout_params.gravity = Gravity.CENTER_HORIZONTAL;

            //creates checkbox
            String s = String.valueOf(sub.getText());
            CheckBox check_box = new CheckBox(this);
            check_box.setText(s);
            check_box.setId(sub_number);
            check_box.setLayoutParams(layout_params);
            sub_goals.add(s);
            sub_layout.addView(check_box);

        }
    }

    //user gets points and rewards dependent on the difficulty of the goal
    private void getReward() {
        int dif = goal.getDifficulty();
        int points = sp.getInt("points", 200);
        SharedPreferences.Editor editor = sp.edit();

        if (dif == 2) {
            int goal_medium = sp.getInt("mediumGoals", 0);
            goal_medium++;
            editor.putInt("mediumGoals", goal_medium);
            points += 20;
        } else if (dif == 3) {
            points += 30;
            int goal_hard = sp.getInt("hardGoals", 0);
            goal_hard++;
            editor.putInt("hardGoals", goal_hard);
        } else {
            points += 10;
            int goal_easy = sp.getInt("easyGoals", 0);
            goal_easy++;
            editor.putInt("easyGoals", goal_easy);

        }

        int goals = sp.getInt("goals", 0);
        goals++;
        editor.putInt("goals", goals);
        editor.putInt("points", points);
        editor.commit();

        View view_toolbar = this.findViewById(R.id.toolbar);
        points_text = view_toolbar.findViewById(R.id.points);
        points_text.setText(String.valueOf(points));

    }

    //saves changes of the goal int the object and object is saved as JSON String in SP
    private void saveGoalChanges() {
        //Goal Object changes
        String t = String.valueOf(title.getText());
        String end_date = String.valueOf(date_button.getText());
        if (t.length() > 0) {
            if (isFuture(end_date)) {
                goal.setTitle(t);
                goal.setDescription(String.valueOf(description.getText()));
                goal.setEnd_date(end_date);
                int dif = 0;
                if (easy.isChecked()) {
                    dif = 1;
                } else if (medium.isChecked()) {
                    dif = 2;
                } else if (hard.isChecked()) {
                    dif = 3;
                }
                goal.setDifficulty(dif);
                goal.setNotification(notification.isChecked());  // ischecked not isactivated
                goal.setSubgoals(sub_goals);

                //Save in existing SP-key
                String goalstr = objectToJson(goal);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(key, goalstr);
                editor.commit();

                //Save checked subgoals
                saveCheckedSubGoals();
                Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
            } else {
                new androidx.appcompat.app.AlertDialog.Builder(this)
                        .setTitle("Past Date!")
                        .setMessage("Deadline date is in the past.")
                        .setCancelable(true)
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).show();
            }
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Missing title!")
                    .setMessage("Title field can't be empty")
                    .setCancelable(true)
                    .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }

                    }).show();
        }

    }

    //check if picked date is in future or today
    private boolean isFuture(String enddate) {

        String start_date;
        LocalDate today = LocalDate.now();
        start_date = String.valueOf(today);

        int month_end = getMonthInt(enddate);
        char[] start = new char[start_date.length()];
        char[] end = new char[enddate.length()];

        for (int i = 0; i < start_date.length(); i++) {
            start[i] = start_date.charAt(i);
        }
        for (int i = 0; i < enddate.length(); i++) {
            end[i] = enddate.charAt(i);
        }

        String year = String.copyValueOf(start, 0, 4);
        int y = Integer.parseInt(year);

        String month = String.copyValueOf(start, 5, 2);
        int m = Integer.parseInt(month);

        String day = String.copyValueOf(start, 8, 2);
        int d = Integer.parseInt(day);

        String end_year = String.copyValueOf(end, end.length - 4, 4);
        int end_y = Integer.parseInt(end_year);
        String end_day;
        int end_d;
        if (Character.isDigit(end[1])) {
            end_day = String.copyValueOf(end, 0, 2);
            end_d = Integer.parseInt(end_day);
        } else {
            end_day = String.copyValueOf(end, 0, 1);
            end_d = Integer.parseInt(end_day);
        }


        if (y < end_y || month_end > m && y == end_y || end_d >= d && month_end == m && y == end_y) {
            return true;
        } else {
            return false;
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

    //saves checked subgoals in SP
    private void saveCheckedSubGoals() {
        int sub_number = sub_goals.size();
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 1; i <= sub_number; i++) {
            try {
                String sk = "sub" + i;
                CheckBox cb;
                cb = this.findViewById(i);
                editor.putBoolean(sk, cb.isChecked());
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //deletes Goal from SP and set every goal behind one key in front
    private void deleteGoalFromSp() {
        int goal_number = sp.getInt("goalnumber", 0);
        SharedPreferences.Editor editor = sp.edit();
        int i = Home.id;
        while (i <= goal_number) {
            String k = "goal" + String.valueOf(i + 1);
            String nk = "goal" + String.valueOf(i);
            String s = sp.getString(k, null);
            editor.putString(nk, s);
            i++;
            editor.commit();

        }
        goal_number--;
        editor.putInt("goalnumber", goal_number);
        editor.commit();

    }

    //goal object transformed to a JSON String
    public String objectToJson(Goal goal) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json_Str = mapper.writeValueAsString(goal);
            return json_Str;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
