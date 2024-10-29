/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book.show;

import com.movie_book.Role;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 *
 * @author Dr. PANDA 002
 */
public class ShowController {

    ShowService showService = new ShowService();

    public void registerShowRoutes(Javalin app) {
        // Defining routes using lambda expressions
        app.get("/api/show", ctx -> getAllShowsByMovieId(ctx), Role.anyone);
        app.get("/api/show/{id}", ctx -> getShowById(ctx), Role.anyone);
        app.post("/api/show", ctx -> addShow(ctx), Role.admin);
        app.put("/api/show/{id}", ctx -> updateShow(ctx), Role.admin);
        app.delete("/api/show/{id}", ctx -> deleteShow(ctx), Role.admin);
    }

    // Get all shows
    private void getAllShowsByMovieId(Context ctx) {
        try {
            Long movieId = Long.parseLong(ctx.queryParam("movieId"));
            List<Show> shows = showService.getShowsByMovieId(movieId);
            if (shows.size() > 0) {
                ctx.status(200).json(shows);
                return;
            }
            ctx.status(404).json("No shows for this movie.");

        } catch (Exception e) {
            ctx.status(500).json("Error fetching shows. Error: " + e);
        }
    }

    // Get a single show by ID
    private void getShowById(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Show show = showService.getShowById(id);
            if (show != null) {
                ctx.status(200).json(show);
            } else {
                ctx.status(404).json("Show not found");
            }
        } catch (Exception e) {
            ctx.status(500).json("Error fetching show. Error: " + e);
        }
    }

    // Add a new show
    private void addShow(Context ctx) {
        try {
            Show show = ctx.bodyAsClass(Show.class);
            showService.addShow(show);
            ctx.status(201).json("Show added successfully");
        } catch (Exception e) {
            ctx.status(500).json("Error adding show. Error: " + e);
        }
    }

    // Update an existing show
    private void updateShow(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Show show = ctx.bodyAsClass(Show.class);
            Show updatedShow = showService.updateShow(id, show);
            if (updatedShow != null) {
                ctx.status(200).json(updatedShow);
                return;
            }
            ctx.status(404).json("No Show with id " + id + " found.");
            return;
        } catch (Exception e) {
            ctx.status(500).json("Error updating show. Error: " + e);
        }
    }

    // Delete a show
    private void deleteShow(Context ctx) {
        try {
            Long id = Long.parseLong(ctx.pathParam("id"));
            if (showService.deleteShow(id)) {
                ctx.status(200).json("Show deleted successfully");
                return;
            }
            ctx.status(404).json("No Show with id " + id + " found.");
            return;

        } catch (Exception e) {
            ctx.status(500).json("Error deleting show. Error: " + e);
        }
    }

}
