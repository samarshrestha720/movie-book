package com.movie_book;

import com.movie_book.user.User;

import com.movie_book.dbConnection.TableCreateService;
import com.movie_book.user.UserController;
import com.movie_book.user.UserService;
import com.movie_book.user.movie.MovieController;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Server");
        var app = Javalin.create(/* config */)
                .get("/", ctx -> ctx.result("Hello World"))
                .start(7070);

        UserController uc = new UserController();
        uc.registerUserRoutes(app);
        MovieController mc = new  MovieController();
        mc.registerMovieRoutes(app);
        
        
        
        app.get("/create-seat-table", ctx -> {
            TableCreateService tcs = new TableCreateService();
            tcs.createSeatTable();
            ctx.result("Completed!");
        });
        app.get("/deletetable/{tableName}", ctx -> {
            TableCreateService tcs = new TableCreateService();
            int result = tcs.deleteTable(ctx.pathParam("tableName"));
            if (result == 0) {
                ctx.result("Deleted!");
            } else {
                ctx.result("Failed!");
            }
        });

        app.post("/api/user/add", ctx -> {
            UserService us = new UserService();
            User user = ctx.bodyAsClass(User.class);

            User resultUser = us.addUser(user);
            if (resultUser != null) {
                ctx.result(resultUser.toString());
            }

        });
    }
}