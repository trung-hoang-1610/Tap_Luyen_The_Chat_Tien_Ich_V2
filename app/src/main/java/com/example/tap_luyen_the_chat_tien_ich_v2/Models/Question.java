package com.example.tap_luyen_the_chat_tien_ich_v2.Models;

import java.io.Serializable;

public class Question implements Serializable {
    private String question;
    private String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getQuestionText() {
        return 0;
    }
}
