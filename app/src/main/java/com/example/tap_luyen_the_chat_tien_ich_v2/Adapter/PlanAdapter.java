package com.example.tap_luyen_the_chat_tien_ich_v2.Adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.Plan;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities.MainActivity;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities.PlanActivity;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private List<Plan> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private ImageView imgView;

    // Constructor
    public PlanAdapter(Context context, List<Plan> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    // Binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        Plan plan = mData.get(position);
        holder.textView.setText(plan.getName());
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("plan", plan);
                intent.putExtras(bundle);
                //mContext.startActivity(intent);
                ((MainActivity)mContext).startActivityWithAnimation(intent);
            }
        });



        Glide.with(mContext)
                .load(plan.getPath_image())
                .into(imgView);
    }

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

    // Method for getting the clicked item position

}
