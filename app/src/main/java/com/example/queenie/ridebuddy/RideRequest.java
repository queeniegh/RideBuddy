package com.example.queenie.ridebuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Random;

public class RideRequest extends Activity implements View.OnClickListener {

    Button buttonSubmitRide;
    EditText editTextOrigin, editTextDestination, editTextDate, editTextTimeStart, editTextTimeEnd;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_request);

        buttonSubmitRide = findViewById(R.id.buttonSubmitRide);
        editTextDate = findViewById(R.id.editTextDate);
        editTextOrigin = findViewById(R.id.editTextOrigin);
        editTextDestination = findViewById(R.id.editTextDestination);
        editTextTimeStart = findViewById(R.id.editTextTimeStart);
        editTextTimeEnd = findViewById(R.id.editTextTimeEnd);

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Trips");

        if (v == buttonSubmitRide) {



            String createOrigin = editTextOrigin.getText().toString();
            String createDestination = editTextDestination.getText().toString();
            String createTravelTime1 = editTextTimeStart.getText().toString();
            String createTravelDate = editTextDate.getText().toString();
            String createTravelTime2 = editTextTimeEnd.getText().toString();
            String createEmail = mAuth.getCurrentUser().getEmail();
            Random r = new Random();
            int confirmnum = r.nextInt(999999);
            Trips newTrip = new Trips(createOrigin, createDestination, createTravelDate, createTravelTime1, createTravelTime2, createEmail, confirmnum);

            myRef.push().setValue(newTrip);

            myRef.orderByChild("origin").equalTo(createOrigin).orderByChild("destination").equalTo(createDestination).orderByChild("time").equalToaddChildEventListener(new ChildEventListener()

                    Intent intentConfirm = new Intent(RideRequest.this, RideConfirmationActivity.class);
            startActivity(intentConfirm);








        }
    }
}
