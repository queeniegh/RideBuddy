package com.example.queenie.ridebuddy;

import java.sql.Time;
import java.util.Date;

public class Trips {

    String origin;
    String destination;
    String traveldate;
    String traveltime1;
    String traveltime2;
    String user;
    int confirmnum;

    public Trips(String origin, String destination, String traveldate, String traveltime1, String traveltime2, String user, int confirmnum) {
        this.origin = origin;
        this.destination = destination;
        this.traveldate = traveldate;
        this.traveltime1 = traveltime1;
        this.traveltime2 = traveltime2;
        this.user = user;
        this.confirmnum = confirmnum;
    }

    public Trips() {
    }
}


