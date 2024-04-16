package com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO;

import androidx.annotation.NonNull;

import com.example.tap_luyen_the_chat_tien_ich_v2.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    // Interface để chuyển dữ liệu người dùng về nơi gọi
    public interface UserCallback {

        void onUserReceived(User user);
    }
    public interface UsersCallback{
        void onUserListReceived(List<User> users);
    }

    public UserDAO() {
    }

    public void setUserAccount(String email,String password, String name){
        String userKey = myRef.child("User").push().getKey();

        myRef.child("User").child(userKey).child("id").setValue(userKey);
        myRef.child("User").child(userKey).child("email").setValue(email);
        myRef.child("User").child(userKey).child("password").setValue(password);
        myRef.child("User").child(userKey).child("name").setValue(name);
        myRef.child("User").child(userKey).child("fitness_level").setValue(null);
        myRef.child("User").child(userKey).child("height").setValue(null);
        myRef.child("User").child(userKey).child("weight").setValue(null);
        myRef.child("User").child(userKey).child("planIds").setValue(null);


    }
    public void setUserInfor(String id, String fitness_level,String height, String weight){

        myRef.child("User").child(id).child("fitness_level").setValue(fitness_level);
        myRef.child("User").child(id).child("height").setValue(height);
        myRef.child("User").child(id).child("weight").setValue(weight);

    }

    public void addUserPlanId(String userId, String planId) {
        myRef.child("Users").child(userId).child("planIds").push().setValue(planId);
    }

    public void getAllUsers(UsersCallback callback){
        List<User> users = new ArrayList<>();
        myRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    User user = childSnapshot.getValue(User.class);
                    users.add(user);
                }
                // Gọi phương thức callback để chuyển danh sách người dùng về nơi gọi
                callback.onUserListReceived(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }

    public void getOneUser(UserCallback callback, String userID){
        myRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = null;
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    if(childSnapshot.getKey().equals(userID)){
                        user = childSnapshot.getValue(User.class);
                        break;
                    }
                }
                // Kiểm tra xem user có tồn tại không trước khi gọi callback
                if (user != null) {
                    callback.onUserReceived(user);
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
