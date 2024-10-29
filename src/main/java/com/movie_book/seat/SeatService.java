/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book.seat;

import com.movie_book.dbConnection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dr. PANDA 002
 */
public class SeatService {

    private DbConnection dbc = new DbConnection(); // Assuming you have a DatabaseConnection class

    // Get all seats for a specific show
    public List<Seat> getAllSeatsForShow(Long showId) throws SQLException {
        List<Seat> seats = new ArrayList<>();
        String query = "SELECT * FROM seat WHERE show_id = ?";
        try (PreparedStatement prstm = dbc.estConnection().prepareStatement(query)) {
            prstm.setLong(1, showId);
            ResultSet rs = prstm.executeQuery();

            while (rs.next()) {
                Seat seat = new Seat();
                seat.setId(rs.getLong("id"));
                seat.setNumber(rs.getString("number"));
                seat.setStatus(rs.getString("status"));
                seat.setShowId(rs.getLong("show_id"));
                seat.setTicketId(rs.getLong("ticket_id"));
                seats.add(seat);
            }
        }

        return seats;
    }

    public Seat getOneSeat(Long id) throws SQLException {
        String query = "SELECT * FROM seat WHERE id  = ?";
        Seat seat = new Seat();
        try (PreparedStatement prstm = dbc.estConnection().prepareStatement(query)) {
            prstm.setLong(1, id);
            ResultSet rs = prstm.executeQuery();
            while (rs.next()) {
                seat.setId(rs.getLong("id"));
                seat.setNumber(rs.getString("number"));
                seat.setStatus(rs.getString("status"));
                seat.setShowId(rs.getLong("show_id"));
                seat.setTicketId(rs.getLong("ticket_id"));
            }
        }
        return seat;
    }

    //Check if the user has any seats selected. i.e. Any rows set to "processing" by user.
    public boolean getProcessingSeatsOfUser(Long showId, int userId) throws SQLException {
        String query = "SELECT * FROM `seat` WHERE show_id = ? AND user_id = ? AND status='processing'";
        try (PreparedStatement prstm = dbc.estConnection().prepareStatement(query)) {
            prstm.setLong(1, showId);
            prstm.setLong(2, userId);
            if (prstm.executeQuery().isBeforeFirst()) {
                return true;
            }
            return false;
        }
    }

    // Add a new seat
    public void addSeat(Seat seat) throws SQLException {
        String query = "INSERT INTO seat (number, status, show_id) VALUES (?, ?, ?)";
        try (PreparedStatement prstm = dbc.estConnection().prepareStatement(query)) {
            prstm.setString(1, seat.getNumber());
            prstm.setString(2, seat.getStatus());
            prstm.setLong(3, seat.getShowId());
            prstm.executeUpdate();
        }
    }

    // Update seat status
    public boolean updateSeatStatus(Long seatId, String status, int userId) throws SQLException {
        String query = "UPDATE `seat` SET status = ?, user_id = ? WHERE id = ?";
        Seat seat = getOneSeat(seatId);

        switch (status) {

            case "available": //Setting status to available

                //check if the seat is booked or not OR check if the seat is already in booking. If already in "available", don't update table.
                if (seat.getStatus().equals("booked") || seat.getStatus().equals("available")) {
                    return false;
                }

                //Check if the user setting available is the user that set seat to "processing"
                if (seat.getId() != seatId) {
                    return false;
                }

                try (PreparedStatement prstm = dbc.estConnection().prepareStatement(query)) {
                    prstm.setString(1, status);
                    prstm.setNull(2, 0);
                    prstm.setLong(3, seatId);
                    if (prstm.executeUpdate() == 1) {
                        return true;
                    }
                    return false;
                }

            case "processing": //setting status to "processing"
                //check if the seat is booked or not OR check if the seat is already in booking. If already in "processing", don't update table.
                if (seat.getStatus().equals("booked") || seat.getStatus().equals("procesing")) {
                    return false;
                }

                try (PreparedStatement prstm = dbc.estConnection().prepareStatement(query)) {
                    prstm.setString(1, status);
                    prstm.setInt(2, userId);
                    prstm.setLong(3, seatId);
                    if (prstm.executeUpdate() == 1) {
                        return true;
                    }
                    return false;
                }
            default:
                System.out.println("Default execute in switch case. Invalid status.");
                return false;
        }
    }

    public boolean bookSeats(int userId, Long showId, Long ticketId) throws SQLException {
        String query = "UPDATE `seat` SET status = ?, ticket_id = ? WHERE user_id = ? AND show_id = ?";
        try (PreparedStatement prstm = dbc.estConnection().prepareStatement(query)) {
            prstm.setString(1, "booked");
            prstm.setLong(2, ticketId);
            prstm.setInt(3, userId);
            prstm.setLong(4, showId);
            if (prstm.executeUpdate() > 0) {
                return true;
            }
            return false;
        }
    }

    // Delete a seat
    public boolean deleteSeat(Long seatId) throws SQLException {
        String query = "DELETE FROM seat WHERE id = ?";
        try (PreparedStatement prstm = dbc.estConnection().prepareStatement(query)) {
            prstm.setLong(1, seatId);
            if (prstm.executeUpdate() == 1) {
                return true;
            }
            return false;
        }
    }

}
