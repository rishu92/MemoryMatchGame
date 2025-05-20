import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/memory_match_game";
    private static final String USER = "root"; // apna MySQL username daalo
    private static final String PASSWORD = ""; // agar password hai toh yahan daalo

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}