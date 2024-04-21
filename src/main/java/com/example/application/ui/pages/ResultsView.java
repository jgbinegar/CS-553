package com.example.application.ui.pages;

import com.example.application.backend.data.Result;
import com.example.application.backend.database.DatabaseConnection;
import com.example.application.ui.MainLayout;
import com.example.application.ui.components.Scene;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Route(value="results", layout = MainLayout.class)
@PageTitle("Quiz Results | CS 553")
public class ResultsView extends HorizontalLayout {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    private final Scene searchScene;

    private Grid<Result> resultTable;

    public ResultsView() {
        System.out.println("Results View");

        setSizeFull();
        getStyle ().set ( "border" , "6px dotted DarkOrange" );
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultVerticalComponentAlignment(Alignment.CENTER);

        searchScene = new Scene();
        add(searchScene);

        createSearch();
        createAdminLogin();
        createResultGrid();
    }

    private void createSearch() {
        TextField searchBar = new TextField("Enter User ID:");
        searchBar.setPlaceholder("User ID");
        searchBar.setWidth("85%");

        Button searchButton = new Button("Search");
        searchButton.setWidth("15%");
        searchButton.addSingleClickListener(click -> search(searchBar.getValue()));

        HorizontalLayout searchLayout = new HorizontalLayout(searchBar, searchButton);
        searchLayout.setAlignItems(Alignment.BASELINE);
        searchLayout.setWidthFull();
        searchScene.add(searchLayout);
    }

    private void createAdminLogin() {

        TextField username = new TextField("Username");
        username.setRequiredIndicatorVisible(true);
        username.setWidth("45%");

        PasswordField password = new PasswordField("Password");
        password.setRequiredIndicatorVisible(true);
        password.setWidth("45%");

        Button login = new Button("Login");
        login.addSingleClickListener(click -> {
            username.setInvalid(username.getValue().isEmpty());
            password.setInvalid(password.getValue().isEmpty());

            System.out.println(username.getValue() + " : " + password.getValue());

            if(Objects.equals(username.getValue(), "admin") && Objects.equals(password.getValue(), "admin")) {
                username.setInvalid(false);
                password.setInvalid(false);
                search();
            } else {
                username.setInvalid(true);
                password.setInvalid(true);
            }
        });
        login.setWidth("10%");

        HorizontalLayout adminLayout = new HorizontalLayout(username, password, login);
        adminLayout.setAlignItems(Alignment.BASELINE);
        adminLayout.setWidthFull();

        Accordion adminLogin = new Accordion();
        adminLogin.setWidthFull();
        adminLogin.close();
        adminLogin.add("Administrator Login", adminLayout);

        searchScene.add(adminLogin);
    }

    private void search(String userID) {
        try(ResultSet resultSet = databaseConnection.lookupResults(userID)) {
            List<Result> resultList = new ArrayList<>();

            while(resultSet.next()) {
                String id = resultSet.getString(1);
                LocalDate date = resultSet.getDate(2).toLocalDate();
                String timeCompleted = resultSet.getString(3);
                int correctAnswers = resultSet.getInt(4);
                int questionsAsked = resultSet.getInt(5);

                Result result = new Result(id, timeCompleted, date, correctAnswers, questionsAsked);
                resultList.add(result);
            }

            resultTable.setItems(resultList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void search() {
        try(ResultSet resultSet = databaseConnection.allResults()) {
            List<Result> resultList = new ArrayList<>();

            while(resultSet.next()) {
                String id = resultSet.getString(1);
                LocalDate date = resultSet.getDate(2).toLocalDate();
                String timeCompleted = resultSet.getString(3);
                int correctAnswers = resultSet.getInt(4);
                int questionsAsked = resultSet.getInt(5);

                Result result = new Result(id, timeCompleted, date, correctAnswers, questionsAsked);
                resultList.add(result);
            }

            resultTable.setItems(resultList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createResultGrid() {
        resultTable = new Grid<>(Result.class, false);
        resultTable.addColumn(Result::getUserID).setHeader("User ID");
        resultTable.addColumn(Result::getDateCompleted).setHeader("Date Completed");
        resultTable.addColumn(Result::getCompletionTime).setHeader("Completion Time");
        resultTable.addColumn(Result::getGradePercentage).setHeader("Grade Percentage");

        searchScene.add(resultTable);
    }
}
