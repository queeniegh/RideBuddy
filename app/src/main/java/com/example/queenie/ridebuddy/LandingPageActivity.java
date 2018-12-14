package com.example.queenie.ridebuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LandingPageActivity extends Activity implements View.OnClickListener {

    Button buttonStatus;
    Button buttonRequest;
    Button buttonEdit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);


        buttonEdit= findViewById(R.id.buttonEdit);
        buttonRequest = findViewById(R.id.buttonRequest);
        buttonStatus = findViewById(R.id.buttonStatus);

        buttonEdit.setOnClickListener(this);
        buttonRequest.setOnClickListener(this);
        buttonStatus.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {

        if (v == buttonEdit) {
            Intent intentEdit = new Intent(this, EditProfileActivity.class);
            startActivity(intentEdit);

        } else if (v == buttonRequest) {
            Intent intentRequest = new Intent(this, RideRequest.class );
            startActivity(intentRequest);


        } else if (v == buttonStatus){
            Intent intentStatus = new Intent(this,RideConfirmationActivity.class );
           startActivity(intentStatus);


        }

    }
}
