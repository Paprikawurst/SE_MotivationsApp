package com.dhbw.se_motivationsapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Reward extends Fragment implements View.OnClickListener
{
    //initialisierung
    View view;
    private int points;
    private SharedPreferences spref;
    TextView points_text;
    ProgressBar progressBarReward1;
    ProgressBar progressBarReward2;


    public Reward()
    {
        // Required empty public constructor
    }

    public static Reward newInstance() {
        Reward fragment = new Reward();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SP auslesen und Punkte auslesen - wenn nicht vorhanden dann Punkte = 0
        spref = getContext().getSharedPreferences("SP", 0);
        points = spref.getInt("points", 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reward, container, false);

        //init
        Button btnReward1 = view.findViewById(R.id.button_Reward_1);
        Button btnReward2 = view.findViewById(R.id.button_Reward_2);

        progressBarReward1 = view.findViewById(R.id.progressBar_Reward1);
        progressBarReward2 = view.findViewById(R.id.progressBar_Reward2);

        //Set EventListener for Buttons
        btnReward1.setOnClickListener(this);
        btnReward2.setOnClickListener(this);
        filterRedeemed();

        return view;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    //onClick von Button x schaut welcher gedrückt wurde und gibt Namen weiter
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_Reward_1:
                onRedeemReward(10, "Reward1");
                break;
            case R.id.button_Reward_2:
                onRedeemReward(20, "Reward2");
                break;
            default:
                break;
        }
    }

    //Punkte gutschreiben + Punkte updaten
    public void onRedeemReward(int rewardPoints, String rewardName)
    {
        SharedPreferences.Editor editor = spref.edit();
        points = points + rewardPoints;
        editor.putInt("points", points);
        editor.commit();
        Toast.makeText(getContext() , "You just got " + rewardPoints + " points!", Toast.LENGTH_SHORT).show();
        editor.putBoolean(rewardName ,true);

        View view_toolbar = getActivity().findViewById(R.id.toolbar);
        points_text = view_toolbar.findViewById(R.id.points);
        points_text.setText(String.valueOf(points));

        //Ausblenden von Progressbar
        if(rewardName.equals("Reward1"))
        {
            progressBarReward1.setVisibility(View.GONE);
            editor.putBoolean("Reward1redeemed",true);
            editor.commit();
        }
        else if(rewardName.equals("Reward2"))
        {
            progressBarReward2.setVisibility(View.GONE);
            editor.putBoolean("Reward2redeemed",true);
            editor.commit();
        }
    }

    //Bei Viewaufruf schauen welche Rewards schon eingelöst wurden - dort dann Progressbar ausblenden
    public void filterRedeemed()
    {
        if(spref.getBoolean("Reward1redeemed", false))
        {
            progressBarReward1.setVisibility(View.GONE);
        }

        if(spref.getBoolean("Reward2redeemed", false))
        {
            progressBarReward2.setVisibility(View.GONE);
        }
    }

}
