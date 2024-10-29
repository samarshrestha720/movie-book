package com.movie_book.user;

import com.movie_book.Role;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.sql.SQLException;
import java.util.List;

public class UserController {

    UserService us = new UserService();

    public void registerUserRoutes(Javalin app) {
        app.get("/api/user/{email}", ctx -> getUserByEmail(ctx), Role.admin);
        app.get("/api/user", ctx -> getAllUsers(ctx), Role.admin);
        app.post("/api/user/login", ctx -> loginUser(ctx), Role.anyone);
        app.post("/api/user/logout", ctx -> logOut(ctx), Role.admin, Role.user);

    }

    private void loginUser(Context ctx) {
        try {
            String authRole = us.loginUser(ctx.formParam("email"), ctx.formParam("password"));
            if (authRole != null) {
                UserContext userContext = new UserContext(ctx.formParam("email"), Role.valueOf(authRole));

                System.out.println(userContext.getRole());
                System.out.println(userContext.getEmail());

                //Setting session cookie
                ctx.sessionAttribute("user-context", userContext);
                //Changing Session Id. Why?
                //Reason: it could be wise to change the session id on login, to protect against session fixation attacks
                ctx.req().changeSessionId();

                ctx.status(200).json("Login Success!!");
            } else {
                ctx.status(401).json("Login Failed!");
            }
        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500).json("Internal server error. Error: " + e);
        }
    }

    private void getUserByEmail(Context ctx) throws SQLException {
        try {
            User user = us.getUserByEmail(ctx.pathParam("email"));
            if (user.getName() == null) {
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

    private void getAllUsers(Context ctx) {
        try {
            List<User> users = us.getAllUsers();
            ctx.json(users);
        } catch (Exception e) {
            //log
            System.out.println(e);
            ctx.status(HttpStatus.BAD_REQUEST);
        }
    }

    private void logOut(Context ctx) {
        try {
            //Invalidate Session. 
            //Explaination: if you want to invalidate a session, jetty will clean everything up for you
            ctx.req().getSession().invalidate();
            ctx.status(205).json("Logout Success!");
        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500).json("Failed to Logout. Error: " + e);
        }
    }

}
