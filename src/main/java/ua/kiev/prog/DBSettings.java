package ua.kiev.prog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBSettings {
    public static final String driver = "com.mysql.cj.jdbc.Driver";
    public static final String host = "jdbc:mysql://127.0.0.1:3306/test";
    public static final String user = "root";
    public static final String password = "mazafaka";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        return DriverManager.getConnection(host, user, password);
    }
}
