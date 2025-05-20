import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameScoreDAO {
    public void saveScore(String playerName, int score, String timeTaken) {
        String sql = "INSERT INTO game_score (player_name, score, time_taken) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);
            pstmt.setString(3, timeTaken);

            pstmt.executeUpdate();
            System.out.println("Score saved successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}