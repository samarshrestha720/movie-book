/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book;

import com.movie_book.user.UserContext;
import io.javalin.http.Context;
import io.javalin.http.Header;
import io.javalin.http.UnauthorizedResponse;

/**
 *
 * @author Dr. PANDA 002
 */
public class Auth {

    public static void handleAccess(Context ctx) {
        var permittedRoles = ctx.routeRoles();
        if (permittedRoles.contains(Role.anyone)) {
            return; // anyone can access
        }
        UserContext userContext = ctx.sessionAttribute("user-context");
        if (userContext == null) {
            ctx.header(Header.WWW_AUTHENTICATE, "Basic");
            throw new UnauthorizedResponse();
        }

        if (permittedRoles.contains(userContext.getRole())) {
            System.out.println("Role granted: " + userContext.getRole());
            return;
        }

        ctx.header(Header.WWW_AUTHENTICATE, "Basic");
        throw new UnauthorizedResponse();
    }

}
