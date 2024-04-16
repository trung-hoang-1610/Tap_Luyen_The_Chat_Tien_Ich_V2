package com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO.Plan;

import androidx.annotation.NonNull;

import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.DayDetail;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.Plan;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.PlanDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlanDetailDAO {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    private DayDetailDAO dayDetailDAO;
    public PlanDetailDAO(){

    }

    public interface PlanDetailCallback {

        void onPlanDetailReceived(PlanDetail planDetail);
    }
    public interface PlanDetailListCallback{
        void onPlanDetailListReceived(List<PlanDetail> planDetailList);
    }

    public void setPlanDetail(PlanDetail planDetail){
        myRef.child("PlanDetails").child(planDetail.getId()).setValue(planDetail);
    }
    public void getAllPlanDetails(PlanDetailDAO.PlanDetailListCallback callback){
        List<PlanDetail> planDetailList = new ArrayList<>();

        myRef.child("PlanDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    PlanDetail planDetail = childSnapshot.getValue(PlanDetail.class);
                    planDetailList.add(planDetail);
                }
                // Gọi phương thức callback để chuyển danh sách người dùng về nơi gọi
                callback.onPlanDetailListReceived(planDetailList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }
    List<DayDetail> dayDetails = null;
    public void getOnePlanDetail(PlanDetailDAO.PlanDetailCallback callback, String planDetailID) {
        dayDetailDAO = new DayDetailDAO();
        dayDetailDAO.getAllDayDetails(new DayDetailDAO.DayDetailListCallback() {
            @Override
            public void onDayDetailListReceived(List<DayDetail> dayDetailList) {
                System.out.println(dayDetailList);
                myRef.child("PlanDetails").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        PlanDetail planDetail = null;
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            if (childSnapshot.getKey().equals(planDetailID)) {
                                planDetail = childSnapshot.getValue(PlanDetail.class);
                                planDetail.setListDay(dayDetailList);
                                break;
                            }
                        }
                        // Kiểm tra xem planDetail có tồn tại không trước khi gọi callback
                        if (planDetail != null) {
                            callback.onPlanDetailReceived(planDetail);
                        } else {
                            // Xử lý khi không tìm thấy planDetail với planDetailID tương ứng
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Xử lý khi có lỗi
                    }
                });
            }
        },planDetailID);
    }
}
