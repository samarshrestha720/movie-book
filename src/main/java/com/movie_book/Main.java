package com.movie_book;

import com.movie_book.user.User;

import com.movie_book.dbConnection.TableCreateService;
import com.movie_book.user.UserController;
import com.movie_book.user.UserService;
import com.movie_book.movie.MovieController;
import com.movie_book.seat.SeatController;
import com.movie_book.show.ShowController;
import com.movie_book.ticket.TicketController;

import io.javalin.Javalin;


public class Main {

    
    public static void main(String[] args) {
        System.out.println("Starting Server");
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.anyHost();
                });
            });
            config.jetty.modifyServletContextHandler(handler
                    -> {
                handler.setSessionHandler(SessionUtil.fileSessionHandler());
                //Setting Session max time.
                handler.getServletContext().getSessionCookieConfig().setMaxAge(7200); //Set to 2 hours
            }
            );

            config.router.mount(router -> {
                router.beforeMatched(Auth::handleAccess);
            });

        })
                .get("/", ctx -> {
                    ctx.sessionAttribute("my-key", "My value");
                    String myValue = ctx.sessionAttribute("my-key");
                    System.out.println(myValue);
                    ctx.result("Hello World. Value: " + myValue);

                }, Role.anyone)
                .start(7070);

        //register routes
        UserController userController = new UserController();
        userController.registerUserRoutes(app);
        MovieController movieController = new MovieController();
        movieController.registerMovieRoutes(app);
        ShowController showController = new ShowController();
        showController.registerShowRoutes(app);
        TicketController ticketController = new TicketController();
        ticketController.registerTicketRoutes(app);
        SeatController seatController = new SeatController();
        seatController.registerSeatRoutes(app);

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
