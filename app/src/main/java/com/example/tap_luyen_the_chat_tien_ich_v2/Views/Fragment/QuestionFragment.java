package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tap_luyen_the_chat_tien_ich_v2.Models.Question;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    private Question question;
    private TextView txtQuestion;
    private Button btnContinue;
    private int questionIndex;
    private List<Question> questionList;
    private FragmentManager fragmentManager;

    public QuestionFragment() {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance(Question question, int index, List<Question> questionList) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable("question", question);
        args.putInt("index", index);
        args.putSerializable("questionList", (Serializable) questionList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable("question");
            questionIndex = getArguments().getInt("index");
            questionList = (List<Question>) getArguments().getSerializable("questionList");
        }
        fragmentManager = getParentFragmentManager();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        txtQuestion = view.findViewById(R.id.txtQuestion);
        btnContinue = view.findViewById(R.id.btnContinue);

        txtQuestion.setText(question.getQuestion());

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextQuestion();
            }
        });

        return view;
    }

    private void showNextQuestion() {
        if (questionIndex < questionList.size() - 1) {
            QuestionFragment nextQuestionFragment = QuestionFragment.newInstance(
                    questionList.get(questionIndex + 1),
                    questionIndex + 1,
                    questionList
            );
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, nextQuestionFragment)
                    .addToBackStack(null) // Thêm Fragment hiện tại vào back stack
                    .commit();
        } else {
            // Nếu đã là câu hỏi cuối cùng, thực hiện hành động kết thúc quiz ở đây
        }
    }
}