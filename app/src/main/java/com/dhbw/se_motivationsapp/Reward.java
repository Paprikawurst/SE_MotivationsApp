package com.dhbw.se_motivationsapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class Reward extends Fragment implements View.OnClickListener {
    //init
    View view;
    TextView points_text;
    Button btn_reward1;
    Button btn_reward2;
    Button btn_reward3;
    Button btn_reward4;
    Button btn_reward5;
    Button btn_reward6;
    ImageView check_reward1;
    ImageView check_reward2;
    ImageView check_reward3;
    ImageView check_reward4;
    ImageView check_reward5;
    ImageView check_reward6;
    private int points;
    private SharedPreferences spref;

    public Reward() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //read points from sPref
        spref = requireContext().getSharedPreferences("SP", 0);
        points = spref.getInt("points", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reward, container, false);

        //init
        btn_reward1 = view.findViewById(R.id.button_Reward_1);
        btn_reward2 = view.findViewById(R.id.button_Reward_2);
        btn_reward3 = view.findViewById(R.id.button_Reward_3);
        btn_reward4 = view.findViewById(R.id.button_Reward_4);
        btn_reward5 = view.findViewById(R.id.button_Reward_5);
        btn_reward6 = view.findViewById(R.id.button_Reward_6);

        //button color as set in shop, default dkgray
        btn_reward1.setBackgroundColor(spref.getInt("color", Color.DKGRAY));
        btn_reward2.setBackgroundColor(spref.getInt("color", Color.DKGRAY));
        btn_reward3.setBackgroundColor(spref.getInt("color", Color.DKGRAY));
        btn_reward4.setBackgroundColor(spref.getInt("color", Color.DKGRAY));
        btn_reward5.setBackgroundColor(spref.getInt("color", Color.DKGRAY));
        btn_reward6.setBackgroundColor(spref.getInt("color", Color.DKGRAY));

        //check gone on first starts
        check_reward1 = view.findViewById(R.id.check_Reward1);
        check_reward1.setVisibility(View.GONE);
        check_reward2 = view.findViewById(R.id.check_Reward2);
        check_reward2.setVisibility(View.GONE);
        check_reward3 = view.findViewById(R.id.check_Reward3);
        check_reward3.setVisibility(View.GONE);
        check_reward4 = view.findViewById(R.id.check_Reward4);
        check_reward4.setVisibility(View.GONE);
        check_reward5 = view.findViewById(R.id.check_Reward5);
        check_reward5.setVisibility(View.GONE);
        check_reward6 = view.findViewById(R.id.check_Reward6);
        check_reward6.setVisibility(View.GONE);

        //Set EventListener for Buttons
        btn_reward1.setOnClickListener(this);
        btn_reward2.setOnClickListener(this);
        btn_reward3.setOnClickListener(this);
        btn_reward4.setOnClickListener(this);
        btn_reward5.setOnClickListener(this);
        btn_reward6.setOnClickListener(this);
        filterRedeemed();

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    //get id of clicked button as string and set points for successful reward completion
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_Reward_1:
                onRedeemReward(10, "Reward1");
                break;
            case R.id.button_Reward_2:
                onRedeemReward(20, "Reward2");
                break;
            case R.id.button_Reward_3:
                onRedeemReward(30, "Reward3");
                break;
            case R.id.button_Reward_4:
                onRedeemReward(40, "Reward4");
                break;
            case R.id.button_Reward_5:
                onRedeemReward(50, "Reward5");
                break;
            case R.id.button_Reward_6:
                onRedeemReward(60, "Reward6");
                break;
            default:
                break;
        }
    }

    //redeem and update points after check if reward can be redeemed
    public void onRedeemReward(int reward_points, String reward_name) {
        SharedPreferences.Editor editor = spref.edit();
        boolean can_be_redeemed = false;
        int total_goals;

        //Reward1 Logic
        if (reward_name.equals("Reward1")) {
            can_be_redeemed = true;
        }

        //Reward2 Logic
        else if (reward_name.equals("Reward2")) {
            total_goals = spref.getInt("easyGoals", 0) + spref.getInt("mediumGoals", 0) + spref.getInt("hardGoals", 0);
            if (total_goals >= 1) {
                can_be_redeemed = true;
            } else {
                Toast.makeText(getContext(), "You need to complete one goal to redeem this reward!", Toast.LENGTH_SHORT).show();
            }
        }

        //Reward3 Logic
        else if (reward_name.equals("Reward3")) {
            total_goals = spref.getInt("easyGoals", 0);
            if (total_goals >= 5) {
                can_be_redeemed = true;

            } else {
                Toast.makeText(getContext(), "You need to complete " + (5 - total_goals) + " more easy goals to redeem this reward!", Toast.LENGTH_SHORT).show();
            }
        }

        //Reward4 Logic
        else if (reward_name.equals("Reward4")) {
            total_goals = spref.getInt("mediumGoals", 0);
            if (total_goals >= 3) {
                can_be_redeemed = true;

            } else {
                Toast.makeText(getContext(), "You need to complete " + (3 - total_goals) + " more medium goals to redeem this reward!", Toast.LENGTH_SHORT).show();
            }
        }

        //Reward5 Logic
        else if (reward_name.equals("Reward5")) {
            total_goals = spref.getInt("hardGoals", 0);
            if (total_goals >= 1) {
                can_be_redeemed = true;

            } else {
                Toast.makeText(getContext(), "You need to complete one hard goal to redeem this reward!", Toast.LENGTH_SHORT).show();
            }
        }

        //Reward6 Logic
        else if (reward_name.equals("Reward6")) {
            total_goals = spref.getInt("easyGoals", 0) + spref.getInt("mediumGoals", 0) + spref.getInt("hardGoals", 0);
            if (total_goals >= 10) {
                can_be_redeemed = true;
            } else {
                Toast.makeText(getContext(), "You need to complete " + (10 - total_goals) + " more goals to redeem this reward!", Toast.LENGTH_SHORT).show();
            }

        }

        //check if reward can be redeemed and if yes do logic behind
        if (can_be_redeemed) {
            points = points + reward_points;
            reward_name = reward_name + "redeemed";
            editor.putInt("points", points);
            editor.putBoolean(reward_name, true);
            Toast.makeText(getContext(), "You just got " + reward_points + " points!", Toast.LENGTH_SHORT).show();
            editor.commit();
            View view_toolbar = getActivity().findViewById(R.id.toolbar);
            points_text = view_toolbar.findViewById(R.id.points);
            points_text.setText(String.valueOf(points));
            filterRedeemed();
        }
    }

    //check which reward has been redeemed and show button and check accordingly
    public void filterRedeemed() {
        if (spref.getBoolean("Reward1redeemed", false)) {
            btn_reward1.setVisibility(View.GONE);
            check_reward1.setVisibility(View.VISIBLE);
        }

        if (spref.getBoolean("Reward2redeemed", false)) {
            btn_reward2.setVisibility(View.GONE);
            check_reward2.setVisibility(View.VISIBLE);
        }

        if (spref.getBoolean("Reward3redeemed", false)) {
            btn_reward3.setVisibility(View.GONE);
            check_reward3.setVisibility(View.VISIBLE);
        }

        if (spref.getBoolean("Reward4redeemed", false)) {
            btn_reward4.setVisibility(View.GONE);
            check_reward4.setVisibility(View.VISIBLE);
        }

        if (spref.getBoolean("Reward5redeemed", false)) {
            btn_reward5.setVisibility(View.GONE);
            check_reward5.setVisibility(View.VISIBLE);
        }

        if (spref.getBoolean("Reward6redeemed", false)) {
            btn_reward6.setVisibility(View.GONE);
            check_reward6.setVisibility(View.VISIBLE);
        }
    }
}
