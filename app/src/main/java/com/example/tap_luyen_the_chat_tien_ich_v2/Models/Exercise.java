package com.example.tap_luyen_the_chat_tien_ich_v2.Models;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String exercise_id;
    private String excercise_description;
    private String excercise_name;
    private String excercise_path;

    public Exercise(){
    }
    public Exercise(String exercise_id, String excercise_description, String excercise_name, String excercise_path) {
        this.exercise_id = exercise_id;
        this.excercise_description = excercise_description;
        this.excercise_name = excercise_name;
        this.excercise_path = excercise_path;
    }

    public String getExcercise_description() {
        return excercise_description;
    }

    public void setExcercise_description(String excercise_description) {
        this.excercise_description = excercise_description;
    }

    public String getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(String exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getExcercise_name() {
        return excercise_name;
    }

    public void setExcercise_name(String excercise_name) {
        this.excercise_name = excercise_name;
    }

    public String getExcercise_path() {
        return excercise_path;
    }

    public void setExcercise_path(String excercise_path) {
        this.excercise_path = excercise_path;
    }




}
