package com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO.Plan;

import androidx.annotation.NonNull;

import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.DayDetail;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.PlanDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DayDetailDAO {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public DayDetailDAO(){

    }

    public interface DayDetailCallback {

        void onDayDetailReceived(DayDetail dayDetail);
    }
    public interface DayDetailListCallback{
        void onDayDetailListReceived(List<DayDetail> dayDetailList);

    }
    public void setDayDetail(DayDetail dayDetail){
        myRef.child("PlanDetails").child(dayDetail.getId()).setValue(dayDetail);
    }
    public void getAllDayDetails(DayDetailDAO.DayDetailListCallback callback, String planDetailID){
        List<DayDetail> dayDetailList = new ArrayList<>();

        myRef.child("PlanDetails").child(planDetailID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    DayDetail dayDetail = new DayDetail(); // Khởi tạo DayDetail trong vòng lặp
                    List<String> exerciseID = new ArrayList<>(); // Khởi tạo danh sách exerciseID
                    for(DataSnapshot day : childSnapshot.getChildren()){
                        for(DataSnapshot exercise : day.getChildren()){
                                    exerciseID.add(exercise.getKey());

                        }
                    }
                    dayDetail.setId(childSnapshot.child("id").getValue(String.class));
                    dayDetail.setName(childSnapshot.child("name").getValue(String.class));
                    dayDetail.setLevel(childSnapshot.child("level").getValue(String.class));
                    dayDetail.setImgPath(childSnapshot.child("imgPath").getValue(String.class));
                    dayDetail.setExerciseIds(exerciseID);
                    dayDetailList.add(dayDetail);
                }
                // Gọi phương thức callback để chuyển danh sách người dùng về nơi gọi
                callback.onDayDetailListReceived(dayDetailList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }
    public void getOneDayDetail(DayDetailDAO.DayDetailCallback callback, String dayDeTailID){
        myRef.child("PlanDetails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DayDetail dayDetail = null;
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    if(childSnapshot.getKey().toString().equals(dayDeTailID)){
                        dayDetail = childSnapshot.getValue(DayDetail.class);
                        break;
                    }
                }
                if (dayDetail != null) {
                    callback.onDayDetailReceived(dayDetail);
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }
}
