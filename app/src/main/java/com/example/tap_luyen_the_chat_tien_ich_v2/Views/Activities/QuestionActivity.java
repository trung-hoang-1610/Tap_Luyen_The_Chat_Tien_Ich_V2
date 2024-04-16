package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.tap_luyen_the_chat_tien_ich_v2.Models.Question;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private List<Question> questionList;
    private int currentQuestionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Initialize your question list
        questionList = new ArrayList<>();

        Question question = new Question("Cân nặng? ","100kg");
        Question question2 = new Question("Chiều cao? ","100kg");

        questionList.add(question);
        questionList.add(question2);
        // Show the first question
        showQuestion(currentQuestionIndex);
    }

    private void showQuestion(int index) {
        QuestionFragment questionFragment = QuestionFragment.newInstance(
                questionList.get(index),
                index,
                questionList
        );
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, questionFragment);
        transaction.commit();
    }
}