package com.dhbw.se_motivationsapp;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AddGoal extends Fragment implements View.OnClickListener {
    private EditText title;
    private EditText description;
    private EditText end_date;
    private CheckBox notification;
    private RadioButton easy;
    private RadioButton medium;
    private RadioButton hard;
    private ImageButton addbtn;

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
        description = view.findViewById(R.id.DescriptionInputId);
        end_date = view.findViewById(R.id.endDateInputId);
        notification = view.findViewById(R.id.NotifcationId);
        easy = view.findViewById(R.id.radioButtoneasy);
        medium = view.findViewById(R.id.radioButtonmedium);
        hard = view.findViewById(R.id.radioButtonhard);
        addbtn = view.findViewById(R.id.addbtn);
        addbtn.setOnClickListener(this);
        sp = view.getContext().getSharedPreferences("SP", 0);

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


            SharedPreferences.Editor editor = sp.edit();
            int gnum = sp.getInt("goalnumber", 0);
            gnum++;
            editor.putInt("goalnumber", gnum);

            String key = "goal" + String.valueOf(gnum);
            Set<String> goalset = new HashSet<>();
            goalset.add(newGoal.getTitle());
            goalset.add(newGoal.getDescription());
            goalset.add(String.valueOf(newGoal.getEnd_date()));
            goalset.add(String.valueOf(newGoal.isNotification()));
            goalset.add(String.valueOf(newGoal.getDifficulty()));
            int i = 0;
            while (newGoal.getSubgoals().get(i) != null) {
                goalset.add(String.valueOf(goalset.add(String.valueOf(i))));
            }

            goalset.add(String.valueOf(newGoal.getStart_date()));

            editor.putStringSet(key, goalset);
            editor.commit();
            //Toast.makeText(getActivity(),"Sucessfully added", Toast.LENGTH_SHORT).show();


        }
    }
}
