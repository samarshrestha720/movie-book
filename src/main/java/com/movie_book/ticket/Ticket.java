/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book.ticket;

import java.time.LocalDateTime;

/**
 *
 * @author Dr. PANDA 002
 */
public class Ticket {
    private long id;
    private int userId;
    private long showId;
    private LocalDateTime booked_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getShowId() {
        return showId;
    }

    public void setShowId(long showId) {
        this.showId = showId;
    }

    public LocalDateTime getBooked_at() {
        return booked_at;
    }

    public void setBooked_at(LocalDateTime booked_at) {
        this.booked_at = booked_at;
    }
    
}
