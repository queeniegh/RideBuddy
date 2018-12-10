package com.example.queenie.ridebuddy;

import java.sql.Time;
import java.util.Date;

public class Trips {

    String origin;
    String destination;
    Date traveldate;
    Time traveltime;

    public Trips(String origin, String destination, Date traveldate, Time traveltime) {
        this.origin = origin;
        this.destination = destination;
        this.traveldate = traveldate;
        this.traveltime = traveltime;
    }

    public Trips() {
    }
}


