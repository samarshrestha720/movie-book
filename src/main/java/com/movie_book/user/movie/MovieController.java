/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book.user.movie;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.List;

/**
 *
 * @author Dr. PANDA 002
 */
public class MovieController {

    MovieService movieService = new MovieService();

    public void registerMovieRoutes(Javalin app) {
        app.get("/api/movie", ctx -> getAllMovies(ctx));                 // Get all movies
        app.get("/api/movie/{id}", ctx -> getMovieById(ctx));            // Get a movie by ID
        app.post("/api/movie", ctx -> addMovie(ctx));                   // Add a new movie
        app.put("/api/movie/{id}", ctx -> updateMovie(ctx));             // Update an existing movie
        app.delete("/api/movie/{id}", ctx -> deleteMovie(ctx));          // Delete a movie

    }

    private void getAllMovies(Context ctx) {
        try {
            List<Movie> movies = movieService.getAllMovies();
            ctx.status(200).json(movies);
        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500).json("DB error");
        }
    }

    // Get a single movie by ID
    private void getMovieById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            Movie movie = movieService.getMovieById(id);
            if (movie != null) {
                ctx.status(200).json(movie);
                return;
            }
            ctx.status(404).json("Movie not found");
            return;

        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500).json("DB error");
        }
    }

    // Add a new movie
    private void addMovie(Context ctx) {
        Movie movie = ctx.bodyAsClass(Movie.class);  // Get movie data from request body
        try {
            Movie addedMovie = movieService.addMovie(movie);
            if (addedMovie != null) {
                ctx.status(201).json(addedMovie);
                return;
            }
            ctx.status(HttpStatus.BAD_REQUEST).json("Failed to add movie!");
            return;
        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500).json("DB error: " + e);
        }
    }

    // Update an existing movie
    private void updateMovie(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Movie movie = ctx.bodyAsClass(Movie.class);
        movie.setId(id);  // Set the ID from the path parameter
        try {
            Movie updatedMovie = movieService.updateMovie(movie);
            if (updatedMovie != null) {
                ctx.status(200).json(updatedMovie);
                return;
            }
            ctx.status(404).json("No Movie of id " + id + " found");
            return;

        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500).json("DB error");
        }
    }

    // Delete a movie
    private void deleteMovie(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            if (movieService.deleteMovie(id)) {
                ctx.status(200).json("Movie deleted successfully");
                return;
            }
            ctx.status(404).json("No Movie of id " + id + " found");
            return;
        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500).json("DB error");
        }
    }
}
