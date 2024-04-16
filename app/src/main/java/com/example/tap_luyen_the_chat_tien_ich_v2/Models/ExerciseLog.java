package com.example.tap_luyen_the_chat_tien_ich_v2.Models;

public class ExerciseLog {
    private double caloBurned;
    private String dateTime;
    private String dayName;
    private String duration;
    private int numberOfExercise;
    private String userID;
    private String imgPlan;

    public ExerciseLog(){}

    public ExerciseLog(double caloBurned, String dateTime,String dayName, String duration, int numberOfExercise, String userID, String imgPlan) {
        this.caloBurned = caloBurned;
        this.dateTime = dateTime;
        this.dayName = dayName;
        this.duration = duration;
        this.numberOfExercise = numberOfExercise;
        this.userID = userID;
        this.imgPlan = imgPlan;
    }

    public double getCaloBurned() {
        return caloBurned;
    }

    public void setCaloBurned(double caloBurned) {
        this.caloBurned = caloBurned;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getNumberOfExercise() {
        return numberOfExercise;
    }

    public void setNumberOfExercise(int numberOfExercise) {
        this.numberOfExercise = numberOfExercise;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImgPlan() {
        return imgPlan;
    }

    public void setImgPlan(String imgPlan) {
        this.imgPlan = imgPlan;
    }
}
