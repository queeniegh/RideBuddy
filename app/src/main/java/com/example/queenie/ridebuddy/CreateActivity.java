package com.example.queenie.ridebuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateActivity extends Activity implements View.OnClickListener {

    EditText editTextEmailTwo, editTextPW;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        editTextEmailTwo = findViewById(R.id.editTextEmailTwo);
        editTextPW = findViewById(R.id.editTextPW);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {

        mAuth.createUserWithEmailAndPassword(editTextEmailTwo.getText().toString(), editTextPW.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(CreateActivity.this, "Registration Successful - Welcome!", Toast.LENGTH_SHORT).show();
                            //Intent intentHome = new Intent(CreateActivity.this,LandingPageActivity.class) ;
                            //startActivity(intentHome);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateActivity.this, "Registration Failed - Try Again", Toast.LENGTH_SHORT).show();
                        }
                        //...
                    }
                });

    }
}
