package com.dhbw.se_motivationsapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Shop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Shop extends Fragment {

    // TODO: Rename and change types of parameters

    private int points;
    private SharedPreferences spref;

    public Shop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Shop.
     */
    // TODO: Rename and change types and number of parameters
    public static Shop newInstance(String param1, String param2) {
        Shop fragment = new Shop();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        spref = getContext().getSharedPreferences("SP",0);
        points = spref.getInt("points", 0);




    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CheckBox checkbox_red;
        CheckBox checkbox_blue;
        CheckBox checkbox_black;
        CheckBox checkbox_green;
        CheckBox checkbox_gold;
        CheckBox checkbox_orange;
        CheckBox checkbox_yellow;
        CheckBox checkbox_gray;
        view = inflater.inflate(R.layout.fragment_shop, container, false);

        checkbox_red = view.findViewById(R.id.checkBox_red);
        checkbox_blue = view.findViewById(R.id.checkBox_blue);
        checkbox_black = view.findViewById(R.id.checkBox_black);
        checkbox_green = view.findViewById(R.id.checkBox_green);
        checkbox_gold = view.findViewById(R.id.checkBox_gold);
        checkbox_orange = view.findViewById(R.id.checkBox_orange);
        checkbox_yellow = view.findViewById(R.id.checkBox_yellow);
        checkbox_gray = view.findViewById(R.id.checkBox_gray);

        checkbox_red.setEnabled(false);
        checkbox_blue.setEnabled(false);
        checkbox_black.setEnabled(false);
        checkbox_green.setEnabled(false);
        checkbox_gold.setEnabled(false);
        checkbox_orange.setEnabled(false);
        checkbox_yellow.setEnabled(false);
        checkbox_gray.setEnabled(false);


        // Inflate the layout for this fragment
        return view;
    }
}