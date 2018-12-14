package com.example.queenie.ridebuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class CreateActivity extends Activity implements View.OnClickListener {

    Button buttonCreateTwo, buttonCancel, buttonBack;
    EditText editTextName, editTextEmailTwo, editTextPhone, editTextPW, editTextConfirm;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        editTextName = findViewById(R.id.editTextName);
        editTextEmailTwo = findViewById(R.id.editTextEmailTwo);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPW = findViewById(R.id.editTextPW);
        editTextConfirm = findViewById(R.id.editTextConfirm);

        buttonCreateTwo = findViewById(R.id.buttonCreateTwo);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonBack = findViewById(R.id.buttonBack);


        mAuth = FirebaseAuth.getInstance();

        buttonCreateTwo.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        passwordCheck();
    }

    public void passwordCheck() {


        String pass = editTextPW.getText().toString();
        String confirm = editTextConfirm.getText().toString();
        if (confirm != pass) {

            Toast.makeText(this, "Passowrds do not match!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");

        if (v == buttonCreateTwo) {
            mAuth.createUserWithEmailAndPassword(editTextEmailTwo.getText().toString(), editTextPW.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(CreateActivity.this, "Registration Successful - Welcome!", Toast.LENGTH_SHORT).show();

                                String createName = editTextName.getText().toString();
                                String createNumber = editTextPhone.getText().toString();
                                String createEmail = editTextEmailTwo.getText().toString();

                                User newUser = new User(createName, createNumber, createEmail,"");

                                myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(newUser);

                                Intent intentHome = new Intent(CreateActivity.this, LandingPageActivity.class);
                                startActivity(intentHome);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(CreateActivity.this, "Registration Failed - Try Again", Toast.LENGTH_SHORT).show();
                            }
                            //...
                        }
                    });

        } else if (v == buttonCancel) {

            editTextName.setText("");
            editTextEmailTwo.setText("");
            editTextPhone.setText("");
            editTextPW.setText("");
            editTextConfirm.setText("");

        } else if(v==buttonBack) {

            Intent intentEdit = new Intent(this, MainActivity.class);
            startActivity(intentEdit);
        }

    }

}
