package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.tap_luyen_the_chat_tien_ich_v2.Adapter.ExerciseAdapter;
import com.example.tap_luyen_the_chat_tien_ich_v2.Adapter.PlanAdapter;
import com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO.ExerciseDAO;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.Exercise;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.DayDetail;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.Plan;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities.ExerciseDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class DayDetailFragment extends Fragment {

    TextView txtPlanName;
    TextView txtExerise;
    TextView txtDayName;
    TextView txtDayLevel;
    ScrollView scrollView;
    RecyclerView rcViewExercise;
    Button btnStart;
    ExerciseAdapter mAdapter;
    private DayDetail dayDetail;
    private ExerciseDAO exerciseDAO;
    private String planDetailID;

    public void setPlanDetailID(String planDetailID){
        this.planDetailID = planDetailID;
    }

    // Phương thức public để nhận dữ liệu từ activity
    public void setDayDetail(DayDetail dayDetail) {
        this.dayDetail = dayDetail;
    }
    //public void setPlanName(String planName){this.planName = planName;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_day_detail, container, false);
        //txtPlanName = mView.findViewById(R.id.txtplanName);
        txtDayName = mView.findViewById(R.id.txtDayName);
        txtDayLevel = mView.findViewById(R.id.txtDayLevel);
        rcViewExercise = mView.findViewById(R.id.rcViewExercise);
        scrollView = mView.findViewById(R.id.scrollView2);
        btnStart = mView.findViewById(R.id.btnStart);
        txtExerise = mView.findViewById(R.id.txtExercises);


        exerciseDAO = new ExerciseDAO();
        txtDayName.setText(dayDetail.getName());
        txtDayLevel.setText("Level: "+dayDetail.getLevel());
       // txtPlanName.setText(planName);
        List<String> ListStrExerciseID = dayDetail.getExerciseIds();

        exerciseDAO.getAllExercises(new ExerciseDAO.ExerciseListCallback() {
            @Override
            public void onExerciseListReceived(List<Exercise> exerciseList) {
                int count = 0;
                List<String> excerciseNames = new ArrayList<>();
                List<Exercise> data = new ArrayList<>();
                for(Exercise ex : exerciseList){

                    for(String exID :ListStrExerciseID){
                        if(ex.getExercise_id().trim().equals(exID.trim())){
                            data.add(ex);
                            count ++;
                            excerciseNames.add(ex.getExcercise_name());
                            System.out.println(ex.getExcercise_description());
                        }

                    }
                }
                txtExerise.setText("Exercises ("+ count +")");
                System.out.println(excerciseNames);

                rcViewExercise.setLayoutManager(new LinearLayoutManager(getContext())); // Change this line
                mAdapter = new ExerciseAdapter(getContext(), data);
                //mAdapter = new ExerciseAdapter();
                rcViewExercise.setAdapter(mAdapter);
            }

        });



        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExerciseDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dayWorkout",dayDetail);
                bundle.putSerializable("planDetailID", planDetailID);
                intent.putExtras(bundle);
                startActivity(intent);
                //getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);


            }
        });


        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}