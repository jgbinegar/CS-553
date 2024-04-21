package com.example.application.ui.pages;

import com.example.application.backend.data.Question;
import com.example.application.backend.data.QuizController;
import com.example.application.backend.data.Result;
import com.example.application.backend.database.DatabaseConnection;
import com.example.application.ui.MainLayout;
import com.example.application.ui.pages.quiz.QuizScene;
import com.example.application.ui.pages.quiz.ResultsScene;
import com.example.application.ui.pages.quiz.SetupScene;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value="quiz", layout = MainLayout.class)
@PageTitle("Quiz | CS 553")
public class QuizView extends HorizontalLayout {

    private static final DatabaseConnection databaseConnection = new DatabaseConnection();



    public QuizView() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultVerticalComponentAlignment(Alignment.CENTER);

        createSetup();
    }

    private void createSetup() {
        SetupScene setupScene = new SetupScene(this);

        add(setupScene);
    }

    public void startQuiz(String userID, List<Question> questionList, int numQuestions, int timeLimit) {

        QuizController quizController = new QuizController(userID, questionList, numQuestions, timeLimit);

        QuizScene quizScene = new QuizScene(this, quizController);

        removeAll();

        add(quizScene);
    }

    public void displayResults(Result result) {
        ResultsScene resultsScene = new ResultsScene(result);

        databaseConnection.insertResult(result);

        removeAll();
        add(resultsScene);
    }


}
