package com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO.Plan;

import androidx.annotation.NonNull;

import com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO.UserDAO;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.User;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.Plan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlanDAO {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    // Interface để chuyển dữ liệu người dùng về nơi gọi
    public interface PlanCallback {

        void onPlanReceived(Plan plan);
    }
    public interface PlansCallback{
        void onPlanListReceived(List<Plan> planList);
    }

    public PlanDAO() {
    }

    public void setPlan(Plan plan){
        myRef.child("Plans").child(plan.getId()).setValue(plan);
    }

    public void getAllPlans(PlanDAO.PlansCallback callback){
        List<Plan> plans = new ArrayList<>();
        myRef.child("Plans").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Plan plan = childSnapshot.getValue(Plan.class);
                    plans.add(plan);
                }
                // Gọi phương thức callback để chuyển danh sách người dùng về nơi gọi
                callback.onPlanListReceived(plans);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }

    public void getOnePlan(PlanDAO.PlanCallback callback, String planID){
        myRef.child("Plans").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Plan plan = null;
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    if(childSnapshot.getKey().equals(planID)){
                        plan = childSnapshot.getValue(Plan.class);
                        break;
                    }
                }
                // Kiểm tra xem user có tồn tại không trước khi gọi callback
                if (plan != null) {
                    callback.onPlanReceived(plan);
                } else {
                    // Xử lý khi không tìm thấy user với userID tương ứng
                    // Ví dụ: callback.onUserReceived(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }
}
