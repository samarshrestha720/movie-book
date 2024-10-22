/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book.show;


import java.time.LocalDateTime;

/**
 *
 * @author Dr. PANDA 002
 */
public class Show {
    private long id;               // Unique identifier for the show
    private LocalDateTime time;    // Timestamp for the show
    private String movieHall;      // Name of the movie hall
    private long movieId;          // Foreign key referencing Movie table

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getMovieHall() {
        return movieHall;
    }

    public void setMovieHall(String movieHall) {
        this.movieHall = movieHall;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
    
}
