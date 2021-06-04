package com.dhbw.se_motivationsapp;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "title",
        "description",
        "end_date",
        "notification",
        "difficulty",
        "sub_goals"
})
public class Goal
{

    //init
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("end_date")
    private String end_date;
    @JsonProperty("notification")
    private boolean notification;
    @JsonProperty("difficulty")
    private int difficulty;
    @JsonProperty("subgoals")
    private ArrayList<String> subgoals;



    public Goal() {
        this.title = "Test";
        this.description = "";
        this.end_date = "22.11.1999";
        this.notification = false;
        this.difficulty = 0;

        this.subgoals = null;
    }

    public Goal(String title, String description, String end_date, boolean notification, int difficulty,
                ArrayList<String> subgoals) {
        this.title = title;
        this.description = description;
        this.end_date = end_date;
        this.notification = notification;
        this.difficulty = difficulty;
        this.subgoals = subgoals;
    }


    //getter setter
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("end_date")
    public String getEnd_date() {
        return end_date;
    }

    @JsonProperty("end_date")
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @JsonProperty("notification")
    public boolean isNotification() {
        return notification;
    }

    @JsonProperty("notification")
    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    @JsonProperty("difficulty")
    public int getDifficulty() {
        return difficulty;
    }

    @JsonProperty("difficulty")
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }


    @JsonProperty("subgoals")
    public ArrayList<String> getSubgoals() {
        return subgoals;
    }

    @JsonProperty("subgoals")
    public void setSubgoals(ArrayList<String> subgoals) {
        this.subgoals = subgoals;
    }


}


