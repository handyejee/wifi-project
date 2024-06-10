package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// DB 연결 호출 부분 구현
public class DatabaseConnection {
    private static final String url = "jdbc:mariadb://localhost:3306/wifi_db";
    private static final String dbUserId = "root";
    private static final String dbPassword = "rldnrl";

    static { // 정적 초기화 블록
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbUserId, dbPassword);
    }
}
