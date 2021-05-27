package com.dhbw.se_motivationsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Calendar;

public class DetailsGoal extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    private SharedPreferences sp;
    private EditText title, description, sub;
    private CheckBox notification;
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    private ImageButton delete, save, done, back, addSub;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private String key;
    private Goal goal;
    private ArrayList<String> subgoals = new ArrayList<>();
    private LinearLayout subLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_goal);

        sp = this.getSharedPreferences("SP", 0);
        String goalstr;
        key = "goal" + Home.id;

        goalstr = sp.getString(key, null);

        goal = Home.jsonToObject(goalstr);

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
        subLayout = findViewById(R.id.subLayout);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerBtn);


        dateButton.setOnClickListener(this);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        save.setOnClickListener(this);
        done.setOnClickListener(this);
        addSub.setOnClickListener(this);


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

        createSubgoals();


    }

    private void createSubgoals() {
        subgoals = goal.getSubgoals();
        int sub_number = subgoals.size();

        for (int i = 0; i <= sub_number; i++) {
            try {
                int c = i + 1;
                String sk = "sub" + c;
                boolean done = sp.getBoolean(sk, false);

                CheckBox checkBox = new CheckBox(this);
                checkBox.setText(subgoals.get(i));
                checkBox.setId(c);
                checkBox.setChecked(done);
                subLayout.addView(checkBox);


            } catch (Exception e) {
                e.printStackTrace();
            }

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
            saveGoalChanges();
            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();


        } else if (view.equals(done)) {
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


        } else if (view.equals((dateButton))) {
            datePickerDialog.show();
        } else if (view.equals((addSub))) {
            int sub_number = goal.getSubgoals().size();//sp.getInt("subNumber", 0);
            //sub_number++;
            String s = String.valueOf(sub.getText());
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(s);
            checkBox.setId(sub_number);
            /*SharedPreferences.Editor editor = sp.edit();
            editor.putInt("subNumber", sub_number);
            editor.commit();
             */
            subgoals.add(s);
            subLayout.addView(checkBox);

        }

    }

    private void getReward() {
    }

    private void saveGoalChanges() {
        //Goal Object changes
        goal.setTitle(String.valueOf(title.getText()));
        goal.setDescription(String.valueOf(description.getText()));
        goal.setEnd_date(String.valueOf(dateButton.getText()));
        int dif = 0;
        if (easy.isChecked()) {
            dif = 1;
        } else if (medium.isChecked()) {
            dif = 2;
        } else if (hard.isChecked()) {
            dif = 3;
        }
        goal.setDifficulty(dif);
        goal.setNotification(notification.isChecked());
        goal.setSubgoals(subgoals);

        //Save in existing SP-key
        String goalstr = objectToJson(goal);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, goalstr);
        editor.commit();

        //Save checked subgoals
        saveCheckedSubGoals();
    }

    private void saveCheckedSubGoals() {
        int sub_number = subgoals.size();//sp.getInt("subNumber", 1);
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

    //Aim is to delete the goal object from the SP file and moveup every further goal object & decrement goalnumber
    private void deleteGoalFromSp() {
        int gnum = sp.getInt("goalnumber", 0);
        SharedPreferences.Editor editor = sp.edit();
        int i = Home.id;
        while (i <= gnum) {
            String k = "goal" + String.valueOf(i + 1);
            String nk = "goal" + String.valueOf(i);
            String s = sp.getString(k, null);
            editor.putString(nk, s);
            i++;
            editor.commit();

        }
        gnum--;
        editor.putInt("goalnumber", gnum);
        editor.commit();

    }

    public String objectToJson(Goal goal) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            //mapper.writeValue(new File(getFilesDir(), "person.json"), person);  // write to file
            String jsonStr = mapper.writeValueAsString(goal);                   // write to string
            //System.out.println("object -> json string\n" + jsonStr);
            return jsonStr;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}