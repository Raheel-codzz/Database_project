package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection1 {
    private static final String URL = "jdbc:mysql://localhost:3306/the_mall_of_lahore";
    private static final String USER = "root"; // your MySQL username
    private static final String PASSWORD = "moazzam2547422"; // your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


