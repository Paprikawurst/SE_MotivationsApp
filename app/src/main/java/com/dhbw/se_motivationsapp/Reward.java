package com.dhbw.se_motivationsapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Reward extends Fragment implements View.OnClickListener
{
    View view;

    public Reward()
    {
        // Required empty public constructor
    }
    //TODO: was ist das
    public static Reward newInstance() {
        Reward fragment = new Reward();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: adi fragen wegen nullpointerexception
        SharedPreferences spref = getContext().getSharedPreferences("SP", 0);
        int points = spref.getInt("points", 0);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //TODO: ieine logik hier hin
            case R.id.button_Reward1:
                //logic
                break;
            case R.id.button_Reward2:
                //logic
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reward, container, false);

       Button btnReward1 = view.findViewById(R.id.button_Reward1);
        Button btnReward2 = view.findViewById(R.id.button_Reward2);

        //Set EventListener for Buttons
        btnReward1.setOnClickListener(this);
        btnReward2.setOnClickListener(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reward, container, false);
    }


}