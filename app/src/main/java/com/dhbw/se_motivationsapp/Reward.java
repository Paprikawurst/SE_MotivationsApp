package com.dhbw.se_motivationsapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Reward extends Fragment implements View.OnClickListener
{
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

        spref = getContext().getSharedPreferences("SP", 0);
        points = spref.getInt("points", 0);

    }
    //TODO: visibility bei progress bar weg wenn schon eingelöst
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reward, container, false);

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

        if(rewardName.equals("Reward1"))
        {
            progressBarReward1.setVisibility(View.GONE);
        }
        else if(rewardName.equals("Reward2"))
        {
            progressBarReward2.setVisibility(View.GONE);
        }
    }

    public void filterRedeemed()
    {

        progressBarReward1.setEnabled(spref.getBoolean("purchased_red",false));

        if(spref.getBoolean("Reward1", false))
        {
            progressBarReward1.setVisibility(View.GONE);
        }
        else if(spref.getBoolean("Reward2", false))
        {
            progressBarReward2.setVisibility(View.GONE);
        }


    }

}
