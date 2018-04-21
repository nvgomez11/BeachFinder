package com.example.nelson.beachfinder;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectedBeach extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        ArrayList<String> chosen_beach_info = new ArrayList<String>();
        MixpanelAPI mixpanel;
        String lat;
        String longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_beach);

        //----------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //---------------------------------------

        //recibe intent
        Intent intent = getIntent();
        chosen_beach_info = intent.getStringArrayListExtra("selected_beach");
        //carga wigets
        ImageView imageView = findViewById(R.id.icon_selected_beach);
        TextView textView = findViewById(R.id.title_selected_beach);
        //carga info de la palya seleccionada
        String url = chosen_beach_info.get(5);
        String title = chosen_beach_info.get(1);
        //set info
        Picasso.get().load(url).into(imageView);
        textView.setText(title);

        lat = chosen_beach_info.get(7);
        longi = chosen_beach_info.get(8);
        Log.d("tata",lat);
        Log.d("tata",longi);


        mixpanel =
                MixpanelAPI.getInstance(getApplicationContext(), "7794ef33d0569cd4c3041a629abcd1ab");

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
            Intent intent = new Intent(this,Profile.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this,About_section.class);
            startActivity(intent);

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

    public void go_pictures_activity(View view){
        JSONObject props = new JSONObject();
        try {
            props.put("Button selected","pictures");
            mixpanel.track("Button pictures selected", props);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(),Pictures.class);
        intent.putStringArrayListExtra("selected_beach",chosen_beach_info);
        startActivity(intent);
    }
    public void go_comments_activity(View view){
        JSONObject props = new JSONObject();
        try {
            props.put("Button selected","comments");
            mixpanel.track("Button comments selected", props);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this,Comments.class);
        intent.putExtra("selected_beach",chosen_beach_info.get(18)); //Pasan la lista de comentarios al activiry comentarios
        startActivity(intent);
    }

    public void go_description_activity(View view){
        JSONObject props = new JSONObject();
        try {
            props.put("Button selected","description");
            mixpanel.track("Button description selected", props);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(),Description.class);
        intent.putStringArrayListExtra("selected_beach",chosen_beach_info);
        startActivity(intent);
    }

    public void go_route_activity(View view){
        JSONObject props = new JSONObject();
        try {
            props.put("Button selected","route");
            mixpanel.track("Button route selected", props);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Intent intent = new Intent(getApplicationContext(),Route.class);
        //intent.putStringArrayListExtra("selected_beach",chosen_beach_info);
        //startActivity(intent);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr="+lat+","+longi));
        startActivity(intent);
    }

    public void go_weather_activity(View view){
        JSONObject props = new JSONObject();
        try {
            props.put("Button selected","weather");
            mixpanel.track("Button weather selected", props);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(getApplicationContext(),Weather.class);
        intent.putStringArrayListExtra("selected_beach",chosen_beach_info);
        startActivity(intent);
    }
}
