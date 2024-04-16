package com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan;

import java.util.List;

public class PlanDetail {
    private String id;
    private List<DayDetail> listDay;

    // Thêm các thuộc tính cho các ngày khác nếu cần

    public PlanDetail() {
        // Constructor mặc định cần thiết cho Firebase
    }

    public PlanDetail(String id, List<DayDetail> listDay) {
        this.id = id;
        this.listDay = listDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DayDetail> getListDay() {
        return listDay;
    }

    public void setListDay(List<DayDetail> listDay) {
        this.listDay = listDay;
    }
}

