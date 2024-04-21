package com.example.application.backend.data;

import java.util.List;

public class Question {

    private int correctAnswerIndex;
    private String question;
    private List<String> answers;


    public Question(String question, int correctAnswerIndex, List<String> answers) {
        this.question = question;
        // We calculate the Index as the passed index - 1 to account for List indices starting at 0.
        this.correctAnswerIndex = correctAnswerIndex - 1;
        this.answers = answers;
    }

    public String getQuestion() { return question; }

    public List<String> getAnswers() { return this.answers; }

    public String getCorrectAnswer() { return answers.get(correctAnswerIndex); }

    public boolean isAnswerCorrect(String answer) { return answers.get(correctAnswerIndex).equals(answer); }

}

