package com.example.tap_luyen_the_chat_tien_ich_v2.Models;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private String id;
    private String name;
    private String email;
    private String password;
    private String fitness_level;
    private float height;
    private float weight;
    private List<String> planIds;
    public User(){

    }

    public User(String id, String name, String email, String password, String fitness_level, float height, float weight) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.fitness_level = fitness_level;
        this.height = height;
        this.weight = weight;

    }

    public User(String userId) {
        this.id = userId;
    }

    public List<String> getPlanIds() {
        return planIds;
    }

    public void setPlanIds(List<String> planIds) {
        this.planIds = planIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String user_id) {
        this.id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFitness_level() {
        return fitness_level;
    }

    public void setFitness_level(String fitness_level) {
        this.fitness_level = fitness_level;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Exclude
    public Boolean isValidEmail(){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    @Exclude
    public Boolean isValidPassWord(){
        return !TextUtils.isEmpty(password) && password.length()>=6;
    }
}
