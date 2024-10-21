package com.movie_book.user;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;

    public User() {

    }
    
    //Entity for getting single user
    public User(int id, String email, String name){
        setId(id);
        setEmail(email);
        setName(name);
    }

    public User(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
