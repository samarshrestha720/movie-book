package com.movie_book.dbConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConnection {

    private Properties configure() {
        try {
            String configFilePath = "config.properties";
            FileInputStream propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            prop.load(propsInput);
            return prop;
        } catch (Exception e) {
            System.out.println("Error loading Config Files. Error: " + e);
            return null;
        }
    }

    public Connection estConnection() {
        Properties prop = configure();
        String url = prop.getProperty("DB_URL");
        String user = prop.getProperty("DB_USER");
        String password = prop.getProperty("DB_PASSWORD");
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                System.out.println("Connected to database");
            }
            return con;
        } catch (Exception e) {
            System.out.println("Error connecting to DB. Error: "+e);
            return null;
        }

    }

}
