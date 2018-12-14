package com.example.queenie.ridebuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;

public class RideConfirmationActivity extends Activity implements View.OnClickListener {

    TextView textViewInfo, textViewConfirm;
    Button buttonHome;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_confirmation);

        mAuth = FirebaseAuth.getInstance();

        textViewInfo = findViewById(R.id.textViewInfo);
        textViewConfirm = findViewById(R.id.textViewConfirm);
        buttonHome=findViewById(R.id.buttonHome);

        buttonHome.setOnClickListener(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    final User thisUser = dataSnapshot.getValue(User.class);
                    if (!thisUser.currentRide.isEmpty()) {
                        DatabaseReference tripRef = database.getReference("Trips").child(thisUser.currentRide);
                        tripRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    Trips thisTrip = dataSnapshot.getValue(Trips.class);
                                    if (thisTrip.confirmnum != 0) {
                                        DateFormat df = new SimpleDateFormat("hh:mm");
                                        String start = df.format(new Date(thisTrip.traveltime1));
                                        String end = df.format(new Date(thisTrip.traveltime2));
                                        textViewConfirm.setText(thisTrip.confirmnum + "");
                                        textViewInfo.setText("From " + thisTrip.origin + "(" + start + ") to " + thisTrip.destination + "(" + end + ").\nPlease note down these trip details, you won't be able to see them again!");
                                        thisUser.currentRide = "";
                                        userRef.setValue(thisUser);
                                    } else {
                                        textViewInfo.setText("No match found yet");
                                    }
                                } else {
                                    Toast.makeText(RideConfirmationActivity.this, "Couldn't find ride", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        // Not waiting for a ride
                    }
                } else {
                    Toast.makeText(RideConfirmationActivity.this, "Could not find User", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {


            }
        });

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
        if(v==buttonHome) {
            Intent intentmenu = new Intent(this, LandingPageActivity.class);
            startActivity(intentmenu);
        }
    }
}
