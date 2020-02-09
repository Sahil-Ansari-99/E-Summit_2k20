package com.company.e_summit;

public class FaqModel {
    public String question, answer;

    public FaqModel(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
