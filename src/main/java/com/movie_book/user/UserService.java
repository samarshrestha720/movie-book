package com.movie_book.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;


import com.movie_book.dbConnection.DbConnection;
import java.sql.ResultSet;

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
    
    public User getUserByEmail(String email) throws SQLException{
        String getQuery = "SELECT * FROM user WHERE email=?";
        PreparedStatement prstm = dbc.estConnection().prepareStatement(getQuery);
        prstm.setString(1,email);
        
        ResultSet result = prstm.executeQuery();
        System.out.println(result);
        User user = new User();
        while(result.next()){
            int uid = result.getInt("id");
            String uname = result.getString("email");
            String uemail = result.getString("name");
            user.setId(uid);
            user.setEmail(uemail);
            user.setName(uname);
        }
        return user;
        
        
    }
}
