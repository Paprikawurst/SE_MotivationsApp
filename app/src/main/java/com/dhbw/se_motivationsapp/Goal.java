package com.dhbw.se_motivationsapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Goal {
    private String title;
    private String description;
    private Date end_date;
    private LocalDate start_date;
    private boolean notification;
    private int difficulty;
    private ArrayList<String> subgoals;

    public Goal(String title, String description, Date end_date, boolean notification, int difficulty,
                ArrayList<String> subgoals, LocalDate start_date) {
        this.title = title;
        this.description = description;
        this.end_date = end_date;
        this.notification = notification;
        this.difficulty = difficulty;
        this.start_date = start_date;
        this.subgoals = subgoals;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
