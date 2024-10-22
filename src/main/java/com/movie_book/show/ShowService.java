/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book.show;

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
public class ShowService {

    private DbConnection dbc = new DbConnection();

    // Get all shows
    public List<Show> getAllShows() throws SQLException {
        List<Show> shows = new ArrayList<>();
        String query = "SELECT * FROM `show`;";

        try (Statement stmt = dbc.estConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Show show = new Show();
                show.setId(rs.getLong("id"));
                show.setTime(rs.getTimestamp("time").toLocalDateTime());
                show.setMovieHall(rs.getString("movie_hall"));
                show.setMovieId(rs.getLong("movie_id"));  // Get foreign key
                shows.add(show);
            }
        }
        return shows;
    }

    // Get a single show by ID
    public Show getShowById(long id) throws SQLException {
        String query = "SELECT * FROM `show` WHERE id=?";
        Show show = new Show();

        try (PreparedStatement stmt = dbc.estConnection().prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                show.setId(rs.getLong("id"));
                show.setTime(rs.getTimestamp("time").toLocalDateTime());
                show.setMovieHall(rs.getString("movie_hall"));
                show.setMovieId(rs.getLong("movie_id"));
            }
        }
        return show;
    }

    // Add a new show
    public void addShow(Show show) throws SQLException {
        String query = "INSERT INTO `show` (time, movie_hall, movie_id) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = dbc.estConnection().prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(show.getTime())); // Set current time
            stmt.setString(2, show.getMovieHall());
            stmt.setLong(3, show.getMovieId());
            stmt.executeUpdate();
        }
    }

    // Update an existing show
    public Show updateShow(long id, Show show) throws SQLException {
        String query = "UPDATE `show` SET time=?, movie_hall=?, movie_id=? WHERE id=?";

        try (PreparedStatement stmt = dbc.estConnection().prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(show.getTime()));
            stmt.setString(2, show.getMovieHall());
            stmt.setLong(3, show.getMovieId());
            stmt.setLong(4, id);
            if (stmt.executeUpdate() == 1) {
                return show;
            }
            return null;
        }
    }

    // Delete a show by ID
    public boolean deleteShow(long id) throws SQLException {
        String query = "DELETE FROM `show` WHERE id=?";

        try (PreparedStatement stmt = dbc.estConnection().prepareStatement(query)) {
            stmt.setLong(1, id);
            if(stmt.executeUpdate()==1){
                return true;
            }
            return false;
        }
    }

}
