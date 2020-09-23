package com.ananda.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
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

import java.util.regex.Pattern;

public class DaftarActivity extends AppCompatActivity {

    //views
    EditText memailEt, mpasswordEt;
    Button mdaftarButton;
    TextView mpunyaakuntv;

    //progress bar to display while registering user
    ProgressDialog progressDialog;

    // Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);


    // Action bar and its title
     ActionBar actionBar = getSupportActionBar();
     actionBar.setTitle("Buat Akun");

     //Enable back button
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowHomeEnabled(true);

      //init
        memailEt = findViewById(R.id.emailEt);
        mpasswordEt = findViewById(R.id.passwordEt);
        mdaftarButton = findViewById(R.id.daftar_button);
        mpunyaakuntv = findViewById(R.id.punya_akunTv);

        // In the onCreate() method, initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Mendaftar User...");

        //handle register button
        mdaftarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input email dan password
                String email = memailEt.getText().toString().trim();
                String password = mpasswordEt.getText().toString().trim();
                //validate
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    //set error and focus to email and edit text
                    memailEt.setError("Email Salah");
                    memailEt.setFocusable(true);
                }
                else if (password.length()<6){
                    //set error and focus to password and edit text
                    mpasswordEt.setError("Password setidaknya harus 6 karakter");
                    mpasswordEt.setFocusable(true);
                }
                else {
                    daftaruser(email, password);

                }


            }
        });

        //handle login textview click listener
        mpunyaakuntv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DaftarActivity.this, LoginActivity.class));
            }
        });

    }

    private void daftaruser(String email, String password) {
        //email dan password valid, show progress dan daftarkan user

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, dismiss dialog and start register activity
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(DaftarActivity.this, "Terdaftar ...\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DaftarActivity.this, ProfileActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(DaftarActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //error, dismiss progress dialog and get and show the error message
                progressDialog.dismiss();
                Toast.makeText(DaftarActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go previous activity
        return super.onSupportNavigateUp();
    }
}