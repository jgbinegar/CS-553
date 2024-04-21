package com.example.application.backend.data;

import java.math.BigDecimal;
import java.util.List;

public class QuizController {
    private int currentQuestionNumber;

    private int correctAnswers;

    private final String userID;
    private final List<Question> questionList;
    private final int totalQuestions;
    private final int timeLimit;

    private BigDecimal elapsedTime = BigDecimal.valueOf(0.0);


    public QuizController(String userID, List<Question> questionList, int numQuestions, int timeLimit) {
        this.userID = userID;
        this.questionList = questionList;

        if(numQuestions == 0) {
            totalQuestions = -1;
        } else {
            totalQuestions = numQuestions;
        }

        if(timeLimit == 0) {
            this.timeLimit = -1;
        } else {
            this.timeLimit = timeLimit;
        }

        currentQuestionNumber = 0;
    }

    public boolean isAnswerCorrect(String answer) {
        if(getCurrentQuestion().isAnswerCorrect(answer)) {
            correctAnswers++;
            return true;
        }

        return false;
    }

    public String getCorrectAnswer() { return getCurrentQuestion().getCorrectAnswer(); }

    public int getNumCorrectAnswers() { return correctAnswers; }
    public int getNumQuestions() {
        if(totalQuestions == -1)
            return questionList.size();

        return totalQuestions;
    }
    public int getTimeLimit() { return timeLimit; }

    public Question nextQuestion() {
        currentQuestionNumber++;

        if((currentQuestionNumber >= totalQuestions || currentQuestionNumber >= questionList.size()) && totalQuestions != -1) {
            return null;
        }

        return questionList.get(currentQuestionNumber);
    }

    public Question getCurrentQuestion() {
        return questionList.get(currentQuestionNumber);
    }
    public int getCurrentQuestionIndex() { return currentQuestionNumber; }

    public void setQuestions(List<Question> questions) { questionList.addAll(questions); }

    public String getUserID() { return userID; }

    public void setElapsedTime(BigDecimal value) { elapsedTime = value; }

    public Result generateResult() {
        String completionTime = "";

        if(timeLimit == -1) {
            completionTime = String.valueOf(elapsedTime);
        } else if(elapsedTime.intValue() == 0) {
            completionTime = String.valueOf(timeLimit);
        } else {
            completionTime = String.valueOf(timeLimit - elapsedTime.intValue());
        }

        return new Result(getUserID(), completionTime, getNumCorrectAnswers(), getNumQuestions());
    }
}
