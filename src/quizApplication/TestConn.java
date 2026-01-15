package quizApplication;


// import quizApplication.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConn {
    public static void main(String[] args) {
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT now()")) {

            if (rs.next()) {
                System.out.println("Connected to DB. Server time: " + rs.getString(1));
            } else {
                System.out.println("Connected but query returned nothing.");
            }

        } catch (Exception e) {
            System.err.println("DB test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
