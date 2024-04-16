package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tap_luyen_the_chat_tien_ich_v2.R;

import java.text.DecimalFormat;


public class ResultFragment extends Fragment {

    private double caloriesBurned;
    private String time;
    private int numberOfExercises;
    private String dayName;
    private TextView txtTime;
    private TextView txtCaloriesBurned;
    private TextView txtNumberOfExercises;
    private TextView txtDayName;

    public static ResultFragment newInstance(double caloriesBurned, String time, int numberOfExercises, String dayName) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putDouble("caloriesBurned", caloriesBurned);
        args.putString(("time"), time);
        args.putInt(("numberOfExercises"), numberOfExercises);
        args.putString("dayName", dayName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            caloriesBurned = getArguments().getDouble("caloriesBurned", 0.0);
            time = getArguments().getString("time", "00:00");
            numberOfExercises = getArguments().getInt("numberOfExercises", 0);
            dayName = getArguments().getString("dayName", "Day name");
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        txtCaloriesBurned = view.findViewById(R.id.txtCaloriesBurned);
        txtTime = view.findViewById(R.id.txtTime);
        txtNumberOfExercises = view.findViewById(R.id.txtNumberOfExercises);
        txtDayName  = view.findViewById(R.id.txtDayName);

        DecimalFormat df = new DecimalFormat("#.##");

        txtCaloriesBurned.setText(df.format(caloriesBurned));
        txtTime.setText(time);
        txtNumberOfExercises.setText(String.valueOf(numberOfExercises));
        txtDayName.setText(dayName);
        return view;
    }
}