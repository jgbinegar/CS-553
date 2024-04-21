package com.example.application.backend.data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Result {

    private final String completionTime;
    private final LocalDate dateCompleted;
    private final Integer numCorrect;
    private final Integer numAsked;
    private final String userID;

    public Result(String userID, String completionTime, Integer numCorrect, Integer numAsked) {
        this(userID, completionTime, Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDate(), numCorrect, numAsked);
    }

    public Result(String userID, String completionTime, LocalDate dateCompleted, Integer numCorrect, Integer numAsked) {
        this.userID = userID;
        this.dateCompleted = dateCompleted;
        this.completionTime = completionTime;
        this.numCorrect = numCorrect;
        this.numAsked = numAsked;
    }

    public String getUserID() { return userID; }
    public LocalDate getDateCompleted() { return dateCompleted; }
    public String getCompletionTime() { return completionTime; }
    public Integer getNumCorrect() { return numCorrect; }
    public Integer getNumAsked() { return numAsked; }
    public Double getGradePercentage() { return calculatePercentage(); }

    private double calculatePercentage() {
        return (double) getNumCorrect() * 100 / (double) getNumAsked();
    }

    @Override
    public String toString() {
        String toString = "Result [User ID=%s, Date Completed=%s, Completion Time=%s, Number Correct Answers=%s, Number Asked=%s]";
        return String.format(toString, getUserID(), getDateCompleted(), getCompletionTime(), getNumCorrect(), getNumAsked());
    }
}
