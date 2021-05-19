package com.dhbw.se_motivationsapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;


import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class AddGoal extends Fragment implements View.OnClickListener {
    private EditText title;
    private EditText description;
    private EditText end_date;
    private CheckBox notification;
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    private ImageButton addbtn;


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
        description = view.findViewById(R.id.DescriptionInputId);
        end_date = view.findViewById(R.id.endDateInputId);
        notification = view.findViewById(R.id.NotifcationId);
        easy = view.findViewById(R.id.radioButtoneasy);
        medium = view.findViewById(R.id.radioButtonmedium);
        hard = view.findViewById(R.id.radioButtonhard);
        addbtn = view.findViewById(R.id.addbtn);
        addbtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == addbtn) {
            Date end = (Date) end_date.getText();
            int dif = 0;
            if (easy.isChecked()) {
                dif = 1;
            } else if (medium.isChecked()) {
                dif = 2;
            } else if (hard.isChecked()) {
                dif = 3;
            }
            ArrayList<String> subgoals = new ArrayList<>();
            LocalDate start_date = LocalDate.now();
            String t = String.valueOf(title.getText());


            Goal newGoal = new Goal(t, String.valueOf(description.getText()), end, notification.isChecked(),
                    dif, subgoals, start_date);
        }
    }
}
