/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.movie_book;

import io.javalin.security.RouteRole;

/**
 *
 * @author Dr. PANDA 002
 */
public enum Role implements RouteRole {
    admin, user, anyone
};
