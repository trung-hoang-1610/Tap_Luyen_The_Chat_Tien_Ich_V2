package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tap_luyen_the_chat_tien_ich_v2.Adapter.HistoryAdapter;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.ExerciseLog;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private ExerciseLog exerciseLog;
    private RecyclerView recyclerView;
    private List<ExerciseLog> data = new ArrayList<>();
    private TextView tvEmptyView;


    private HistoryAdapter adapter;
    public HistoryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.rcViewHistory);
        tvEmptyView = view.findViewById(R.id.tvEmptyView);

        exerciseLog = new ExerciseLog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("exercise_logs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    if(child.child("userID").getValue(String.class).equals(LoginActivity.mUser.getId())){


                        exerciseLog = child.getValue(ExerciseLog.class);
                        data.add(exerciseLog);
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Change this line
                adapter = new HistoryAdapter(getContext(), data); // Change this line
                recyclerView.setAdapter(adapter);
                if(data.isEmpty()){
                    //recyclerView.setVisibility(View.GONE);
                    tvEmptyView.setVisibility(View.VISIBLE);
                } else {
                    tvEmptyView.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}