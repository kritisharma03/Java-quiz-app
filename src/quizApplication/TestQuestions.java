package quizApplication;
import java.util.List;

public class TestQuestions {
    public static void main(String[] args) {
        System.out.println("Loading questions...");
        List<Question> qs = QuestionBank.getQuestions();
        System.out.println("Loaded count: " + qs.size());
        for (int i = 0; i < Math.min(qs.size(), 5); i++) {
            System.out.println((i+1) + ") " + qs.get(i).getQuestionText());
        }
    }
}