package com.example.queenie.ridebuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends Activity implements View.OnClickListener {

    EditText editTextName, editTextPhone, editTextBio, editTextEmail;
    Button buttonUpdateName, buttonUpdatePhone, buttonUpdateBio, buttonUpdateEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTextBio = findViewById(R.id.editTextBio);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);

        buttonUpdateName=findViewById(R.id.buttonUpdateName);
        buttonUpdatePhone=findViewById(R.id.buttonUpdatePhone);
        buttonUpdateBio=findViewById(R.id.buttonUpdateBio);
        buttonUpdateEmail=findViewById(R.id.buttonUpdateEmail);

        buttonUpdateName.setOnClickListener(this);
        buttonUpdatePhone.setOnClickListener(this);
        buttonUpdateBio.setOnClickListener(this);
        buttonUpdateEmail.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }
    @Override
    public void onClick(View v) {

        //Edit Profile Information In Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");

        if (v == buttonUpdateName) {
            String editEmail = editTextEmail.getText().toString();
            final String editName = editTextName.getText().toString();
            Toast.makeText(EditProfileActivity.this,"Name Has Been Updated", Toast.LENGTH_SHORT).show();

            myRef.orderByChild("email").equalTo(editEmail).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String editKey = dataSnapshot.getKey();
                    myRef.child(editKey).child("name").setValue(editTextName);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else  if (v == buttonUpdateEmail){

        }else if (v== buttonUpdatePhone) {

        }else if (v== buttonUpdateBio) {

        }

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
