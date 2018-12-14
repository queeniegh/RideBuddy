package com.example.queenie.ridebuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RideRequest extends Activity implements View.OnClickListener {

    Button buttonSubmitRide;
    EditText editTextOrigin, editTextDestination, editTextDate, editTextTimeStart, editTextTimeEnd;
    CalendarView calendarViewSetDate;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_request);

        buttonSubmitRide = findViewById(R.id.buttonSubmitRide);
        //editTextDate = findViewById(R.id.editTextDate);
        editTextOrigin = findViewById(R.id.editTextOrigin);
        editTextDestination = findViewById(R.id.editTextDestination);
        editTextTimeStart = findViewById(R.id.editTextTimeStart);
        editTextTimeEnd = findViewById(R.id.editTextTimeEnd);

        calendarViewSetDate = findViewById(R.id.calendarViewSetDate);

        mAuth = FirebaseAuth.getInstance();

        buttonSubmitRide.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuitemhome) {
            Intent intentmenu = new Intent(this, LandingPageActivity.class);
            startActivity(intentmenu);
        } else if (item.getItemId() == R.id.menuitemnewride) {
            Intent intentmenu = new Intent(this, RideRequest.class);
            startActivity(intentmenu);
        } else if (item.getItemId() == R.id.menuitemcheckstatus) {
            Intent intentmenu = new Intent(this, RideStatusActivity.class);
            startActivity(intentmenu);
        } else if (item.getItemId() == R.id.menuitemlogout) {
            FirebaseAuth.getInstance().signOut();
            Intent intentmenu = new Intent(this, MainActivity.class);
            startActivity(intentmenu);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Trips");

        if (v == buttonSubmitRide) {



            String createOrigin = editTextOrigin.getText().toString();
            String createDestination = editTextDestination.getText().toString();
            String createTravelTime1 = editTextTimeStart.getText().toString();
            long createTravelDate = calendarViewSetDate.getDate();
            String createTravelTime2 = editTextTimeEnd.getText().toString();
            String createEmail = mAuth.getCurrentUser().getEmail();
//            Random r = new Random();
//            int confirmnum = r.nextInt(999999);
            int confirmnum = 0;
            DateFormat df = new SimpleDateFormat("hh:mm");
            Date time1, time2;
            try {
                time1 = df.parse(createTravelTime1);
                time2 = df.parse(createTravelTime2);
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
            final Trips newTrip = new Trips(createOrigin, createDestination, createTravelDate, time1.getTime(), time2.getTime(), createEmail, confirmnum);

            // myRef.push().setValue(newTrip);

           // myRef.orderByChild("origin").equalTo(createOrigin).orderByChild("destination").equalTo(createDestination).orderByChild("time").equalToaddChildEventListener(new ChildEventListener());

//            Intent intentConfirm = new Intent(RideRequest.this, RideConfirmationActivity.class);
//            startActivity(intentConfirm);

            // Step 1: Read from the database
            // Step 2: loop through the Trips in the database
            // Step 3: find trips with the same date, start location, end location, and similar start/end time

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(final DataSnapshot child : dataSnapshot.getChildren()) {
                        Trips trip = child.getValue(Trips.class);
                        // Find an unconfirmed trip with the same date, start location, end location, and similar start/end time
                        boolean isNotConfirmed = (trip.confirmnum == 0);

                        boolean sameDate = (trip.traveldate == newTrip.traveldate);
                        boolean sameOrigin = (trip.origin.equals(newTrip.origin));
                        boolean sameDestination = (trip.destination.equals(newTrip.destination));

                        boolean similarStart = (Math.abs(trip.traveltime1 - newTrip.traveltime1) < 1800000);
                        boolean similarEnd = (Math.abs(trip.traveltime2 - newTrip.traveltime2)< 1800000);
                        // If you find such a trip, you want to mark this request as resolved?
                        if(isNotConfirmed && sameDate && similarStart && sameOrigin && sameDestination && similarEnd) {
                            // Mark the request as resolved, send confirmations to both users
                            trip.traveltime1 = Math.max(trip.traveltime1, newTrip.traveltime1);
                            trip.traveltime2 = Math.min(trip.traveltime2, newTrip.traveltime2);
                            Random r = new Random();
                            trip.confirmnum = r.nextInt(999999 - 100000) + 100000; // Generate 6 digit confirmnum
                            child.getRef().setValue(trip);
                            final DatabaseReference userRef = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        User thisUser = dataSnapshot.getValue(User.class);
                                        thisUser.currentRide = child.getRef().getKey();
                                        userRef.setValue(thisUser);
                                    } else {
                                        Toast.makeText(RideRequest.this, "User not found", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            // Display to both users that the trip was confirmed
                            return; // Ends the loop
                        }
                    }

                    // Got through the whole for loop, didn't find a matching ride.
                    // Add new ride to the database.
                    final DatabaseReference newTripRef = myRef.push();
                    // newTripRef.setValue(newTrip);

                    final DatabaseReference userRef = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                User thisUser = dataSnapshot.getValue(User.class);
                                if (thisUser.currentRide.isEmpty()) {
                                    thisUser.currentRide = newTripRef.getKey();
                                    userRef.setValue(thisUser);
                                    newTripRef.setValue(newTrip);
                                } else {
                                    Toast.makeText(RideRequest.this, "You are already waiting for a ride!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RideRequest.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });






        }
    }
}
