package com.dhbw.se_motivationsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.graphics.drawable.GradientDrawable;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;


public class Home extends Fragment implements View.OnClickListener {


    //declaration

    private SharedPreferences sp;
    public static int id;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //read SharedPrefernce
        sp = view.getContext().getSharedPreferences("SP", 0);
        int gnum = sp.getInt("goalnumber", 0);
        // set margins
        LinearLayout linearLayout_wrapper = view.findViewById(R.id.linearLayout);
        LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //loop through all goals saved in SP and create a button for each of them with the title as button text
        for (int i = 0; i < gnum; i++) {
            int c = i + 1;
            String goalstr;
            String key = "goal" + c;

            goalstr = sp.getString(key, null);

            Goal goal = jsonToObject(goalstr);

            String title = goal.getTitle();
            String btnid = "button" + c;
            String enddate = goal.getEnd_date();

            Button btn = new Button(view.getContext());
            btn.setText(title);
            btn.setId(c);

            ImageButton img_button = new ImageButton(getContext());
            img_button.setId(c);
            img_button.setImageResource(R.drawable.baseline_more_vert_24);

            ConstraintLayout btn_layout = new ConstraintLayout(requireContext());
            ConstraintLayout.LayoutParams params_btn_layout_wrapper = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params_btn_layout_wrapper.setMargins(10, 0, 10, 0);
            btn_layout.setLayoutParams(params_btn_layout_wrapper);


            ConstraintLayout.LayoutParams params_btn_layout = new ConstraintLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params_btn_layout.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            params_btn_layout.endToStart = img_button.getId();
            params_btn_layout.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params_btn_layout.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            params_btn_layout.setMargins(0, 0, 0, 0);
            btn.setLayoutParams(params_btn_layout);

            ConstraintLayout.LayoutParams params_imgbtn_layout = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 180);
            params_imgbtn_layout.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            params_imgbtn_layout.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params_imgbtn_layout.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            params_imgbtn_layout.setMargins(0, 0, 0, 0);

            img_button.setLayoutParams(params_imgbtn_layout);

            // round corners and color
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(getButtonColor(enddate));
            gd.setCornerRadius(15);
            gd.setStroke(2, Color.WHITE);
            btn.setBackground(gd);

            btn_layout.addView(btn);
            btn_layout.addView(img_button);

            linearLayout_wrapper.addView(btn_layout, params_btn_layout_wrapper);
            img_button.setOnClickListener(this);

        }
        return view;
    }

    //JSON String transformed to a goal object
    public static Goal jsonToObject(String goalJson) {
        final ObjectMapper mapper = new ObjectMapper();
        try {

            Goal goal = mapper.readValue(goalJson, Goal.class);// read from json string
            //String kein Json
            return goal;

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return null;
    }

    //returns button color dependent on the time to deadline
    private int getButtonColor(String enddate) {
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

        //check if day consist of one or two digits
        if (Character.isDigit(end[1])) {
            endday = String.copyValueOf(end, 0, 2);
            endd = Integer.parseInt(endday);
        } else {
            endday = String.copyValueOf(end, 0, 1);
            endd = Integer.parseInt(endday);
        }


        if (y == endy && month_end - m <= 1) {
            if (getDayDif(endd, d, month_end, m) <= 1) {
                return Color.rgb(247, 70, 0);
            } else if (getDayDif(endd, d, month_end, m) <= 3) {
                return Color.rgb(255, 185, 46);
            } else if (getDayDif(endd, d, month_end, m) <= 7) {
                return Color.rgb(250, 250, 25);
            } else if (getDayDif(endd, d, month_end, m) <= 14) {
                return Color.rgb(0, 200, 60);
            } else {
                return Color.rgb(150, 150, 150);
            }
        } else {
            return Color.rgb(150, 150, 150);
        }
    }

    //helper method for getButtonColor to calculate the days between now and deadline day
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

    @Override
    public void onClick(View view) {
        //set id to the id of the button which is clicked
        id = view.getId();
        //starts new activity DetailsGoal
        Intent intent = new Intent(getActivity(), DetailsGoal.class);
        startActivity(intent);
        getActivity().finish();


    }

    public static int getIds() {
        return id;
    }
}