package com.example.tap_luyen_the_chat_tien_ich_v2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.Exercise;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.Plan;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities.MainActivity;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities.PlanActivity;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {


    private List<Exercise> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private ImageView imgView;

    // Constructor
    public ExerciseAdapter(Context context, List<Exercise> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Inflates the row layout from xml when needed
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_exercise_layout, parent, false);
        return new ExerciseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = mData.get(position);
        holder.textView.setText(exercise.getExcercise_name() + "\n" + exercise.getExcercise_description() + " reps" );
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("plan", exercise);
                intent.putExtras(bundle);
                //mContext.startActivity(intent);
                ((MainActivity)mContext).startActivityWithAnimation(intent);
            }
        });
                Glide.with(mContext)
                .load(exercise.getExcercise_path())
                .into(imgView);

    }

    // Binds the data to the TextView in each row



    // Total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ConstraintLayout layoutItem;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtPlanName);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            imgView = itemView.findViewById(R.id.imgView);
        }

    }
}
