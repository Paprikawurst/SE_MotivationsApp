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


public class Shop extends Fragment {

    private int points;
    private SharedPreferences spref;

    public Shop() {
        System.out.println("test1");
    }


    public static Shop newInstance(String param1, String param2) {
        Shop fragment = new Shop();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spref = getContext().getSharedPreferences("SP", 0);
        points = spref.getInt("points", 0);

    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view_toolbar = requireActivity().findViewById(R.id.toolbar);
        view_toolbar.setBackgroundColor(Color.DKGRAY);

        view = inflater.inflate(R.layout.fragment_shop, container, false);
        changeColor(Color.DKGRAY);
        check_Checkbox();

        Button button_red = view.findViewById(R.id.id_button_red);
        Button button_blue = view.findViewById(R.id.id_button_blue);
        Button button_black = view.findViewById(R.id.id_button_black);
        Button button_green = view.findViewById(R.id.id_button_green);
        ;
        Button button_cyan = view.findViewById(R.id.id_button_cyan);
        Button button_gray = view.findViewById(R.id.id_button_gray2);

        CheckBox checkbox_default = view.findViewById(R.id.checkBox_defalut);
        CheckBox checkbox_red = view.findViewById(R.id.checkBox_red);
        CheckBox checkbox_blue = view.findViewById(R.id.checkBox_blue);
        CheckBox checkbox_black = view.findViewById(R.id.checkBox_black);
        CheckBox checkbox_green = view.findViewById(R.id.checkBox_green);
        CheckBox checkbox_cyan = view.findViewById(R.id.checkBox_cyan);
        CheckBox checkbox_gray = view.findViewById(R.id.checkBox_gray);

        checkbox_red.setEnabled(spref.getBoolean("purchased_red", false));
        checkbox_blue.setEnabled(spref.getBoolean("purchased_blue", false));
        checkbox_black.setEnabled(spref.getBoolean("purchased_black", false));
        checkbox_green.setEnabled(spref.getBoolean("purchased_green", false));
        checkbox_cyan.setEnabled(spref.getBoolean("purchased_cyan", false));
        checkbox_gray.setEnabled(spref.getBoolean("purchased_gray", false));

        checkbox_default.setChecked(spref.getBoolean("activated_default", false));
        checkbox_red.setChecked(spref.getBoolean("activated_red", false));
        checkbox_blue.setChecked(spref.getBoolean("activated_blue", false));
        checkbox_black.setChecked(spref.getBoolean("activated_black", false));
        checkbox_green.setChecked(spref.getBoolean("activated_green", false));
        checkbox_cyan.setChecked(spref.getBoolean("activated_cyan", false));
        checkbox_gray.setChecked(spref.getBoolean("activated_gray", false));


        button_red.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              getButtonEvent(checkbox_red, "purchased_red",500);
                                          }
                                      }
        );

        button_blue.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               getButtonEvent(checkbox_blue, "purchased_blue",200);
                                           }
                                       }
        );

        button_black.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                getButtonEvent(checkbox_black, "purchased_black",100);
                                            }
                                        }
        );

        button_green.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                getButtonEvent(checkbox_green, "purchased_green",50);
                                            }
                                        }
        );

        button_cyan.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               getButtonEvent(checkbox_cyan, "purchased_cyan",20);
                                           }
                                       }
        );


        button_gray.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               getButtonEvent(checkbox_gray, "purchased_gray",10);
                                           }
                                       }
        );

        checkbox_default.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SelectActivated(checkbox_default);
                }else System.out.println(false);
            }
        });

        checkbox_red.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SelectActivated(checkbox_red);
                }else checkbox_default.setChecked(true);
            }
        });

        checkbox_blue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SelectActivated(checkbox_blue);
                }else checkbox_default.setChecked(true);
            }
        });

        checkbox_black.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SelectActivated(checkbox_black);
                }else checkbox_default.setChecked(true);
            }
        });

        checkbox_green.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SelectActivated(checkbox_green);
                }else checkbox_default.setChecked(true);
            }
        });

        checkbox_cyan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SelectActivated(checkbox_cyan);
                }else checkbox_default.setChecked(true);
            }
        });


        checkbox_gray.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SelectActivated(checkbox_gray);
                }else checkbox_default.setChecked(true);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public boolean getButtonEvent(CheckBox box, String purchased_skin, int cost) {

        if (points >= cost && !box.isEnabled()) {
            box.setEnabled(true);
            SharedPreferences.Editor editor = spref.edit();
            points = points - cost;
            editor.putInt("points", points);
            editor.putBoolean(purchased_skin, true);
            editor.commit();

            TextView points_text;

            View view_toolbar = getActivity().findViewById(R.id.toolbar);
            points_text = view_toolbar.findViewById(R.id.points);
            points_text.setText(String.valueOf(points));
            return true;
        } else {
            if (points < cost && !spref.getBoolean(purchased_skin,false)) {
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

    public void changeColor(int color) {

        Button shop_button_red = view.findViewById(R.id.id_button_red);
        Button shop_button_blue = view.findViewById(R.id.id_button_blue);
        Button shop_button_black = view.findViewById(R.id.id_button_black);
        Button shop_button_green = view.findViewById(R.id.id_button_green);
        Button shop_button_cyan = view.findViewById(R.id.id_button_cyan);
        Button shop_button_gray = view.findViewById(R.id.id_button_gray2);

        shop_button_red.setBackgroundColor(color);
        shop_button_blue.setBackgroundColor(color);
        shop_button_black.setBackgroundColor(color);
        shop_button_green.setBackgroundColor(color);
        shop_button_cyan.setBackgroundColor(color);
        shop_button_gray.setBackgroundColor(color);

        View view_toolbar = requireActivity().findViewById(R.id.toolbar);
        view_toolbar.setBackgroundColor(color);

    }

    public void check_Checkbox() {

        if (spref.getBoolean("activated_default", false)) {
            changeColor(Color.DKGRAY);
        }
        if (spref.getBoolean("activated_red", false)) {
            changeColor(Color.RED);
        }
        if (spref.getBoolean("activated_blue", false)) {
            changeColor(Color.BLUE);
        }
        if (spref.getBoolean("activated_black", false)) {
            changeColor(Color.BLACK);
        }
        if (spref.getBoolean("activated_green", false)) {
            changeColor(Color.GREEN);
        }
        if (spref.getBoolean("activated_cyan", false)) {
            changeColor(Color.CYAN);
        }
        if (spref.getBoolean("activated_gray", false)) {
            changeColor(Color.GRAY);
        }

    }

    public void SelectActivated(CheckBox box) {

        SharedPreferences.Editor editor = spref.edit();

        CheckBox checkbox_default = view.findViewById(R.id.checkBox_defalut);
        CheckBox checkbox_red = view.findViewById(R.id.checkBox_red);
        CheckBox checkbox_blue = view.findViewById(R.id.checkBox_blue);
        CheckBox checkbox_black = view.findViewById(R.id.checkBox_black);
        CheckBox checkbox_green = view.findViewById(R.id.checkBox_green);
        CheckBox checkbox_cyan = view.findViewById(R.id.checkBox_cyan);
        CheckBox checkbox_gray = view.findViewById(R.id.checkBox_gray);

        editor.putBoolean("activated_default", false);
        editor.putBoolean("activated_red", false);
        editor.putBoolean("activated_blue", false);
        editor.putBoolean("activated_black", false);
        editor.putBoolean("activated_green", false);
        editor.putBoolean("activated_cyan", false);
        editor.putBoolean("activated_gray", false);

        checkbox_default.setChecked(false);
        checkbox_red.setChecked(false);
        checkbox_blue.setChecked(false);
        checkbox_black.setChecked(false);
        checkbox_green.setChecked(false);
        checkbox_cyan.setChecked(false);
        checkbox_gray.setChecked(false);


        if (box == checkbox_default) {
            changeColor(Color.DKGRAY);
            editor.putBoolean("activated_default", true);
            checkbox_default.setChecked(true);
            editor.putInt("color", Color.DKGRAY);
            editor.commit();
        }
        if (box == checkbox_red) {
            changeColor(Color.RED);
            editor.putBoolean("activated_red", true);
            checkbox_red.setChecked(true);
            editor.putInt("color", Color.RED);
            editor.commit();
        }
        if (box == checkbox_blue) {
            changeColor(Color.BLUE);
            editor.putBoolean("activated_blue", true);
            checkbox_blue.setChecked(true);
            editor.putInt("color", Color.BLUE);
            editor.commit();
        }
        if (box == checkbox_black) {
            changeColor(Color.BLACK);
            editor.putBoolean("activated_black", true);
            checkbox_black.setChecked(true);
            editor.putInt("color", Color.BLACK);
            editor.commit();
        }
        if (box == checkbox_green) {
            changeColor(Color.GREEN);
            editor.putBoolean("activated_green", true);
            checkbox_green.setChecked(true);
            editor.putInt("color", Color.GREEN);
            editor.commit();
        }
        if (box == checkbox_cyan) {
            changeColor(Color.CYAN);
            editor.putBoolean("activated_cyan", true);
            checkbox_cyan.setChecked(true);
            editor.putInt("color", Color.CYAN);
            editor.commit();
        }
        if (box == checkbox_gray) {
            changeColor(Color.GRAY);
            editor.putBoolean("activated_gray", true);
            checkbox_gray.setChecked(true);
            editor.putInt("color", Color.GRAY);
            editor.commit();
        }

    }

    public boolean CheckAllCheckboxes(){
        if (!spref.getBoolean("activated_gray", false) && !spref.getBoolean("activated_red", false) &&
                !spref.getBoolean("activated_blue", false) && !spref.getBoolean("activated_black", false) &&
                !spref.getBoolean("activated_green", false) && !spref.getBoolean("activated_cyan", false) &&
                !spref.getBoolean("activated_gray", false)) {
            return true;
        }
        return false;
    }

}


