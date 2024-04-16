package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO.ExerciseDAO;
import com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO.Plan.DayDetailDAO;
import com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO.Plan.PlanDetailDAO;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.Exercise;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.DayDetail;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.Plan;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.PlanDetail;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment.DayDetailFragment;

import java.util.List;
import java.util.Locale;

public class PlanActivity extends AppCompatActivity {

    private TextView txtPlanName;
    private TextView txtTotalDayFinished;
    private TextView txtPercentDayFinished;
    private ProgressBar processBar;
    private Button btnStart;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private String planDetailID;
    private DayDetailDAO dayDetailDAO;
    private List<DayDetail> dayDetailList;
    private Button[] btnDay;
    public static Plan plan;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        initComponent();
        bundle = getIntent().getExtras();


        if (getIntent().getBooleanExtra("closeFragmentDayDetail", false)) {
            // Xóa FragmentDayDetail nếu tồn tại
            DayDetailFragment fragmentDayDetail = (DayDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragmentDayDetail != null) {
                getSupportFragmentManager().beginTransaction().remove(fragmentDayDetail).commit();
            }
        }

        if (bundle != null) {

            if (bundle.containsKey("plan")) {

                plan = (Plan) bundle.get("plan");
                txtPlanName.setText(plan.getName());
                planDetailID = plan.getPlan_detail_id().trim();


                dayDetailDAO.getAllDayDetails(new DayDetailDAO.DayDetailListCallback() {
                    @Override
                    public void onDayDetailListReceived(List<DayDetail> dayDetails) {
                        dayDetailList = dayDetails;
                        updateUI();
                    }
                }, planDetailID);

                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startDay(Tempt.getCompletedDay(PlanActivity.this, planDetailID));
                    }
                });
            } else {

                Toast.makeText(this, "Missing 'plan' extras", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {

            Toast.makeText(this, "Missing bundle", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    private void initComponent() {
        txtPlanName = findViewById(R.id.txtplanName);
        txtTotalDayFinished = findViewById(R.id.totalDayFinished);
        txtPercentDayFinished = findViewById(R.id.percentFinished);
        processBar = findViewById(R.id.progressBar);
        btnStart = findViewById(R.id.btnContinue1);
        dayDetailDAO = new DayDetailDAO();
    }

    private void updateUI() {
        btnDay = new Button[dayDetailList.size()];
        for (int i = 0; i < dayDetailList.size(); i++) {
            final int dayIndex = i;
            final DayDetail day = dayDetailList.get(i);
            int btnId = getResources().getIdentifier("btnDay" + (i + 1), "id", getPackageName());
            btnDay[i] = findViewById(btnId);
            btnDay[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Tempt.getCompletedDay(PlanActivity.this, planDetailID) > dayIndex) {
                        //
                        Toast.makeText(PlanActivity.this, "Đã hoàn thành ngày tập này!", Toast.LENGTH_SHORT).show();

                    } else if(Tempt.getCompletedDay(PlanActivity.this, planDetailID) == dayIndex){
                        startDay(dayIndex);
                    } else{
                        Toast.makeText(PlanActivity.this, "Cần hoàn thành ngày hiện tại!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (Tempt.getCompletedDay(PlanActivity.this, planDetailID) > dayIndex) {
                btnDay[i].setText("\u2713");
            }
            btnDay[i].setBackgroundTintList(getResources().getColorStateList(Tempt.getCompletedDay(PlanActivity.this, planDetailID) >= dayIndex ? R.color.springGreen : R.color.gainsboro));
            btnDay[i].setTextColor(getResources().getColorStateList(R.color.white));
            updateProgressBar();
        }

    }

    DayDetailFragment fragment = new DayDetailFragment();
    private void startDay(int dayIndex) {

        fragment.setDayDetail(dayDetailList.get(dayIndex));
        //fragment.setPlanName(plan.getName());
        fragment.setPlanDetailID(planDetailID);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack("fragment");
        fragmentTransaction.commit();
    }
    private void updateProgressBar() {
        int totalDays = dayDetailList.size();
        double progress = (double) Tempt.getCompletedDay(PlanActivity.this, planDetailID) / totalDays * 100;
        processBar.setProgress((int) progress);
        txtTotalDayFinished.setText(Tempt.getCompletedDay(PlanActivity.this, planDetailID)+"/28 Days Completed");
        txtPercentDayFinished.setText((int)progress +"%");
    }

    @Override
    protected void onResume() {
        super.onResume();

        //updateUI();
        dayDetailDAO.getAllDayDetails(new DayDetailDAO.DayDetailListCallback() {
            @Override
            public void onDayDetailListReceived(List<DayDetail> dayDetails) {
                dayDetailList = dayDetails;
                updateUI();
            }
        }, planDetailID);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commit();

    }


}