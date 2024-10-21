package com.movie_book.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.movie_book.dbConnection.DbConnection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    DbConnection dbc = new DbConnection();

    public UserService() {

    }

    public User addUser(User user) throws SQLException {
        String insertQuery = "INSERT INTO user (name, email, password) VALUES (?, ?, ?);";
        PreparedStatement prstm = dbc.estConnection().prepareStatement(insertQuery);
        prstm.setString(1, user.getName());
        prstm.setString(2, user.getEmail());
        prstm.setString(3, user.getPassword());
        int result = prstm.executeUpdate();
        System.out.println(result);
        return user;
    }

    public User getUserByEmail(String email) throws SQLException {
        String getQuery = "SELECT * FROM user WHERE email=?";
        try (PreparedStatement prstm = dbc.estConnection().prepareStatement(getQuery);) {
            prstm.setString(1, email);

            ResultSet result = prstm.executeQuery();
            System.out.println(result);
            User user = new User();
            while (result.next()) {
                int uid = result.getInt("id");
                String uname = result.getString("email");
                String uemail = result.getString("name");
                user.setId(uid);
                user.setEmail(uemail);
                user.setName(uname);
            }
            return user;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }
    
    // Method to verify user login credentials
    public boolean loginUser(String email, String password) throws SQLException {
        String loginQuery = "SELECT * FROM user WHERE email = ?";
        try(PreparedStatement prstm = dbc.estConnection().prepareStatement(loginQuery);) {
            prstm.setString(1, email);
            ResultSet result = prstm.executeQuery();
            // Check if a user with the provided email exists
        if (result.next()) {
            // Get the stored password (assumed to be hashed) for comparison
            String storedPassword = result.getString("password");

            // If the password matches, return true (login successful)
            if (storedPassword.equals(password)) {
                return true;
            }
        }

        // If no match or incorrect password, return false
        return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        

        

        
    } 
    

    public List<User> getAllUsers() throws SQLException {
        String getQuery = "SELECT * FROM user";
        PreparedStatement prstm = dbc.estConnection().prepareStatement(getQuery);

        ResultSet result = prstm.executeQuery();
        List<User> users = new ArrayList<>();

        // Iterate over the result set and populate the user list
        while (result.next()) {
            User user = new User();
            int uid = result.getInt("id");
            String uname = result.getString("name");
            String uemail = result.getString("email");

            user.setId(uid);
            user.setName(uname);
            user.setEmail(uemail);

            // Add the user to the list
            users.add(user);
        }
        return users;  // Return the list of users
    }
}
