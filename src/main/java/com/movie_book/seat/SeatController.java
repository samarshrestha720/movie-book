/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book.seat;

import com.movie_book.Role;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Dr. PANDA 002
 */
public class SeatController {

    private SeatService seatService = new SeatService();

    public void registerSeatRoutes(Javalin app) {
        app.get("/api/seat", ctx -> getAllSeatsForShow(ctx), Role.anyone);
        app.get("/api/seat/{seatId}", ctx -> getOneSeat(ctx), Role.anyone);
        app.post("/api/seat", ctx -> addSeat(ctx), Role.admin);
        app.put("/api/seat/{seatId}", ctx -> updateSeatStatus(ctx), Role.user);
        app.delete("/api/seat/{seatId}", ctx -> deleteSeat(ctx), Role.admin);
    }

    private void getAllSeatsForShow(Context ctx) {

        try {
            Long showId = Long.parseLong(ctx.queryParam("showId"));
            List<Seat> seats = seatService.getAllSeatsForShow(showId);
            if (seats.size() > 0) {
                ctx.status(200).json(seats);
                return;
            }
            ctx.status(404).json("No seats for this show.");
            return;
        } catch (Exception e) {
            ctx.status(500).json("Database error: " + e.getMessage());
        }
    }

    private void addSeat(Context ctx) {
        try {
            Seat seat = ctx.bodyAsClass(Seat.class);
            seatService.addSeat(seat);
            ctx.status(201).json("Seat added successfully");
        } catch (SQLException e) {
            ctx.status(500).json("Database error: " + e.getMessage());
        }
    }

    private void updateSeatStatus(Context ctx) {
        try {
            Long seatId = Long.valueOf(ctx.pathParam("seatId"));
            String status = ctx.queryParam("status"); // Expecting status as a query parameter
            int userId = Integer.parseInt(ctx.queryParam("userId")); // Expecting status as a query parameter
            if (seatService.updateSeatStatus(seatId, status, userId)) {
                ctx.status(200).json("Seat status updated successfully");
                return;
            }
            ctx.status(404).json("Seat with id " + seatId + " not found");
            return;

        } catch (SQLException e) {
            ctx.status(500).json("Database error: " + e.getMessage());
        }
    }

    private void deleteSeat(Context ctx) {
        Long seatId = Long.valueOf(ctx.pathParam("id"));
        try {
            if (seatService.deleteSeat(seatId)) {
                ctx.status(200).json("Seat deleted successfully");
                return;
            }
            ctx.status(404).json("Seat with id " + seatId + " not found");
            return;
        } catch (SQLException e) {
            ctx.status(500).json("Database error: " + e.getMessage());
        }
    }

    private void getOneSeat(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("seatId"));
        try {
            Seat seat = seatService.getOneSeat(id);
            if (seat.getId() != null) {
                ctx.status(200).json(seat);
                return;
            }
            ctx.status(404).json("Seat With id " + id + " not found.");
            return;

        } catch (SQLException e) {
            ctx.status(500).json("Database error: " + e);
            System.out.println(e);
            return;
        }

    }
}
