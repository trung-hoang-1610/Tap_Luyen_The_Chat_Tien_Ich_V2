package com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan;

import java.io.Serializable;
import java.util.List;

public class DayDetail implements Serializable {
    private String id;
    private String name;
    private List<String> exerciseIds;
    private String imgPath;
    private String level;

    public DayDetail() {
        // Constructor mặc định cần thiết cho Firebase
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

    public List<String> getExerciseIds() {
        return exerciseIds;
    }

    public void setExerciseIds(List<String> exerciseIds) {
        this.exerciseIds = exerciseIds;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
