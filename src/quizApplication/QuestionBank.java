package quizApplication;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

// Question class (exact same structure as yours)
class Question {
    private String questionText;
    private List<String> options;
    private String correctAnswer;

    public Question(String questionText, List<String> options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

public class QuestionBank {

    // PUBLIC FUNCTION USED BY Quiz.java
    public static List<Question> getQuestions() {
        System.out.println("QuestionBank: loading questions from DB...");

        List<Question> questions = new ArrayList<>();

        String sql = "SELECT question_text, option_a, option_b, option_c, option_d, correct_option " +
                "FROM question_bank ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String qText = rs.getString("question_text");
                String a = rs.getString("option_a");
                String b = rs.getString("option_b");
                String c = rs.getString("option_c");
                String d = rs.getString("option_d");
                String correct = rs.getString("correct_option");

                // Convert correct_option (A/B/C/D) : actual option text
                String correctAnswer = mapCorrect(correct, a, b, c, d);

                List<String> options = Arrays.asList(a, b, c, d);
                questions.add(new Question(qText, options, correctAnswer));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Failed to load questions from database!",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // randomize order of questions
        Collections.shuffle(questions);
        return questions;
    }

    // Helper : convert A/B/C/D : option text
    private static String mapCorrect(String c, String a, String b, String c2, String d) {
        if (c == null)
            return "";
        switch (c.trim().toUpperCase()) {
            case "A":
                return a;
            case "B":
                return b;
            case "C":
                return c2;
            case "D":
                return d;
            default:
                return "";
        }
    }
}
