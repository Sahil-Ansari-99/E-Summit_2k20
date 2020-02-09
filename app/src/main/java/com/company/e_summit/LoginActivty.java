package com.company.e_summit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivty extends AppCompatActivity {
    private EditText email, passsword;
    private TextView forgorPassword, register;
    private Button proceed;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        email = findViewById(R.id.login_email);
        passsword = findViewById(R.id.login_password);

        forgorPassword = findViewById(R.id.login_forgot_password);
        register = findViewById(R.id.login_register);
        proceed = findViewById(R.id.login_proceed);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPass = passsword.getText().toString();

                if (!userEmail.isEmpty() && !userPass.isEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                if (user != null) {
                                    if (user.isEmailVerified()) {
                                        getUserData(user.getUid());
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Please verify your email", Toast.LENGTH_LONG).show();
                                    }
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println(e.getMessage());
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter all entries", Toast.LENGTH_LONG).show();
                }
            }
        });

        forgorPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPassActivity.class));
            }
        });
    }

    private void getUserData(String uid) {
        databaseReference = firebaseDatabase.getReference().child("Users");
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirstActivity.currUserData = dataSnapshot.getValue(UserClass.class);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Summit", FirstActivity.currUserData.getSummit());
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
