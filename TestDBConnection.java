public class TestDBConnection {
    public static void main(String[] args) {
        try {
            // DB connection test karna
            DBConnection.getConnection();
            System.out.println("Database connected successfully!");

            // DAO test karna - score insert karna
            GameScoreDAO dao = new GameScoreDAO();
            dao.saveScore("TestPlayer", 100, "1 min 30 sec");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}