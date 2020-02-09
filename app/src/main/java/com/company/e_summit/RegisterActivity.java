package com.company.e_summit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private Spinner spinner;
    private EditText email, password, confirmPassword;
    private Button submit;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        spinner = findViewById(R.id.register_spinner);

        List<String> summits = new ArrayList<>();
        summits.add("Select Registered Summit");
        summits.add("Youth Summit");
        summits.add("Innovator's Summit");
        summits.add("Startup Summit");
        summits.add("Techno-Entrepreneurship Summit");

        final ArrayAdapter<String>  dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, summits);
        spinner.setAdapter(dataAdapter);
        spinner.setPrompt("Select Registered Summit");

        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.register_confirm_password);
        submit = findViewById(R.id.register_submit);

        progressDialog = new ProgressDialog(RegisterActivity.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailText = email.getText().toString();
                final String passwordText = password.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();
                final String selectedSummit = String.valueOf(spinner.getSelectedItem());

                if (!emailText.isEmpty() && !passwordText.isEmpty() && !confirmPasswordText.isEmpty() && passwordText.equals(confirmPasswordText) && !selectedSummit.equals("Select Registered Summit")) {
                    progressDialog.setMessage("Adding user...");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (progressDialog.isShowing()) progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                FirebaseUser user = task.getResult().getUser();
                                if (user != null) {
                                    UserClass registeredUser = new UserClass(user.getEmail(), selectedSummit);
                                    addToDB(registeredUser, user.getUid());
                                    user.sendEmailVerification();
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setTitle("Success");
                                builder.setMessage("Please check your email for verification!");
                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivty.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                progressDialog.dismiss();
                                AlertDialog dialog =builder.create();
                                dialog.show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    });
                } else if (emailText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_LONG).show();
                } else if (!passwordText.equals(confirmPasswordText)) {
                    Toast.makeText(getApplicationContext(), "Passwords donot match", Toast.LENGTH_LONG).show();
                } else if (selectedSummit.equals("Select Registered Summit")) {
                    Toast.makeText(getApplicationContext(), "Please select summit", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void addToDB(UserClass user, String uid) {
        databaseReference = firebaseDatabase.getReference().child("Users");
        databaseReference.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) System.out.println("User added successfully");
                else System.out.println("User addition failed");
            }
        });
    }
}
