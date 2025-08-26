package persistence;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final Dotenv dotenv = Dotenv.load();

    // Load database configuration from environment variables
    private static final String DB_HOST = dotenv.get("DB_HOST");
    private static final String DB_PORT = dotenv.get("DB_PORT");
    private static final String DB_NAME = dotenv.get("DB_NAME");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    // JDBC URL
    private final String jdbcUrl;

    public DBManager() {
        jdbcUrl = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    }

    // Method to get a database connection
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD);
    }
}
