package com.dhbw.se_motivationsapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Shop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Shop extends Fragment {

    // TODO: Rename and change types of parameters


    private int points;
    private SharedPreferences spref;
    boolean first_create = false;

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

        first_create = true;
        spref = getContext().getSharedPreferences("SP", 0);
        points = spref.getInt("points", 0);


    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_shop, container, false);

        check_Checkbox();

        Button button_red = view.findViewById(R.id.id_button_red);
        Button button_blue = view.findViewById(R.id.id_button_blue);
        Button button_black = view.findViewById(R.id.id_button_black);
        Button button_green  = view.findViewById(R.id.id_button_green);;
        Button button_cyan = view.findViewById(R.id.id_button_cyan);
        Button button_darkgray = view.findViewById(R.id.id_button_darkgray);
        Button button_yellow = view.findViewById(R.id.id_button_yellow);
        Button button_gray = view.findViewById(R.id.id_button_gray);

        CheckBox checkbox_red = view.findViewById(R.id.checkBox_red);
        CheckBox checkbox_blue = view.findViewById(R.id.checkBox_blue);
        CheckBox checkbox_black = view.findViewById(R.id.checkBox_black);
        CheckBox checkbox_green = view.findViewById(R.id.checkBox_green);
        CheckBox checkbox_cyan = view.findViewById(R.id.checkBox_cyan);
        CheckBox checkbox_darkgray = view.findViewById(R.id.checkBox_darkgray);
        CheckBox checkbox_yellow = view.findViewById(R.id.checkBox_yellow);
        CheckBox checkbox_gray = view.findViewById(R.id.checkBox_gray);

        checkbox_red.setEnabled(spref.getBoolean("purchased_red",false));
        checkbox_blue.setEnabled(spref.getBoolean("purchased_blue",false));
        checkbox_black.setEnabled(spref.getBoolean("purchased_black",false));
        checkbox_green.setEnabled(spref.getBoolean("purchased_green",false));
        checkbox_cyan.setEnabled(spref.getBoolean("purchased_cyan",false));
        checkbox_darkgray.setEnabled(spref.getBoolean("purchased_darkgray",false));
        checkbox_yellow.setEnabled(spref.getBoolean("purchased_yellow",false));
        checkbox_gray.setEnabled(spref.getBoolean("purchased_gray",false));


        button_red.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              getButtonEvent(checkbox_red, "purchased_red");
                                          }
                                      }
        );

        button_blue.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               getButtonEvent(checkbox_blue, "purchased_blue");
                                           }
                                       }
        );

        button_black.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                getButtonEvent(checkbox_black, "purchased_black");
                                            }
                                        }
        );

        button_green.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                getButtonEvent(checkbox_green, "purchased_green");
                                            }
                                        }
        );

        button_cyan.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               getButtonEvent(checkbox_cyan, "purchased_cyan");
                                           }
                                       }
        );

        button_darkgray.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 getButtonEvent(checkbox_darkgray, "purchased_darkgray");
                                             }
                                         }
        );

        button_yellow.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 getButtonEvent(checkbox_yellow, "purchased_yellow");
                                             }
                                         }
        );

        button_gray.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               getButtonEvent(checkbox_gray, "purchased_gray");
                                           }
                                       }
        );

        checkbox_red.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    changeColor(Color.RED);
                    checkbox_blue.setChecked(false);
                    checkbox_black.setChecked(false);
                    checkbox_green.setChecked(false);
                    checkbox_cyan.setChecked(false);
                    checkbox_darkgray.setChecked(false);
                    checkbox_yellow.setChecked(false);
                    checkbox_gray.setChecked(false);
                }
            }
        });

        checkbox_blue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    changeColor(Color.BLUE);
                    checkbox_red.setChecked(false);
                    checkbox_black.setChecked(false);
                    checkbox_green.setChecked(false);
                    checkbox_cyan.setChecked(false);
                    checkbox_darkgray.setChecked(false);
                    checkbox_yellow.setChecked(false);
                    checkbox_gray.setChecked(false);
                }
            }
        });

        checkbox_black.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    changeColor(Color.BLACK);
                    checkbox_red.setChecked(false);
                    checkbox_blue.setChecked(false);
                    checkbox_green.setChecked(false);
                    checkbox_cyan.setChecked(false);
                    checkbox_darkgray.setChecked(false);
                    checkbox_yellow.setChecked(false);
                    checkbox_gray.setChecked(false);
                }
            }
        });

        checkbox_green.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    changeColor(Color.GREEN);
                    checkbox_red.setChecked(false);
                    checkbox_blue.setChecked(false);
                    checkbox_black.setChecked(false);
                    checkbox_cyan.setChecked(false);
                    checkbox_darkgray.setChecked(false);
                    checkbox_yellow.setChecked(false);
                    checkbox_gray.setChecked(false);
                }
            }
        });

        checkbox_cyan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    changeColor(Color.CYAN);
                    checkbox_red.setChecked(false);
                    checkbox_blue.setChecked(false);
                    checkbox_black.setChecked(false);
                    checkbox_green.setChecked(false);
                    checkbox_darkgray.setChecked(false);
                    checkbox_yellow.setChecked(false);
                    checkbox_gray.setChecked(false);
                }
            }
        });

        checkbox_darkgray.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    changeColor(Color.DKGRAY);
                    checkbox_red.setChecked(false);
                    checkbox_blue.setChecked(false);
                    checkbox_black.setChecked(false);
                    checkbox_green.setChecked(false);
                    checkbox_cyan.setChecked(false);
                    checkbox_yellow.setChecked(false);
                    checkbox_gray.setChecked(false);
                }
            }
        });

        checkbox_yellow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    changeColor(Color.YELLOW);
                    checkbox_red.setChecked(false);
                    checkbox_blue.setChecked(false);
                    checkbox_black.setChecked(false);
                    checkbox_green.setChecked(false);
                    checkbox_cyan.setChecked(false);
                    checkbox_darkgray.setChecked(false);
                    checkbox_gray.setChecked(false);
                }
            }
        });

        checkbox_gray.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    changeColor(Color.GRAY);
                    checkbox_red.setChecked(false);
                    checkbox_blue.setChecked(false);
                    checkbox_black.setChecked(false);
                    checkbox_green.setChecked(false);
                    checkbox_cyan.setChecked(false);
                    checkbox_darkgray.setChecked(false);
                    checkbox_yellow.setChecked(false);
                }
            }
        });




        // Inflate the layout for this fragment
        return view;
    }

    public boolean getButtonEvent(CheckBox box, String purchased_skin) {

        if (points >= 10 && !box.isEnabled()) {
            box.setEnabled(true);
            SharedPreferences.Editor editor = spref.edit();
            points = points - 10;
            editor.putInt("points", points);
            editor.putBoolean(purchased_skin, true);
            editor.commit();

            TextView points_text;

            View view_toolbar = getActivity().findViewById(R.id.toolbar);
            points_text = view_toolbar.findViewById(R.id.points);
            points_text.setText(String.valueOf(points));
            return true;
        } else {
            if (points < 10) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Not enough points")
                        .setMessage("You dont have enough points to purchase this skin")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                return false;
            }
            if (box.isEnabled()) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Already bought")
                        .setMessage("You already bought this skin")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                return false;
            }
        }

    return false;
    }

    public void changeColor(int color){

        Button shop_button_red = view.findViewById(R.id.id_button_red);
        Button shop_button_blue = view.findViewById(R.id.id_button_blue);
        Button shop_button_black = view.findViewById(R.id.id_button_black);
        Button shop_button_green  = view.findViewById(R.id.id_button_green);;
        Button shop_button_cyan = view.findViewById(R.id.id_button_cyan);
        Button shop_button_darkgray = view.findViewById(R.id.id_button_darkgray);
        Button shop_button_yellow = view.findViewById(R.id.id_button_yellow);
        Button shop_button_gray = view.findViewById(R.id.id_button_gray);

        shop_button_red.setBackgroundColor(color);
        shop_button_blue.setBackgroundColor(color);
        shop_button_black.setBackgroundColor(color);
        shop_button_green.setBackgroundColor(color);
        shop_button_cyan.setBackgroundColor(color);
        shop_button_darkgray.setBackgroundColor(color);
        shop_button_yellow.setBackgroundColor(color);
        shop_button_gray.setBackgroundColor(color);

        View view_toolbar = requireActivity().findViewById(R.id.toolbar);
        view_toolbar.setBackgroundColor(color);

    }

    public void check_Checkbox(){

        if(spref.getBoolean("purchased_red",false)){
            changeColor(Color.RED);
        }
        if(spref.getBoolean("purchased_blue",false)){
            changeColor(Color.BLUE);
        }
        if(spref.getBoolean("purchased_black",false)){
            changeColor(Color.BLACK);
        }
        if(spref.getBoolean("purchased_green",false)){
            changeColor(Color.GREEN);
        }
        if(spref.getBoolean("purchased_cyan",false)){
            changeColor(Color.CYAN);
        }
        if(spref.getBoolean("purchased_darkgray",false)){
            changeColor(Color.DKGRAY);
        }
        if(spref.getBoolean("purchased_yellow",false)){
            changeColor(Color.YELLOW);
        }
        if(spref.getBoolean("purchased_gray",false)){
            changeColor(Color.GRAY);
        }


    }


}


