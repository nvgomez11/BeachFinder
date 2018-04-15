package com.example.nelson.beachfinder;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class beachList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<ArrayList> all_json_beaches = new ArrayList<ArrayList>();
    RequestQueue mRequestQueue;
    Cache cache;
    Network network;
    JsonArrayRequest jsonArrayRequest;
    static ArrayList<String> data_selected_radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //------------------------------------
        Intent intent = getIntent();
        String activityName = intent.getStringExtra("activity_name");


        if(activityName.compareTo("search")==0){
            Log.d("Mae",SearchActivity.beachNameWritten);
            data_selected_radioButton = AdvancedSearch.data_selected_list;
            if(data_selected_radioButton.isEmpty()!=true){
                for(int i=0; i<data_selected_radioButton.size();i++){
                    Log.d("Mae",data_selected_radioButton.get(i));
                }
            }
        }else{
            data_selected_radioButton = AdvancedSearch.data_selected_list;
            if(data_selected_radioButton.isEmpty()!=true){
                for(int i=0; i<data_selected_radioButton.size();i++){
                    Log.d("Mae",data_selected_radioButton.get(i));
                }
            }
        }

        //request_json(activityName);
        //Instantiate the cache
        cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        //Set up the network to use HttpURLConnection as the HTTP client.
        network = new BasicNetwork(new HurlStack());
        //Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);
        //Start the queue
        mRequestQueue.start();
        // Initialize a new JsonArrayRequest instance
        jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                //ip de la maquina, cel y compu deben estar en misma red
                "http://192.168.0.8:3000/beaches.json",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        // Process the JSON
                        Log.d("mop",response.toString());
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject beach = response.getJSONObject(i);
                                //lista donde sera guardada la info
                                ArrayList<String> json_beach = new ArrayList<String>();
                                String id = beach.getString("id");
                                String beachName = beach.getString("beach_name");
                                String location = beach.getString("location");
                                String sand = beach.getString("sand_color");
                                String description = beach.getString("description");
                                String main_image = beach.getString("main_image");
                                String secondary_image = beach.getString("secondary_image");
                                String latitude = beach.getString("latitude");
                                String longitude = beach.getString("longitude");
                                String wave = beach.getString("wave_type");
                                String snorkeling = beach.getString("snorkeling");
                                String swimming = beach.getString("swimming");
                                String shade = beach.getString("shade");
                                String night_life = beach.getString("night_life");
                                String camping_zone = beach.getString("camping_zone");
                                String protected_area = beach.getString("protected_area");
                                String cristal_water = beach.getString("cristal_water");
                                String vegetation = beach.getString("vegetation");
                                Log.d("mop",id);
                                Log.d("mop",beachName);
                                Log.d("mop",location);
                                Log.d("mop",sand);
                                Log.d("mop",description);
                                Log.d("mop",latitude);
                                Log.d("mop",longitude);
                                Log.d("mop",wave);
                                Log.d("mop",snorkeling);
                                Log.d("mop",swimming);
                                Log.d("mop",shade);
                                Log.d("mop",night_life);
                                Log.d("mop",camping_zone);
                                Log.d("mop",protected_area);
                                Log.d("mop",cristal_water);
                                Log.d("mop",vegetation);
                                json_beach.add(id);
                                json_beach.add(beachName);
                                json_beach.add(location);
                                json_beach.add(sand);
                                json_beach.add(description);
                                json_beach.add(main_image);
                                json_beach.add(secondary_image);
                                json_beach.add(latitude);
                                json_beach.add(longitude);
                                json_beach.add(wave);
                                json_beach.add(snorkeling);
                                json_beach.add(shade);
                                json_beach.add(night_life);
                                json_beach.add(camping_zone);
                                json_beach.add(protected_area);
                                json_beach.add(cristal_water);
                                json_beach.add(vegetation);
                                all_json_beaches.add(json_beach);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.d("Error","No pudo entrar al link");
                    }
                }

        );
        // Adding request to request queue
        mRequestQueue.add(jsonArrayRequest);

        Log.d("edward",String.valueOf(all_json_beaches.size()));
        for(int i=0;i<all_json_beaches.size();i++){
            ArrayList<String> listita =  all_json_beaches.get(i);
            for(int j=0;j<listita.size();j++){
                Log.d("oso",listita.get(j));
            }
        }

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

    public void go_selected_beach(View view){
        Intent intent = new Intent(this,SelectedBeach.class);
        startActivity(intent);
    }





    /*void compare_features(String id,String beachName,String location,String sand,String description,String main_image, String secondary_image,
                          String latitude,String longitude,String wave,String snorkeling,String swimming,String shade, String nightlife,
                          String camping_zone,String protected_area,String cristal_water,String vegetation){

        if((data_selected_radioButton.get(0).compareTo(location)==0) || data_selected_radioButton.get(0).compareTo("Any")==0){
            if((data_selected_radioButton.get(1).compareTo(sand)==0) || data_selected_radioButton.get(1).compareTo("Any")==0){
                if((data_selected_radioButton.get(2).compareTo(wave)==0) || data_selected_radioButton.get(2).compareTo("Any")==0){
                    if((data_selected_radioButton.get(3).compareTo(swimming)==0) || data_selected_radioButton.get(3).compareTo("Any")==0){
                        if((data_selected_radioButton.get(4).compareTo(nightlife)==0) || data_selected_radioButton.get(4).compareTo("Any")==0){
                            if((data_selected_radioButton.get(5).compareTo(camping_zone)==0) || data_selected_radioButton.get(5).compareTo("Any")==0){
                                if((data_selected_radioButton.get(6).compareTo(shade)==0) || data_selected_radioButton.get(6).compareTo("Any")==0){
                                    if((data_selected_radioButton.get(7).compareTo(vegetation)==0) || data_selected_radioButton.get(7).compareTo("Any")==0){
                                        if((data_selected_radioButton.get(8).compareTo(snorkeling)==0) || data_selected_radioButton.get(8).compareTo("Any")==0){
                                            if((data_selected_radioButton.get(9).compareTo(protected_area)==0) || data_selected_radioButton.get(9).compareTo("Any")==0){
                                                if((data_selected_radioButton.get(10).compareTo(cristal_water)==0) || data_selected_radioButton.get(10).compareTo("Any")==0){
                                                    json_beach.clear();
                                                    json_beach.add(id);
                                                    json_beach.add(beachName);
                                                    json_beach.add(location);
                                                    json_beach.add(sand);
                                                    json_beach.add(description);
                                                    json_beach.add(main_image);
                                                    json_beach.add(secondary_image);
                                                    json_beach.add(latitude);
                                                    json_beach.add(longitude);
                                                    json_beach.add(wave);
                                                    json_beach.add(snorkeling);
                                                    json_beach.add(shade);
                                                    json_beach.add(nightlife);
                                                    json_beach.add(camping_zone);
                                                    json_beach.add(protected_area);
                                                    json_beach.add(cristal_water);
                                                    json_beach.add(vegetation);
                                                    beaches_right_features.add(json_beach);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }*/
}
