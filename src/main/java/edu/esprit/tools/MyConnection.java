package edu.esprit.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private String URL="jdbc:mysql://localhost:3306/pi";
    private String USER="root";
    private String PWD="";

Connection cnx;
    public static MyConnection instance;
    private MyConnection() throws SQLException {
        try {
            cnx = DriverManager.getConnection(URL,USER,PWD);
            System.out.println("Connexion établie !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public void setCnx(Connection cnx) {
        this.cnx = cnx;
    }

    public static MyConnection getInstance() throws SQLException {
        if(instance==null){
            instance=new MyConnection();
        }
        return instance;
    }
}
