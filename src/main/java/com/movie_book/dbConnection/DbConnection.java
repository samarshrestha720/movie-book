package com.movie_book.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    public Connection estConnection() {
        String url = "jdbc:mysql://localhost:3306/movie-book";
        String user = "samar";
        String password = "samar";
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                System.out.println("Connected to database");
            }
            return con;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

}
