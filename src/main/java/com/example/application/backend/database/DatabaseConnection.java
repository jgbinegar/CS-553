package com.example.application.backend.database;

import com.example.application.backend.data.Result;

import java.sql.*;

public class DatabaseConnection {

    private Connection connection;

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:~/QuizApplication/Results_Web;");

            initTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initTable() {
        PreparedStatement createTable = null;
        try {
            createTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS quiz_results (user_ID text, date_completed date, time_completed text, num_correct integer, num_asked integer)");

            createTable.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertResult(Result result) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into quiz_results (user_ID, date_completed, time_completed, num_correct, num_asked) values (?,?,?,?,?)");

            ps.setString(1, result.getUserID());
            ps.setDate(2, Date.valueOf(result.getDateCompleted()));
            ps.setString(3, result.getCompletionTime());
            ps.setInt(4, result.getNumCorrect());
            ps.setInt(5, result.getNumAsked());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet lookupResults(String userID) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from quiz_results where user_ID=?");

            ps.setString(1, userID);

            return ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet allResults() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from quiz_results");

            return ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
