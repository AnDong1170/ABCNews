package Util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Jdbc {
    private static String URL = "jdbc:sqlserver://localhost:1433;databaseName=ABCNews;encrypt=true;trustServerCertificate=true;";
    private static String USER = "sa";
    private static String PASS = "123456"; // đổi thành pass SQL của bạn nha

    public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}