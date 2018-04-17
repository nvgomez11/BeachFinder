package com.example.nelson.beachfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        ArrayList <String> datos=LoginActivity.getUSER_CREDENTIALS();
        TextView textView= findViewById(R.id.textViewName);
        textView.setText("Name: "+datos.get(1));


    }
}
