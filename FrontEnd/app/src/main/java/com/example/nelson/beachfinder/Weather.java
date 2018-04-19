package com.example.nelson.beachfinder;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Weather extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> chosen_beach_info = new ArrayList<String>();
    TextView city;
    TextView country;
    TextView temperature;
    TextView description;

    RequestQueue mRequestQueue;
    Cache cache;
    Network network;
    JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ///-------------------------------------------------
        Intent intent = getIntent();
        chosen_beach_info = intent.getStringArrayListExtra("selected_beach");

        city = findViewById(R.id.txt_city);
        country = findViewById(R.id.txt_country);
        temperature = findViewById(R.id.txt_temp);
        description = findViewById(R.id.description);

        find_weather();
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

            startActivity(intent);

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this,Profile.class);
            startActivity(intent);

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

    public void find_weather(){
        String latitud = chosen_beach_info.get(7);
        String longitud = chosen_beach_info.get(8);
        Log.d("rata",latitud);
        Log.d("longitud",longitud);
        String url = "http://api.openweathermap.org/data/2.5/weather?lat="+latitud+"&lon="+longitud+"&appid=d9669c9eb24cfb93cbed4b939102704e";

        cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        //Set up the network to use HttpURLConnection as the HTTP client.
        network = new BasicNetwork(new HurlStack());
        //Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);
        //Start the queue
        mRequestQueue.start();
        // Initialize a new JsonArrayRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://api.openweathermap.org/data/2.5/weather?lat="+latitud+"&lon="+longitud+"&appid=d9669c9eb24cfb93cbed4b939102704e",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO: handle success
                        try {
                            JSONObject main = response.getJSONObject("main");
                            JSONArray weather = response.getJSONArray("weather");
                            JSONObject sys = response.getJSONObject("sys");
                            JSONObject description_object = weather.getJSONObject(0);

                            String temperatura_json = main.getString("temp");
                            String humidity_json = main.getString("humidity");
                            String description_str = description_object.getString("description");
                            String pais = sys.getString("country");

                            float float_num = Float.valueOf(temperatura_json);
                            float total = float_num - 273.15f;
                            String final_temperature = String.valueOf(Math.round(total));

                            Log.d("rata",final_temperature);
                            Log.d("rata",humidity_json);
                            Log.d("rata",description_str);
                            Log.d("rata",pais);
                            Log.d("rata",response.getString("name"));

                            temperature.setText(final_temperature);
                            city.setText(response.getString("name"));
                            description.setText(description_str);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("rata","error");
                        }
                        Log.d("rata","funco");
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //TODO: handle failure
                        Log.d("Error","No pudo entrar al link");
                    }
                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }
}
