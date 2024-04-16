package com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan;

import java.io.Serializable;

public class Plan implements Serializable {

    private String id;
    private String name;
    private String description;
    private String duration;
    private String path_image;
    private String plan_detail_id;

    public Plan(){

    }
    public Plan(String id, String name, String description, String duration, String path_image, String plan_detail_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.path_image = path_image;
        this.plan_detail_id = plan_detail_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String plan_descreption) {
        this.description = plan_descreption;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPath_image() {
        return path_image;
    }

    public void setPath_image(String path_image) {
        this.path_image = path_image;
    }

    public String getPlan_detail_id() {
        return plan_detail_id;
    }

    public void setPlan_detail_id(String plan_detail_id) {
        this.plan_detail_id = plan_detail_id;
    }
}
