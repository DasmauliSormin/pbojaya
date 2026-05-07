package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/classswap_db";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn =
                DriverManager.getConnection(URL, USER, PASSWORD);

            return conn;

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }

    }
}
//mutiara