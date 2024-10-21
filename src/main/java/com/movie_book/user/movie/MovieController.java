/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book.user.movie;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 *
 * @author Dr. PANDA 002
 */
public class MovieController {
    public void registerMovieRoutes(Javalin app){
        app.get("/api/movie", ctx->getAllMovies(ctx));
    }

    private void getAllMovies(Context ctx) {
        
    }
}
