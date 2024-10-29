/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movie_book.user;

import com.movie_book.Role;

/**
 *
 * @author Dr. PANDA 002
 */
public class UserContext {
    
    private String email;
    private Role role;
    
    public UserContext(String email, Role role){
        setEmail(email);
        setRole(role);
    }
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
}
