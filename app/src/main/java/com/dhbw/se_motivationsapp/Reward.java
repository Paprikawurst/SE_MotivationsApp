package com.dhbw.se_motivationsapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

    Button btnReward1;
    Button btnReward2;
    Button btnReward3;
    Button btnReward4;
    Button btnReward5;
    Button btnReward6;
    ProgressBar progressBarReward1;
    ProgressBar progressBarReward2;
    ProgressBar progressBarReward3;
    ProgressBar progressBarReward4;
    ProgressBar progressBarReward5;
    ProgressBar progressBarReward6;
    ImageView checkReward1;
    ImageView checkReward2;
    ImageView checkReward3;
    ImageView checkReward4;
    ImageView checkReward5;
    ImageView checkReward6;

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
        btnReward1 = view.findViewById(R.id.button_Reward_1);
        btnReward2 = view.findViewById(R.id.button_Reward_2);
        btnReward3 = view.findViewById(R.id.button_Reward_3);
        btnReward4 = view.findViewById(R.id.button_Reward_4);
        btnReward5 = view.findViewById(R.id.button_Reward_5);
        btnReward6 = view.findViewById(R.id.button_Reward_6);

        progressBarReward1 = view.findViewById(R.id.progressBar_Reward1);
        progressBarReward2 = view.findViewById(R.id.progressBar_Reward2);
        progressBarReward3 = view.findViewById(R.id.progressBar_Reward3);
        progressBarReward4 = view.findViewById(R.id.progressBar_Reward4);
        progressBarReward5 = view.findViewById(R.id.progressBar_Reward5);
        progressBarReward6 = view.findViewById(R.id.progressBar_Reward6);

        checkReward1 = view.findViewById(R.id.check_Reward1);
        checkReward1.setVisibility(View.GONE);
        checkReward2 = view.findViewById(R.id.check_Reward2);
        checkReward2.setVisibility(View.GONE);
        checkReward3 = view.findViewById(R.id.check_Reward3);
        checkReward3.setVisibility(View.GONE);
        checkReward4 = view.findViewById(R.id.check_Reward4);
        checkReward4.setVisibility(View.GONE);
        checkReward5 = view.findViewById(R.id.check_Reward5);
        checkReward5.setVisibility(View.GONE);
        checkReward6 = view.findViewById(R.id.check_Reward6);
        checkReward6.setVisibility(View.GONE);

        //Set EventListener for Buttons
        btnReward1.setOnClickListener(this);
        btnReward2.setOnClickListener(this);
        btnReward3.setOnClickListener(this);
        btnReward4.setOnClickListener(this);
        btnReward5.setOnClickListener(this);
        btnReward6.setOnClickListener(this);
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

        // schreiben in die SP und dann reloaden TODO: das geht auch generisch mit "Reward1" + "redeemed" direkt in die funktion eingeben oder vorher als string konkat.
        switch (rewardName) {
            case "Reward1":
                editor.putBoolean("Reward1redeemed", true);
                break;
            case "Reward2":
                editor.putBoolean("Reward2redeemed", true);
                break;
            case "Reward3":
                editor.putBoolean("Reward3redeemed", true);
                break;
            case "Reward4":
                editor.putBoolean("Reward4redeemed", true);
                break;
            case "Reward5":
                editor.putBoolean("Reward5redeemed", true);
                break;
            case "Reward6":
                editor.putBoolean("Reward6redeemed", true);
                break;

        }
        editor.commit();
        filterRedeemed();
    }

    //Bei Viewaufruf schauen welche Rewards schon eingelöst wurden - dort dann Progressbar ausblenden
    public void filterRedeemed()
    {
        if(spref.getBoolean("Reward1redeemed", false))
        {
            btnReward1.setVisibility(View.GONE);
            progressBarReward1.setVisibility(View.GONE);
            checkReward1.setVisibility(View.VISIBLE);
        }

        if(spref.getBoolean("Reward2redeemed", false))
        {
            btnReward2.setVisibility(View.GONE);
            progressBarReward2.setVisibility(View.GONE);
            checkReward2.setVisibility(View.VISIBLE);
        }

        if(spref.getBoolean("Reward3redeemed", false))
        {
            btnReward3.setVisibility(View.GONE);
            progressBarReward3.setVisibility(View.GONE);
            checkReward3.setVisibility(View.VISIBLE);
        }

        if(spref.getBoolean("Reward4redeemed", false))
        {
            btnReward4.setVisibility(View.GONE);
            progressBarReward4.setVisibility(View.GONE);
            checkReward4.setVisibility(View.VISIBLE);
        }

        if(spref.getBoolean("Reward5redeemed", false))
        {
            btnReward5.setVisibility(View.GONE);
            progressBarReward5.setVisibility(View.GONE);
            checkReward5.setVisibility(View.VISIBLE);
        }

        if(spref.getBoolean("Reward6redeemed", false))
        {
            btnReward6.setVisibility(View.GONE);
            progressBarReward6.setVisibility(View.GONE);
            checkReward6.setVisibility(View.VISIBLE);
        }
    }
}
