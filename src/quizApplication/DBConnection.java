
package quizApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Minimal DBConnection for PostgreSQL. Uses environment variables if present,
 * otherwise falls back to the hard-coded values below.
 */
public class DBConnection {
    private static final String HOST = System.getenv().getOrDefault("PG_HOST", "localhost");
    private static final String PORT = System.getenv().getOrDefault("PG_PORT", "5432");
    private static final String DB = System.getenv().getOrDefault("PG_DB", "quizdb");
    private static final String USER = System.getenv().getOrDefault("PG_USER", "quizuser");
    private static final String PASS = System.getenv().getOrDefault("PG_PASS", "quizpass");

    private static final String URL = String.format("jdbc:postgresql://%s:%s/%s", HOST, PORT, DB);

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver"); // ensures driver loaded
        } catch (ClassNotFoundException e) {
            throw new SQLException("Postgres JDBC driver not found on classpath", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // find user by username or create one, returns id
    private static int getOrCreateUser(Connection conn, String username) throws SQLException {
        String findUserSql = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(findUserSql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }

        String insertUserSql = "INSERT INTO users(username, email, password_hash) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(insertUserSql)) {
            ps.setString(1, username);
            ps.setString(2, username.replaceAll("\\s+", "").toLowerCase() + "@local");
            ps.setString(3, "nopass"); // dummy password for now
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Failed to create user: " + username);
                }
            }
        }
    }

    // find quiz by title or create one, returns id
    private static int getOrCreateQuiz(Connection conn, String quizTitle, int createdByUserId) throws SQLException {
        String findQuizSql = "SELECT id FROM quizzes WHERE title = ?";
        try (PreparedStatement ps = conn.prepareStatement(findQuizSql)) {
            ps.setString(1, quizTitle);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }

        String insertQuizSql = "INSERT INTO quizzes(title, description, created_by) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(insertQuizSql)) {
            ps.setString(1, quizTitle);
            ps.setString(2, "Created automatically by app");
            ps.setInt(3, createdByUserId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Failed to create quiz: " + quizTitle);
                }
            }
        }
    }

    public static void saveScore(String username, String quizTitle, int score, int total) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("username cannot be empty");
        }
        if (quizTitle == null || quizTitle.trim().isEmpty()) {
            quizTitle = "Default Quiz";
        }

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try {
                int userId = getOrCreateUser(conn, username);
                int quizId = getOrCreateQuiz(conn, quizTitle, userId);

                String insertSql = "INSERT INTO scores(user_id, quiz_id, score, total) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    ps.setInt(1, userId);
                    ps.setInt(2, quizId);
                    ps.setInt(3, score);
                    ps.setInt(4, total);
                    ps.executeUpdate();
                }

                conn.commit();
                System.out.println("Score saved successfully for user=" + username + ", quiz=" + quizTitle);
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

}
