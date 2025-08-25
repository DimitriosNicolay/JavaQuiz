package persistence;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String DB_HOST = dotenv.get("DB_HOST");
    private static final String DB_PORT = dotenv.get("DB_PORT");
    private static final String DB_NAME = dotenv.get("DB_NAME");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    private final String jdbcUrl;

    public DBManager() {
        jdbcUrl = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";
    }

    public Connection getConnection() throws SQLException {
        // Always return a new connection when requested
        return DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD);
    }
}
