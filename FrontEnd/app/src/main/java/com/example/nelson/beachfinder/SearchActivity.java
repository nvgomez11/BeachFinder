package com.example.nelson.beachfinder;

import android.content.Intent;
import android.media.MediaCas;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    AutoCompleteTextView txtField;
    static String beachNameWritten = "";

    static ArrayList<ArrayList> all_json_beaches = new ArrayList<ArrayList>();
    RequestQueue mRequestQueue;
    Cache cache;
    Network network;
    JsonArrayRequest jsonArrayRequest;

    int idActiveUser=0;

    private GoogleApiClient googleApiClient;
    private ImageView imageUser;
    private TextView nameUser;
    private TextView emailUser;
    private View navHeader;
    UsersController userData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        all_json_beaches.clear();
        txtField=findViewById(R.id.auto_comp_text);
        txtField.setText("");
        Button search_button = findViewById(R.id.button_search);

        //-------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //-------------------------------------

        //-----------------------------------------------------
        //---------Para conectar a google silenciosamente y cargar foto nombre email
        navHeader = navigationView.getHeaderView(0);
        imageUser = (ImageView) navHeader.findViewById(R.id.imageViewGoogle_user);
        nameUser = (TextView) navHeader.findViewById(R.id.nameGoogle_user);
        emailUser = (TextView) navHeader.findViewById(R.id.emailGoogle_user);




        MixpanelAPI mixpanel =
                MixpanelAPI.getInstance(getApplicationContext(), "7794ef33d0569cd4c3041a629abcd1ab");



    }
    @Override
    protected void onResume() {
        super.onResume();

        //Se descarga la informacion sobre los usuario nuevamente
        userData = UsersController.getInstance();
        if (userData.getUserSessionState())
        {
            Log.d("Rino", "Va a descargar User del start");
            userData = UsersController.getInstance();
            userData.downloadDataFromAPi(getCacheDir());
            SystemClock.sleep(3000);
            userData.setSessionUser(userData.getIdSession());

            nameUser.setText(userData.getNameSession());
            emailUser.setText(userData.getEmailSession());
            //para cargar la foto de la persona
            if (userData.getProfile_pictureSession() == "null") {
                Log.d("perro", "foto es null");
                imageUser.setImageResource(R.drawable.beach_icon);
            }
            else
            {
                Glide.with(this).load(userData.getProfile_pictureSession()).into(imageUser);
            }

            //
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
                    "https://beach-finder.herokuapp.com/beaches.json",
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
                                    String comments = beach.getString("comments");
                                    //Son 19 datos de playas, es decir comments es el 18 empezando de 0
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
                                    json_beach.add(swimming);
                                    json_beach.add(shade);
                                    json_beach.add(night_life);
                                    json_beach.add(camping_zone);
                                    json_beach.add(protected_area);
                                    json_beach.add(cristal_water);
                                    json_beach.add(vegetation);
                                    json_beach.add(comments);

                                    Log.d("Avion", "comentario tiene:"+comments);
                                    if (comments.equals(""))
                                    {
                                        Log.d("Avion", "tiene comentarios vacios:"+beachName);
                                    }
                                    if(location.length()!=4){
                                        all_json_beaches.add(json_beach);
                                    }
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

            SystemClock.sleep(3000);
            // Adding request to request queue
            mRequestQueue.add(jsonArrayRequest);

        }
        else {

            //Login con FB
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

    public void goBeachListActivity(View view){
        txtField = findViewById(R.id.auto_comp_text);
        beachNameWritten = String.valueOf(txtField.getText());

        if( txtField.getText().toString().trim().equals(""))
        {
            txtField.setError( "Beach name is required!" );

            txtField.setHint("Type a beach name");
        } else {
            Intent intent = new Intent(this,beachList.class);
            intent.putExtra("activity_name","search");
            intent.putExtra("beachWritten_name", txtField.getText().toString());
            AdvancedSearch.data_selected_list.clear();
            AdvancedSearch.data_selected_list.add("Any");
            AdvancedSearch.data_selected_list.add("Any");
            AdvancedSearch.data_selected_list.add("Any");
            AdvancedSearch.data_selected_list.add("Any");
            AdvancedSearch.data_selected_list.add("Any");
            AdvancedSearch.data_selected_list.add("Any");
            AdvancedSearch.data_selected_list.add("Any");
            AdvancedSearch.data_selected_list.add("Any");
            AdvancedSearch.data_selected_list.add("Any");
            AdvancedSearch.data_selected_list.add("Any");
            AdvancedSearch.data_selected_list.add("Any");
            startActivity(intent);
        }

    }
    public void goAdvancedSearchActivity(View view){
        Intent intent = new Intent(this,AdvancedSearch.class);
        startActivity(intent);
    }
}
