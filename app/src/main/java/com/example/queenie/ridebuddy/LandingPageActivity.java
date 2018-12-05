package com.example.queenie.ridebuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LandingPageActivity extends Activity implements View.OnClickListener {

    Button buttonStatus;
    Button buttonRequest;
    Button buttonEdit;

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
            Intent intentStatus = new Intent(this,RideStatusActivity.class );
           startActivity(intentStatus);
           

        }

    }
}
