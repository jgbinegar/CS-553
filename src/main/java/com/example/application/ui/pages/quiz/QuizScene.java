package com.example.application.ui.pages.quiz;

import com.example.application.backend.data.Question;
import com.example.application.backend.data.QuizController;
import com.example.application.ui.components.Scene;
import com.example.application.ui.pages.QuizView;
import com.flowingcode.vaadin.addons.simpletimer.SimpleTimer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.HashMap;
import java.util.List;

public class QuizScene extends Scene {
    private final QuizView quizView;
    private final QuizController quizController;

    private static final String questionCounterString = "Question %s/%s";

    private H2 questionCounter;

    private SimpleTimer timer;

    private H2 questionText;

    private VerticalLayout answerLayout;

    private Button nextQuestion;

    private final HashMap<String, Button> answersMap = new HashMap<>();

    private boolean questionAnswered = false;


    public QuizScene(QuizView quizView, QuizController quizController) {
        super();

        this.quizView = quizView;
        this.quizController = quizController;

        createBanner();
        createQuestionBox();
    }

    private void createBanner() {
        questionCounter = new H2(String.format(questionCounterString, "1", quizController.getNumQuestions()));

        HorizontalLayout questionCounterLayout = new HorizontalLayout(questionCounter);
        questionCounterLayout.setWidth("50%");
        questionCounterLayout.setAlignItems(Alignment.START);


        if(quizController.getTimeLimit() == -1) {
            timer = new SimpleTimer(999999999);
            timer.setCountUp(true);
        } else {
            timer = new SimpleTimer(quizController.getTimeLimit());
        }

        timer.addClassNames(LumoUtility.FontSize.XXLARGE, LumoUtility.AlignContent.END);
        timer.addTimerEndEvent(event -> displayResults());
        timer.setFractions(false);
        timer.setMinutes(true);
        timer.setHours(true);
        timer.start();

        HorizontalLayout timerLayout = new HorizontalLayout(timer);
        timerLayout.setWidth("50%");
        timerLayout.setAlignItems(Alignment.END);

        HorizontalLayout bannerLayout = new HorizontalLayout(questionCounterLayout, timerLayout);
        bannerLayout.setWidthFull();


        add(bannerLayout);
    }

    private void createQuestionBox() {
        Question currentQuestion = quizController.getCurrentQuestion();

        questionText = new H2(currentQuestion.getQuestion());

        answerLayout = new VerticalLayout();
        answerLayout.setWidth("100%");
        setAnswers(currentQuestion.getAnswers());
        Scroller answerBox = new Scroller(answerLayout);
        answerBox.setWidth("75%");

        nextQuestion = new Button("Next");
        nextQuestion.setWidth("40%");
        nextQuestion.getElement().setEnabled(false);
        nextQuestion.addSingleClickListener(click -> nextQuestion());

        add(questionText, answerBox, nextQuestion);
    }

    private void nextQuestion() {
        Question q = quizController.nextQuestion();

        if(q == null) {
            displayResults();
        } else {
            questionCounter.setText(String.format(questionCounterString, quizController.getCurrentQuestionIndex() + 1, quizController.getNumQuestions()));

            questionText.setText(q.getQuestion());

            setAnswers(q.getAnswers());

            nextQuestion.getElement().setEnabled(false);

            questionAnswered = false;
        }
    }

    private void setAnswers(List<String> answers) {
        answerLayout.removeAll();
        answersMap.clear();

        for(String answer : answers) {
            Button answerButton = new Button(answer);
            answerButton.setWidth("100%");
            answerButton.addSingleClickListener(click -> answerSelected(answerButton.getText()));
            answersMap.put(answer, answerButton);
            answerLayout.add(answerButton);
        }
    }

    private void answerSelected(String selectedAnswer) {
        if(!questionAnswered) {
            if(quizController.isAnswerCorrect(selectedAnswer)) {
                answersMap.get(selectedAnswer).addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            } else {
                answersMap.get(selectedAnswer).addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
                answersMap.get(quizController.getCorrectAnswer()).addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            }

            nextQuestion.getElement().setEnabled(true);

            questionAnswered = true;
        }
    }

    private void displayResults() {
        timer.pause();
        quizController.setElapsedTime(timer.getCurrentTime());
        quizView.displayResults(quizController.generateResult());
    }
}
