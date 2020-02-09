package com.company.e_summit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public static UserClass currUserData;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            getUserData(firebaseUser.getUid());
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivty.class);
            startActivity(intent);
            finish();
        }
    }

    private void getUserData(String uid) {
        databaseReference = firebaseDatabase.getReference().child("Users");
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currUserData = dataSnapshot.getValue(UserClass.class);
                changeView(currUserData.getSummit());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void changeView(String summit) {
        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        if (!preferences.contains("summit")) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("summit", summit.toLowerCase());
            editor.apply();
        }
        else if (!preferences.getString("summit", "youth summit").equals(summit.toLowerCase())) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("summit", summit.toLowerCase());
            editor.apply();
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("Summit", summit);
        startActivity(intent);
        finish();
    }
}
