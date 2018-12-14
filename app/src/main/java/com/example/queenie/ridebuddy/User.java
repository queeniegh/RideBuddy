package com.example.queenie.ridebuddy;

public class User {

    public String name;
    public String phone;
    public String email;
    public String bio;
    public String currentRide;

    public User(String name, String phone, String email, String bio) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.bio = bio;
        currentRide = "";
    }

    public User() {
    }
}
