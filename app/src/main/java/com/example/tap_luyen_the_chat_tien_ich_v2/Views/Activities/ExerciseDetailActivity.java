package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO.ExerciseDAO;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.Exercise;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.DayDetail;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment.ExerciseDetailFragment;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment.RestFragment;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment.ResultFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseDetailActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private List<Exercise> exerciseList;
    private int currentExerciseIndex = 0;
    private ExerciseDetailFragment currentFragment;
    private ImageView btnNext;
    private ImageView btnPrevious;
    private ImageView btnPauseContinue;
    private TextView txtTimer;
    boolean isPaused = false; // Biến để kiểm tra xem thời gian đang được tạm dừng hay không
    boolean isTimerRunning = false; // Biến để kiểm tra xem thời gian đã được bắt đầu chưa
    private Handler handler;
    private Runnable runnable;
    private int seconds = 0;

    private Button btnFinish;
    private String planDetailID;
    private double caloriesBurned = 0;
    private String dayName;
    private String time;
    int numberOfExercises = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnPauseContinue = findViewById(R.id.btnPauseContinue);
        btnFinish = findViewById(R.id.btnFinish);
        txtTimer = findViewById(R.id.txtTimer);
        handler = new Handler();
        exerciseList = new ArrayList<>();

        btnFinish.setVisibility(View.INVISIBLE);
        // Lấy danh sách bài tập từ Intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            DayDetail dayWorkout = (DayDetail) bundle.getSerializable("dayWorkout");
            dayName = dayWorkout.getName();
            planDetailID = bundle.getSerializable("planDetailID").toString();

            // Kiểm tra xem có thông tin về ngày tập không
            if (dayWorkout != null && dayWorkout.getExerciseIds() != null) {
                List<String> exerciseIDs = dayWorkout.getExerciseIds();
                numberOfExercises = exerciseIDs.size();
                System.out.println(exerciseIDs.size() +"FFFFFFFFFF");
                // Lặp qua từng bài tập và thêm vào danh sách
                for (String exerciseID : exerciseIDs) {
                    ExerciseDAO exerciseDAO = new ExerciseDAO();
                    exerciseDAO.getOneExercise(new ExerciseDAO.ExerciseCallback() {
                        @Override
                        public void onExerciseReceived(Exercise ex) {
                            // Cập nhật dữ liệu cho fragment
                            exerciseList.add(ex);
                            showExerciseFragment(0);

                        }
                    }, exerciseID);
                }
            }
        }


        checkPreviousButtonState();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextButtonClick();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreviousButtonClick();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    // Nếu ngày tập chưa hoàn thành, thực hiện đánh dấu là đã hoàn thành
                Tempt.setCompletedDay(ExerciseDetailActivity.this, planDetailID);


                String exerciseLogKey = myRef.child("exercise_logs").push().getKey();

                Map<String, Object> logData = new HashMap<>();
                logData.put("caloBurned", caloriesBurned);
                logData.put("duration", time);
                logData.put("dateTime", getCurrentDateTime());
                logData.put("numberOfExercise", numberOfExercises);
                logData.put("userID", LoginActivity.mUser.getId());
                logData.put("dayName", dayName);
                logData.put("imgPlan", PlanActivity.plan.getPath_image());

                myRef.child("exercise_logs").child(exerciseLogKey).updateChildren(logData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Lưu dữ liệu thành công
                                Toast.makeText(ExerciseDetailActivity.this, "Đã hoàn thành ngày tập!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Xử lý lỗi khi lưu dữ liệu
                                Toast.makeText(ExerciseDetailActivity.this, "Lưu dữ liệu thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                finish();
            }
        });

        btnPauseContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTimerRunning) {
                    startTimer();
                    isTimerRunning = true;
                } else {
                    if (isPaused) {
                        resumeTimer();
                    } else {
                        pauseTimer();
                    }
                }
            }
        });
    }

    private void onNextButtonClick() {
        pauseTimer();
        if (currentExerciseIndex < exerciseList.size() - 1) {
            showRestFragment(currentExerciseIndex);

        } else {
            btnNext.setVisibility(View.INVISIBLE);
            btnPrevious.setVisibility(View.INVISIBLE);
            btnPauseContinue.setVisibility(View.INVISIBLE);
            btnFinish.setVisibility(View.VISIBLE);
            txtTimer.setVisibility(View.INVISIBLE);
            showResultFragment();
            //btnNext.setEnabled(false);

        }
        checkNextButtonState();
        checkPreviousButtonState();
    }

    private void onPreviousButtonClick(){
        if (currentExerciseIndex > 0) {
            currentExerciseIndex--;
            showExerciseFragment(currentExerciseIndex);
        } else {
            btnPrevious.setEnabled(false);
        }
        checkPreviousButtonState();
        checkNextButtonState();
    }

    private void showExerciseFragment(int index) {
        if (index >= 0 && index < exerciseList.size()) {
            ExerciseDetailFragment fragment = new ExerciseDetailFragment();
            fragment.setExercise(exerciseList.get(index));
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_right);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            currentExerciseIndex = index;
            checkPreviousButtonState();
            checkNextButtonState();
        } else {
            // Đã đến bài tập cuối cùng, có thể thực hiện các hoạt động khác ở đây
        }
    }

    private void checkPreviousButtonState() {
        if (currentExerciseIndex == 0) {
            btnPrevious.setEnabled(false);
        } else {
            btnPrevious.setEnabled(true);
        }
    }

    private void checkNextButtonState() {
        if (currentExerciseIndex >= exerciseList.size() - 1) {

            //btnNext.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
        }
    }

    private void showRestFragment(int nextExerciseIndex) {
        this.currentExerciseIndex = nextExerciseIndex;
        RestFragment restFragment = new RestFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        transaction.replace(R.id.container_parent, restFragment);
        transaction.commit();
    }
    public void showNextExerciseFragment(){
        resumeTimer();
        if (currentExerciseIndex < exerciseList.size() - 1) {
            currentExerciseIndex++;
            showExerciseFragment(currentExerciseIndex);
        } else {
            btnNext.setEnabled(false);
        }
        checkNextButtonState();
        checkPreviousButtonState();
    }
    private void showResultFragment() {
        // Tạo một instance của Fragment kết quả
        caloriesBurned = 0.2*seconds;
        ResultFragment resultFragment = ResultFragment.newInstance(caloriesBurned, time, numberOfExercises, dayName);

        // Thực hiện việc chuyển sang Fragment kết quả bằng FragmentTransaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, resultFragment);
        transaction.commit();
    }


    private void startTimer() {
        // TODO: Bắt đầu đếm thời gian
        // Thay đổi hình ảnh của nút thành Pause
        isTimerRunning = true;
        btnPauseContinue.setImageResource(R.drawable.ic_resume);
        isPaused = false;
        runnable = new Runnable() {
            @Override
            public void run() {
                seconds++;
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                time = String.format("%02d:%02d", minutes, secs);
                txtTimer.setText(time);

                handler.postDelayed(this, 1000); // Delay 1 giây
            }
        };
        handler.post(runnable);
    }

    // Phương thức để tạm dừng đếm thời gian
    private void pauseTimer() {
        // TODO: Tạm dừng đếm thời gian
        handler.removeCallbacks(runnable);
        // Thay đổi hình ảnh của nút thành Continue
        btnPauseContinue.setImageResource(R.drawable.ic_pause);
        isPaused = true;
    }

    // Phương thức để tiếp tục đếm thời gian sau khi đã tạm dừng
    private void resumeTimer() {
        // TODO: Tiếp tục đếm thời gian
        handler.postDelayed(runnable, 1000);
        // Thay đổi hình ảnh của nút thành Pause
        btnPauseContinue.setImageResource(R.drawable.ic_resume);
        isPaused = false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }


    private String getCurrentDateTime(){

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }

}