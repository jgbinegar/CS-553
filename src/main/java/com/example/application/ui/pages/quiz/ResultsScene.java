package com.example.application.ui.pages.quiz;

import com.example.application.backend.data.Result;
import com.example.application.ui.components.Scene;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ResultsScene extends Scene {

    private final String userID;
    private final String completionTime;
    private final int correctAnswers;
    private final int totalQuestions;

    public ResultsScene(Result result) {
        super();

        setJustifyContentMode(JustifyContentMode.CENTER);

        this.userID = result.getUserID();
        this.completionTime = result.getCompletionTime();
        this.correctAnswers = result.getNumCorrect();
        this.totalQuestions = result.getNumAsked();

        createResults();
    }

    private void createResults() {
        // User ID
        H2 userIdText = new H2("User ID: ");
        H2 userIdResult = new H2(userID);
        createHorizontalLayout(userIdText, userIdResult);

        // Completion Time
        H2 completionTimeText = new H2("Completion Time:");
        H2 completionTimeResult = new H2(completionTime);
        createHorizontalLayout(completionTimeText, completionTimeResult);

        // Correct Answers
        H2 correctAnswersText = new H2("Correct Answers:");
        H2 correctAnswersResult = new H2(correctAnswers + " / " + totalQuestions);
        createHorizontalLayout(correctAnswersText, correctAnswersResult);

        // Correct Percentage
        H2 correctPercentageText = new H2("Quiz Percentage:");
        H2 correctPercentageResult = new H2(String.valueOf(calculatePercentage()));
        createHorizontalLayout(correctPercentageText, correctPercentageResult);
    }

    private void createHorizontalLayout(H2 left, H2 right) {
        HorizontalLayout layout = new HorizontalLayout(left, right);
        add(layout);
    }

    private double calculatePercentage() {
        return (double) correctAnswers * 100 / (double) totalQuestions;
    }
}
