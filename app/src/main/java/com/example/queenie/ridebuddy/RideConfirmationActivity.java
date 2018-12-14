package com.example.queenie.ridebuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;

public class RideConfirmationActivity extends Activity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_confirmation);

        mAuth = FirebaseAuth.getInstance();

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

}
