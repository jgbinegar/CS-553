package com.example.application.backend.utility;

import com.example.application.backend.data.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionImporter {

    private static final String COMMENT = "*";
    private static final String QUESTION = "@Q";
    private static final String ANSWER = "@A";
    private static final String END = "@E";

    public static ArrayList<Question> importQuestions(InputStream inputStream) {
        ArrayList<String> qString = null;
        try {

            qString = parseFile(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Question> questions = new ArrayList<>();

        for (String s : qString) {
            Question temp = parseQuestion(s);

            questions.add(temp);
        }

        Collections.shuffle(questions);

        return questions;
    }

    private static ArrayList<String> parseFile(InputStream inputStream) throws IOException {
        ArrayList<String> qString = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        while ((line = reader.readLine()) != null) {
            if (line.isEmpty() || line.startsWith(COMMENT))
                continue;
            else if (line.startsWith(QUESTION)) {
                StringBuilder question = new StringBuilder(line + "\n");
                while ((line = reader.readLine()) != null) {
                    if (line.isEmpty() || line.startsWith(COMMENT))
                        continue;

                    question.append(line).append("\n");

                    if (line.startsWith(END)) {
                        qString.add(question.toString());
                        break;
                    }
                }
            }
        }

        return qString;
    }

    private static Question parseQuestion(String toParse) {
        String[] lines = toParse.split("\n");

        int qLine = 0;
        int aLine = 0;
        int eLine = 0;

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith(QUESTION))
                qLine = i;
            if (lines[i].startsWith(ANSWER))
                aLine = i;
            if (lines[i].startsWith(END))
                eLine = i;
        }

        StringBuilder question = new StringBuilder();

        for (int i = qLine + 1; i < aLine; i++)
            question.append(lines[i]).append(" ");

        int correctAnswer;

        correctAnswer = Integer.parseInt(lines[aLine + 1]);

        List<String> answers = new ArrayList<>();

        for (int i = aLine + 2; i < eLine; i++)
            answers.add(lines[i]);

        Question q = new Question(question.toString(), correctAnswer, answers);

        return q;
    }
}
