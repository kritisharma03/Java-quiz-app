// import quizApplication.Login;

public class Main {
    public static void main(String[] args) {
        // 1) Test DB — run quick connection test
        System.out.println("Running DB connectivity test...");
        TestConn.main(null);

        // 2) Launch GUI if Login exists
        // If you have a Login class with a no-arg constructor that shows a window:
        try {
            Class.forName("quizApplication.Login");
            // instantiate Login if default constructor exists
            // NOTE: most GUI classes call setVisible(true) in their constructor
            new quizApplication.Login();
        } catch (ClassNotFoundException ex) {
            System.out.println("Login class not found; skip GUI launch.");
        } catch (Exception ex) {
            System.out.println("Failed to launch GUI: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

