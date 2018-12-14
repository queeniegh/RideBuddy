package com.example.queenie.ridebuddy;

import java.sql.Time;
import java.util.Date;

public class Trips {

    public String origin;
    public String destination;
    public long traveldate;
    public long   traveltime1;
    public long   traveltime2;
    public String user;
    public int confirmnum;

    public Trips(String origin, String destination, long traveldate, long traveltime1, long traveltime2, String user, int confirmnum) {
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


