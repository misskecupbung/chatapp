package com.ananda.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //button
    Button mdaftarbutton, mmasukbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



     // init views
     mdaftarbutton = findViewById(R.id.daftar_button);
     mmasukbutton = findViewById(R.id.masuk_button);

     //handler register button click
     mdaftarbutton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             // Start register activity
             startActivity(new Intent(MainActivity.this, DaftarActivity.class));

         }
     });

    }
}