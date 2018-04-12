package com.example.nelson.beachfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

public class AdvancedSearch extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<RadioGroup> radioGroup_list = new ArrayList<RadioGroup>();
    ArrayList<String> data_selected_list = new ArrayList<String>();
    String location,sandColor,tide,vegetation;
    int swimming,nightlife,camping,shade, snorkeling,protectedArea,clearWater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //------------------------------------
        RadioGroup radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup_list.add(radioGroup1);
        RadioGroup radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup_list.add(radioGroup2);
        RadioGroup radioGroup3 = findViewById(R.id.radioGroup3);
        radioGroup_list.add(radioGroup3);
        RadioGroup radioGroup4 = findViewById(R.id.radioGroup4);
        radioGroup_list.add(radioGroup4);
        RadioGroup radioGroup5 = findViewById(R.id.radioGroup5);
        radioGroup_list.add(radioGroup5);
        RadioGroup radioGroup6 = findViewById(R.id.radioGroup6);
        radioGroup_list.add(radioGroup6);
        RadioGroup radioGroup7 = findViewById(R.id.radioGroup7);
        radioGroup_list.add(radioGroup7);
        RadioGroup radioGroup8 = findViewById(R.id.radioGroup8);
        radioGroup_list.add(radioGroup8);;
        RadioGroup radioGroup9 = findViewById(R.id.radioGroup9);
        radioGroup_list.add(radioGroup9);
        RadioGroup radioGroup10 = findViewById(R.id.radioGroup10);
        radioGroup_list.add(radioGroup10);
        RadioGroup radioGroup11 = findViewById(R.id.radioGroup11);
        radioGroup_list.add(radioGroup11);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this,SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(this,MapsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_sign_out) {
            //closes facebook session de facebook
            LoginManager.getInstance().logOut();
            //se va al activity del login
            Intent intent = new Intent(this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goBeachListActivity(View view){
        for(int i=0;i<radioGroup_list.size();i++){
            int radio_btn_id = radioGroup_list.get(i).getCheckedRadioButtonId();
            View radioButton = radioGroup_list.get(i).findViewById(radio_btn_id);
            int radioId = radioGroup_list.get(i).indexOfChild(radioButton);
            RadioButton btn = (RadioButton) radioGroup_list.get(i).getChildAt(radioId);
            String selection = (String) btn.getText();
            data_selected_list.add(selection);
        }

        for(int n=0; n<data_selected_list.size();n++){
            Log.d("Mae", data_selected_list.get(n));
        }

        Intent intent = new Intent(this,beachList.class);
        startActivity(intent);
    }



}
