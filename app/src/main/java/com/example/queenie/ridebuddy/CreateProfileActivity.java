package com.example.queenie.ridebuddy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class CreateProfileActivity extends Activity {

    Button buttonCreateTwo;
    Button buttonCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
    }
}
