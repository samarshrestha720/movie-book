package com.movie_book.user;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.sql.SQLException;

public class UserController {
    UserService us = new UserService();

    public void registerUserRoutes(Javalin app){
       app.get("/api/user/{email}",  ctx -> getUser(ctx));
    }
    
    private void getUser(Context ctx) throws SQLException{
        try {
            User user = us.getUserByEmail(ctx.pathParam("email"));
            ctx.json(user);
        } catch (Exception e) {
            //log
            System.out.println(e);
            ctx.status(HttpStatus.BAD_REQUEST);
        }
        
        
    }
    
}
