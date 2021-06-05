package com.dhbw.se_motivationsapp;


import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.content.DialogInterface;
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


public class AddGoal extends Fragment implements View.OnClickListener {

    //declaration
    private EditText title;
    private EditText description;
    private CheckBox notification;
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    private ImageButton add_btn;
    private DatePickerDialog date_picker_dialog;
    private Button date_button;
    private SharedPreferences sp;
    View view;

    public AddGoal() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //fragement start

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_goal, container, false);

        //connect tools and layout with code
        title = view.findViewById(R.id.InputTitelId);
        title.setText("Sample goal name");
        description = view.findViewById(R.id.DescriptionInputId);
        notification = view.findViewById(R.id.NotifcationId);
        easy = view.findViewById(R.id.radioButtoneasy);
        medium = view.findViewById(R.id.radioButtonmedium);
        hard = view.findViewById(R.id.radioButtonhard);
        add_btn = view.findViewById(R.id.addbtn);

        initDatePicker();
        date_button = view.findViewById(R.id.datePickerBtn);
        date_button.setText(getTodaysDate());

        //set Listeners
        date_button.setOnClickListener(this);
        add_btn.setOnClickListener(this);

        sp = view.getContext().getSharedPreferences("SP", 0);

        return view;
    }

    //get todays date as a string
    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        month++;

        return makeDateString(year, month, day);
    }

    //open datepickerdialog for user when date is clicked
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
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

        date_picker_dialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);

    }

    //european date format
    private String makeDateString(int year, int month, int day) {
        return day + "." + getMonthFormat(month) + "." + year;
    }

    //goal object transformed to a JSON String
    public String objectToJson(Goal goal) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(goal);
            return jsonStr;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    //onClick Listeners
    @Override
    public void onClick(View v) {
        //goal gets added to Shared Preference
        if (v.equals(add_btn)) {

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
            if (t.length() > 0) {
                String end_date = (String) date_button.getText();
                if (isFuture(end_date)) {
                    //goal object is created
                    Goal newGoal = new Goal(t, String.valueOf(description.getText()), end_date, notification.isChecked(),
                            dif, subgoals);

                    String goal_str = objectToJson(newGoal);

                    SharedPreferences.Editor editor = sp.edit();
                    int gnum = sp.getInt("goalnumber", 0);
                    gnum++;
                    editor.putInt("goalnumber", gnum);


                    String key = "goal" + String.valueOf(gnum);
                    editor.putString(key, goal_str);
                    editor.commit();
                    Toast.makeText(getContext(), "Sucessfully added", Toast.LENGTH_SHORT).show();

                    //fragment reset
                    date_button.setText(getTodaysDate());
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

                } else {
                    // alert for past date input
                    new androidx.appcompat.app.AlertDialog.Builder(requireContext())
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
                // alert for missing title
                new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Missing title!")
                        .setMessage("Title field can't be empty")
                        .setCancelable(true)
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).show();
            }

        //datepickerdialog opens
        } else if (v.equals(date_button)) {
            date_picker_dialog.show();

        }
    }

    //check if picked date is in future or today
    private boolean isFuture(String end_date) {

        String start_date;
        LocalDate today = LocalDate.now();
        start_date = String.valueOf(today);

        int month_end = getMonthInt(end_date);
        char[] start = new char[start_date.length()];
        char[] end = new char[end_date.length()];
        //String convert to chararray
        for (int i = 0; i < start_date.length(); i++) {
            start[i] = start_date.charAt(i);
        }
        for (int i = 0; i < end_date.length(); i++) {
            end[i] = end_date.charAt(i);
        }
        //new Strings of chars (parts of the date)
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

    //integer month transform into String
    public String getMonthFormat(int month) {
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
