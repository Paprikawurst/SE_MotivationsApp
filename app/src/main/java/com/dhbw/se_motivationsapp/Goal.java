package com.dhbw.se_motivationsapp;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "title",
            "description",
            "end_date",
            "notification",
            "difficulty",
            "sub_goals",
            "start_date"
    })
    public class Goal {
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
        @JsonProperty("start_date")
        private LocalDate start_date;

        public Goal(String title, String description, String end_date, boolean notification, int difficulty,
                    ArrayList<String> subgoals, LocalDate start_date) {
            this.title = title;
            this.description = description;
            this.end_date = end_date;
            this.notification = notification;
            this.difficulty = difficulty;
            this.start_date = start_date;
            this.subgoals = subgoals;
        }
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

        @JsonProperty("start_date")
        public LocalDate getStart_date() {
            return start_date;
        }

        @JsonProperty("start_date")
        public void setStart_date(LocalDate start_date) {
            this.start_date = start_date;
        }

        @JsonProperty("subgoals")
        public ArrayList<String> getSubgoals() {
            return subgoals;
        }

        @JsonProperty("subgoals")
        public void setSubgoals(ArrayList<String> subgoals) {
            this.subgoals = subgoals;
        }

    public void jsonToObject() {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            String GoalJsonStr = "{\"title\":\"Test\",\"description\":\"Test1\",\"end_date\":\"Test2\",\"notification\":\"Test4\",\"difficulty\":\"Test5\",\"sub_goals\":\"Test6\",\"start_date\":\"Test7\",}";
            //Person person = mapper.readValue(new File(getFilesDir(), "person.json"), Person.class);    // read from file
            Goal Goal = mapper.readValue(GoalJsonStr, Goal.class);// read from json string
            System.out.println("json string -> object\n" + Goal.getTitle() + " " + Goal.getDescription() + " " + Goal.getEnd_date() + " " + Goal.getDifficulty() + " " + Goal.getStart_date() + " " + Goal.getSubgoals());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }
        public void objectToJson(Goal goal) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                //mapper.writeValue(new File(getFilesDir(), "person.json"), person);  // write to file
                String jsonStr = mapper.writeValueAsString(goal);                   // write to string
                System.out.println("object -> json string\n" + jsonStr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }


