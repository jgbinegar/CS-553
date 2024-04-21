package com.example.application.ui.pages.quiz;

import com.example.application.backend.data.Question;
import com.example.application.backend.utility.QuestionImporter;
import com.example.application.ui.components.Scene;
import com.example.application.ui.pages.QuizView;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;

import java.io.*;
import java.util.List;

public class SetupScene extends Scene {
    private final QuizView quizView;

    private TextField userIDTextField;
    private IntegerField questionsCountField;
    private IntegerField timeLimitField;
    private Button startButton;

    private List<Question> questionList;
    
    public SetupScene(QuizView quizView) {
        super();

        this.quizView = quizView;

        setJustifyContentMode(JustifyContentMode.CENTER);

        createUserID();
        createQuizSelector();
        createOptions();
        createStart();
    }

    private void createUserID() {
        userIDTextField = new TextField("Enter User ID:");
        userIDTextField.setRequiredIndicatorVisible(true);
        userIDTextField.setMinLength(4);
        userIDTextField.setMaxLength(12);
        userIDTextField.setAllowedCharPattern("[0-9]");
        userIDTextField.setWidth("100%");
        HorizontalLayout horizontalLayout = new HorizontalLayout(userIDTextField);
        horizontalLayout.setWidth("30%");
        add(horizontalLayout);
    }

    private void createQuizSelector() {
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload fileUpload = new Upload(buffer);
        fileUpload.setAcceptedFileTypes("application/txt", ".txt");
        fileUpload.setMaxFiles(1);
        fileUpload.setWidth("100%");

        fileUpload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream is = buffer.getInputStream(fileName);

            questionList = QuestionImporter.importQuestions(is);

            fileUploaded();
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout(fileUpload);
        horizontalLayout.setWidth("30%");
        add(horizontalLayout);
    }

    private void createOptions() {
        // Question Count
        questionsCountField = new IntegerField("Number of Questions:");
        questionsCountField.setValue(0);
        questionsCountField.getElement().setEnabled(false);
        questionsCountField.setStepButtonsVisible(true);
        questionsCountField.getElement().setAttribute("title", "The total number of questions to be asked. \n A value of '0' will default to all questions.");

        // Time Limit
        timeLimitField = new IntegerField("Time Limit");
        timeLimitField.setValue(0);
        timeLimitField.getElement().setEnabled(false);
        timeLimitField.setStepButtonsVisible(true);
        timeLimitField.getElement().setAttribute("title", "Total time for quiz. \n A value of '0' will default to unlimited time.");

        HorizontalLayout optionsLayout = new HorizontalLayout();
        optionsLayout.add(questionsCountField, timeLimitField);

        Accordion optionsList = new Accordion();
        optionsList.close();
        optionsList.add("Options", optionsLayout);

        HorizontalLayout horizontalLayout = new HorizontalLayout(optionsList);
        horizontalLayout.setWidth("30%");
        add(horizontalLayout);
    }

    private void createStart() {
        // Start Quiz
        startButton = new Button("Start Quiz");
        startButton.getElement().setEnabled(false);
        startButton.addSingleClickListener(click -> {
            if(userIDTextField.getValue().isEmpty()) {
                userIDTextField.setInvalid(true);
            } else {
                quizView.startQuiz(userIDTextField.getValue(), questionList, questionsCountField.getValue(), timeLimitField.getValue());
            }
        });

        add(startButton);
    }

    private void fileUploaded() {
        questionsCountField.getElement().setEnabled(true);
        questionsCountField.setMax(questionList.size());
        timeLimitField.getElement().setEnabled(true);
        startButton.getElement().setEnabled(true);
    }
}
