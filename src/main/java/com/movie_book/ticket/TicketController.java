/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book.ticket;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 *
 * @author Dr. PANDA 002
 */
public class TicketController {

    TicketService ticketService = new TicketService();

    // Register ticket routes
    public void registerTicketRoutes(Javalin app) {
        app.post("/api/ticket", ctx -> addTicket(ctx));                  // Add a new ticket
        app.get("/api/ticket", ctx -> getAllTickets(ctx));              // Get all tickets
        app.get("/api/ticket/{id}", ctx -> getTicketById(ctx));          // Get a ticket by ID
        app.delete("/api/ticket/{id}", ctx -> deleteTicket(ctx));        // Delete a ticket
    }

    // Add a new ticket
    private void addTicket(Context ctx) {
        Ticket ticket = ctx.bodyAsClass(Ticket.class);
        try {
            ticketService.addTicket(ticket);
            ctx.status(201).json("Ticket added successfully");
        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500).json("DB error. Error: " + e);
        }
    }

    // Get all tickets
    private void getAllTickets(Context ctx) {
        try {
            List<Ticket> tickets = ticketService.getAllTickets();
            ctx.status(200).json(tickets);
        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500).json("DB error. Error: " + e);
        }
    }

    // Get a ticket by ID
    private void getTicketById(Context ctx) {
        long id = Long.parseLong(ctx.pathParam("id"));
        try {
            Ticket ticket = ticketService.getTicketById(id);
            if (ticket != null) {
                ctx.status(200).json(ticket);
            } else {
                ctx.status(404).json("Ticket not found");
            }
        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500).json("DB error. Error: " + e);
        }
    }

    // Delete a ticket
    private void deleteTicket(Context ctx) {
        long id = Long.parseLong(ctx.pathParam("id"));
        try {
            if (ticketService.deleteTicket(id)) {
                ctx.status(200).json("Ticket deleted successfully");
                return;
            }
            ctx.status(404).json("No ticket with id " + id + " found.");
            return;

        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500).json("DB error. Error: " + e);
        }
    }
}
