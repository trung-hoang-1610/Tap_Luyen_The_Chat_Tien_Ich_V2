package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities;

import android.content.Context;
import android.content.SharedPreferences;

public class Tempt {
    private static final String PREF_NAME = "completed_day_pref";

    public static int getCompletedDay(Context context, String planId) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getInt(planId, 0); // Return 0 if not found
    }

    public static void setCompletedDay(Context context, String planId) {
        int currentCompletedDay = getCompletedDay(context, planId);
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(planId, currentCompletedDay + 1); // Tăng giá trị lên 1
        editor.apply();
    }
    public static boolean isDayCompleted(Context context, String planId) {
        int currentCompletedDay = getCompletedDay(context, planId);
        // Kiểm tra xem ngày tập đã hoàn thành chưa
        return currentCompletedDay >= 1;
    }
}
