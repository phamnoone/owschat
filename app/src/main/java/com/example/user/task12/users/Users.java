package com.example.user.task12.users;

/**
 * Created by USER on 5/16/2015.
 */
public class Users {
    private String name;
    private String pass;
    private String email;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    public Users() {
    }

    public Users(String name, String pass, String email) {
        this.name = name;
        this.pass = pass;
        this.email=email;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {

        return name;
    }

    public String getEmail() {
        return email;
    }
}