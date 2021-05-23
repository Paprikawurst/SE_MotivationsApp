package com.dhbw.se_motivationsapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

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

        Button button_red;
        Button button_blue;
        Button button_black;
        Button button_green;
        Button button_gold;
        Button button_orange;
        Button button_yellow;
        Button button_gray;

        button_red = view.findViewById(R.id.id_button_red);
        button_blue = view.findViewById(R.id.id_button_blue);
        button_black = view.findViewById(R.id.id_button_black);
        button_green = view.findViewById(R.id.id_button_green);
        button_gold = view.findViewById(R.id.id_button_gold);
        button_orange = view.findViewById(R.id.id_button_orange);
        button_yellow = view.findViewById(R.id.id_button_yellow);
        button_gray = view.findViewById(R.id.id_button_gray);

        button_red.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              getButtonEvent(checkbox_red);
                                          }
                                      }
        );

        button_blue.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              getButtonEvent(checkbox_blue);
                                          }
                                      }
        );

        button_black.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              getButtonEvent(checkbox_black);
                                          }
                                      }
        );

        button_green.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              getButtonEvent(checkbox_green);
                                          }
                                      }
        );

        button_gold.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              getButtonEvent(checkbox_gold);
                                          }
                                      }
        );

        button_orange.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              getButtonEvent(checkbox_orange);
                                          }
                                      }
        );

        button_yellow.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              getButtonEvent(checkbox_yellow);
                                          }
                                      }
        );

        button_gray.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              getButtonEvent(checkbox_gray);
                                          }
                                      }
        );

        // Inflate the layout for this fragment
        return view;
    }

    public void getButtonEvent(CheckBox box){

            if (points >= 10 && !box.isEnabled()) {
                box.setEnabled(true);
                SharedPreferences.Editor editor = spref.edit();
                points = points - 10;
                editor.putInt("points", points);
                editor.commit();

                TextView points_text;

                View test = getActivity().findViewById(R.id.toolbar);
                points_text = test.findViewById(R.id.points);
                points_text.setText(String.valueOf(points));
            }else{
                if(points < 10) {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Not enough points")
                            .setMessage("You dont have enough points to purchase this skin")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }
                if(box.isEnabled()) {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Already bought")
                            .setMessage("You already bought this skin")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }
            }

    }
}


