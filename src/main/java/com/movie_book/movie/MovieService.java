/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book.movie;

import com.movie_book.dbConnection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dr. PANDA 002
 */
public class MovieService {

    DbConnection dbc = new DbConnection();

    // Method to get all movies
    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movie";
        try (Statement stmt = dbc.estConnection().createStatement();) {
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                Movie movie = new Movie();
                movie.setId(result.getInt("id"));
                movie.setName(result.getString("name"));
                movie.setDescription(result.getString("description"));
                movie.setPrice(result.getDouble("price"));
                movie.setPoster_url(result.getString("poster_url"));
                movies.add(movie);
            }
            return movies;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    // Method to get one movie by its ID

    public Movie getMovieById(int id) throws SQLException {
        String query = "SELECT * FROM movie WHERE id = ?";
        try (PreparedStatement stmt = dbc.estConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Movie movie = new Movie();
                movie.setId(result.getInt("id"));
                movie.setName(result.getString("name"));
                movie.setDescription(result.getString("description"));
                movie.setPrice(result.getDouble("price"));
                movie.setPoster_url(result.getString("poster_url"));
                return movie;
            }
            return null;  // Movie not found
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    // Method to add a new movie
    public Movie addMovie(Movie movie) throws SQLException {
        String query = "INSERT INTO movie (name, description, price, poster_url) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = dbc.estConnection().prepareStatement(query)) {
            stmt.setString(1, movie.getName());
            stmt.setString(2, movie.getDescription());
            stmt.setDouble(3, movie.getPrice());
            stmt.setString(4, movie.getPoster_url());
            if(stmt.executeUpdate()>0){
                return movie;
            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    // Method to update a movie
    public Movie updateMovie(Movie movie) throws SQLException {
        String query = "UPDATE movie SET name = ?, description = ?, price = ?, poster_url = ? WHERE id = ?";
        try (PreparedStatement stmt = dbc.estConnection().prepareStatement(query)) {
            stmt.setString(1, movie.getName());
            stmt.setString(2, movie.getDescription());
            stmt.setDouble(3, movie.getPrice());
            stmt.setString(4, movie.getPoster_url());
            stmt.setInt(5, movie.getId());
            if(stmt.executeUpdate()==1){
                return movie;
            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    // Method to delete a movie
    public boolean deleteMovie(int id) throws SQLException {
        String query = "DELETE FROM movie WHERE id = ?";
        try (PreparedStatement stmt = dbc.estConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            if(stmt.executeUpdate()>0){
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

}
