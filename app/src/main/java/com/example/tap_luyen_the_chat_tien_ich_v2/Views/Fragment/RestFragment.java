package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities.ExerciseDetailActivity;

import java.util.Locale;


public class RestFragment extends Fragment {


    private TextView txtCountdown;
    private Button btnExtendRest;
    private Button btnEndRest;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 20000; // Thời gian nghỉ ngơi mặc định: 20 giây
    private boolean timerRunning;

    // Hàm khởi tạo CountDownTimer
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                // Khi kết thúc thời gian nghỉ ngơi, tự động chuyển sang fragment bài tập tiếp theo
                // Gọi phương thức để chuyển sang fragment bài tập tiếp theo
                showNextExerciseFragment();
            }
        }.start();

        timerRunning = true;
    }

    // Hàm cập nhật hiển thị đồng hồ đếm ngược
    private void updateCountdownText() {
        int seconds = (int) (timeLeftInMillis / 1000);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
        txtCountdown.setText(timeLeftFormatted);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rest, container, false);

        txtCountdown = view.findViewById(R.id.txtCountdown);
        btnExtendRest = view.findViewById(R.id.btnExtendRest);
        btnEndRest = view.findViewById(R.id.btnEndRest);

        updateCountdownText();
        startTimer(); // Bắt đầu đếm ngược khi fragment được tạo

        btnExtendRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi ấn nút nghỉ thêm, reset lại thời gian nghỉ ngơi
                timeLeftInMillis += 20000; // Thêm 20s vào thời gian nghỉ ngơi
                updateTimer();
                updateCountdownText();
            }
        });

        btnEndRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                showNextExerciseFragment();
            }
        });

        return view;
    }

    private void updateTimer() {
        countDownTimer.cancel(); // Hủy đối tượng CountDownTimer hiện tại
        startTimer(); // Bắt đầu lại đếm ngược với thời gian mới
    }
    // Phương thức chuyển sang fragment bài tập tiếp theo
    private void showNextExerciseFragment() {
        if (getActivity() instanceof ExerciseDetailActivity) {
            ExerciseDetailActivity activity = (ExerciseDetailActivity) getActivity();
            activity.showNextExerciseFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            transaction.remove(this); // Loại bỏ fragment hiện tại
            transaction.commit();
        }
    }
}