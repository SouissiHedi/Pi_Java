package edu.esprit.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private static String URL = "jdbc:mysql://localhost:3306/pi";
    private static String USER = "root";
    private static String PWD = "";
    private static MyConnection instance;
    private Connection cnx;

    private MyConnection() throws SQLException {
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Connexion établie !");
        } catch (SQLException e) {
            throw new RuntimeException("Error establishing connection", e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static synchronized MyConnection getInstance() throws SQLException {
        if (instance == null || instance.getCnx().isClosed()) {
            instance = new MyConnection();
        }
        return instance;
    }

    public static void reconnect() throws SQLException {
        if (instance != null && !instance.getCnx().isClosed()) {
            instance.getCnx().close();
        }
        instance = new MyConnection();
    }
}
