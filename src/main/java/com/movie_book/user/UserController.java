package com.movie_book.user;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    UserService us = new UserService();

    public void registerUserRoutes(Javalin app){
       app.get("/api/user/{email}",  ctx -> getUserByEmail(ctx));
       app.get("/api/user", ctx->getAllUsers(ctx));
       app.post("/api/user/login", ctx->loginUser(ctx));
    }
    
    private void loginUser(Context ctx){
        try {
           boolean authStatus = us.loginUser(ctx.formParam("email"), ctx.formParam("password"));
           if(authStatus){
               ctx.status(200).json("Login Success!!");
           }
           else{
               ctx.status(401).json("Login Failed!");
           }
        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500).json("Internal server error.");
        }
    }
    
    private void getUserByEmail(Context ctx) throws SQLException{
        try {
            User user = us.getUserByEmail(ctx.pathParam("email"));
            if(user.getName()==null){
                ctx.status(HttpStatus.NOT_FOUND);
                return;
            }
            ctx.json(user);
            
        } catch (Exception e) {
            //log
            System.out.println(e);
            ctx.status(HttpStatus.BAD_REQUEST);
        }
    }
    
    private void getAllUsers(Context ctx){
        try {
            List<User> users = us.getAllUsers();
            ctx.json(users);
        } catch (Exception e) {
            //log
            System.out.println(e);
            ctx.status(HttpStatus.BAD_REQUEST);
        }
    }
    
}
