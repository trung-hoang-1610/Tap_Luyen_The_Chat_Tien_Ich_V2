package com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO;

import androidx.annotation.NonNull;

import com.example.tap_luyen_the_chat_tien_ich_v2.Models.Exercise;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDAO {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    public interface ExerciseCallback {

        void onExerciseReceived(Exercise exercise);
    }
    public interface ExerciseListCallback{
        void onExerciseListReceived(List<Exercise> exerciseList);
    }

    public ExerciseDAO() {
    }

    public void setExercise(Exercise exercise){
        myRef.child("Excercises").child(exercise.getExercise_id()).setValue(exercise);
    }

    public void getAllExercises(ExerciseDAO.ExerciseListCallback callback){
        List<Exercise> exercises = new ArrayList<>();
        myRef.child("Excercises").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Exercise exercise = childSnapshot.getValue(Exercise.class);
                    exercises.add(exercise);
                }
                callback.onExerciseListReceived(exercises);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }

    public void getOneExercise(ExerciseDAO.ExerciseCallback callback, String ExerciseID){
        myRef.child("Excercises").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Exercise exercise = null;
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    if(ExerciseID.trim().equals(key.trim())){
                        exercise = childSnapshot.getValue(Exercise.class);
                        break;
                    }
                }
                // Kiểm tra xem user có tồn tại không trước khi gọi callback
                if (exercise != null) {
                    callback.onExerciseReceived(exercise);
                } else {
                    System.out.println( "Không tìm được exercise");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }
}
