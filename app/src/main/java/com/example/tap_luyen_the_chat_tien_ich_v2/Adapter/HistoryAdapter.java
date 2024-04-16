package com.example.tap_luyen_the_chat_tien_ich_v2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.ExerciseLog;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<ExerciseLog> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private ImageView imgPlan_History;

    // Constructor
    public HistoryAdapter(Context context, List<ExerciseLog> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Inflates the row layout from xml when needed
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_history_layout, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        ExerciseLog exerciseLog= mData.get(position);
        holder.txtDayName_History.setText(exerciseLog.getDayName());
        holder.txtCalories.setText(exerciseLog.getCaloBurned()+"");
        holder.txtDuration.setText(exerciseLog.getDuration());
        holder.txtNumberOfExercise.setText(exerciseLog.getNumberOfExercise()+"");
        holder.txtDateTime.setText(exerciseLog.getDateTime());

//        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, PlanActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("plan", exerciseLog);
//                intent.putExtras(bundle);
//                //mContext.startActivity(intent);
//                ((MainActivity)mContext).startActivityWithAnimation(intent);
//            }
//        });
        Glide.with(mContext)
                .load(exerciseLog.getImgPlan())
                .into(imgPlan_History);

    }

    // Binds the data to the TextView in each row



    // Total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDateTime;
        TextView txtDayName_History;
        TextView txtNumberOfExercise;
        TextView txtDuration;
        TextView txtCalories;
        ConstraintLayout layoutItem;

        ViewHolder(View itemView) {
            super(itemView);
            imgPlan_History = itemView.findViewById(R.id.imgPlan_History);
            txtDateTime = itemView.findViewById(R.id.txtDateTime);
            txtDayName_History = itemView.findViewById(R.id.txtDayName_History);
            txtNumberOfExercise = itemView.findViewById(R.id.txtNumberOfExercise);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            txtCalories = itemView.findViewById(R.id.txtCalories);
        }

    }
}
