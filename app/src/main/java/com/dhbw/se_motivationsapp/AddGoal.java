package com.dhbw.se_motivationsapp;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class AddGoal extends Fragment implements View.OnClickListener {
    private EditText title;
    private EditText description;
    private CheckBox notification;
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    private ImageButton addbtn;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private SharedPreferences sp;


    public AddGoal() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_goal, container, false);
        title = view.findViewById(R.id.InputTitelId);

        title.setText("Goal1");
        description = view.findViewById(R.id.DescriptionInputId);
        //end_date = view.findViewById(R.id.endDateInputId);
        notification = view.findViewById(R.id.NotifcationId);
        easy = view.findViewById(R.id.radioButtoneasy);
        medium = view.findViewById(R.id.radioButtonmedium);
        hard = view.findViewById(R.id.radioButtonhard);
        addbtn = view.findViewById(R.id.addbtn);


        initDatePicker();
        dateButton = view.findViewById(R.id.datePickerBtn);
        dateButton.setText(getTodaysDate());

        dateButton.setOnClickListener(this);
        addbtn.setOnClickListener(this);
        sp = view.getContext().getSharedPreferences("SP", 0);


        return view;
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

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private String makeDateString(int year, int month, int day) {
        return day + "." + getMonthFormat(month) + "." + year;
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

    @Override
    public void onClick(View v) {
        if (v.equals(addbtn)) {

            String endstring = (String) dateButton.getText();

            int dif = 0;
            if (easy.isChecked()) {
                dif = 1;
            } else if (medium.isChecked()) {
                dif = 2;
            } else if (hard.isChecked()) {
                dif = 3;
            }
            ArrayList<String> subgoals = new ArrayList<>();

            String t = String.valueOf(title.getText());
            String end_date = (String) dateButton.getText();


            Goal newGoal = new Goal(t, String.valueOf(description.getText()), end_date, notification.isChecked(),
                    dif, subgoals);

            String goalstr = objectToJson(newGoal);

            SharedPreferences.Editor editor = sp.edit();
            int gnum = sp.getInt("goalnumber", 0);
            gnum++;
            editor.putInt("goalnumber", gnum);


            String key = "goal" + String.valueOf(gnum);
            /*Set<String> goalset = new HashSet<>();
            goalset.add(String.valueOf(title.getText()));
            goalset.add(newGoal.getDescription());
            goalset.add(String.valueOf(newGoal.getEnd_date()));
            goalset.add(String.valueOf(newGoal.isNotification()));
            goalset.add(String.valueOf(newGoal.getDifficulty()));
            int i = 0;
            try {
                while (newGoal.getSubgoals().get(i) != null) {
                    goalset.add(newGoal.getSubgoals().get(i));
                    i++;
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }


            goalset.add(String.valueOf(newGoal.getStart_date()));

             */

            editor.putString(key, goalstr);
            editor.commit();
            Toast.makeText(getContext(), "Sucessfully added", Toast.LENGTH_SHORT).show();
            dateButton.setText(getTodaysDate());
            if (easy.isChecked()) {
                easy.setChecked(false);
            } else if (medium.isChecked()) {
                medium.setChecked(false);
            } else if (hard.isChecked()) {
                hard.setChecked(false);
            }
            title.setText("exampleGoal");
            description.setText("");
            if (notification.isChecked()) {
                notification.setChecked(false);
            }


        } else if (v.equals(dateButton)) {
            datePickerDialog.show();

        }
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
}
