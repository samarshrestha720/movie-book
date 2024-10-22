/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book.ticket;

import com.movie_book.dbConnection.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dr. PANDA 002
 */
public class TicketService {
    private DbConnection dbc = new DbConnection();

    // Method to add a ticket
    public void addTicket(Ticket ticket) throws SQLException {
        String insertQuery = "INSERT INTO tickets (user_id, show_id, booked_at) VALUES (?, ?, ?)";
        try (PreparedStatement prstm = dbc.estConnection().prepareStatement(insertQuery)) {
            prstm.setInt(1, ticket.getUserId());
            prstm.setLong(2, ticket.getShowId());
            prstm.setTimestamp(3, Timestamp.valueOf(ticket.getBooked_at()));
            prstm.executeUpdate();
        }
    }

    // Method to get all tickets
    public List<Ticket> getAllTickets() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT * FROM tickets";
        try (Statement stmt = dbc.estConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getLong("id"));
                ticket.setUserId(rs.getInt("user_id"));
                ticket.setShowId(rs.getLong("show_id"));
                ticket.setBooked_at(rs.getTimestamp("booked_at").toLocalDateTime());
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    // Method to get a ticket by ID
    public Ticket getTicketById(long id) throws SQLException {
        String query = "SELECT * FROM tickets WHERE id = ?";
        try (PreparedStatement prstm = dbc.estConnection().prepareStatement(query)) {
            prstm.setLong(1, id);
            ResultSet result = prstm.executeQuery();
            if (result.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(result.getLong("id"));
                ticket.setUserId(result.getInt("user_id"));
                ticket.setShowId(result.getLong("show_id"));
                ticket.setBooked_at(result.getTimestamp("booked_at").toLocalDateTime());
                return ticket;
            }
        }
        return null; // or throw an exception if not found
    }

    // Method to delete a ticket
    public boolean deleteTicket(long id) throws SQLException {
        String deleteQuery = "DELETE FROM tickets WHERE id = ?";
        try (PreparedStatement prstm = dbc.estConnection().prepareStatement(deleteQuery)) {
            prstm.setLong(1, id);
            if(prstm.executeUpdate()==1){
                return true;
            }
            return false;
        }
    }
}
