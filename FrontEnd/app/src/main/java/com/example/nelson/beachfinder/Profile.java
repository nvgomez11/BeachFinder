package com.example.nelson.beachfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String url;
    EditText textName;
    EditText textLastName;
    EditText textViewNationality;
    EditText textViewPhone;
    EditText textViewEmail;



    UsersController userData;

    public void updateProfile(View view)
    {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);


        StringRequest MyStringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("name", textName.getText().toString()); //Add the data you'd like to send to the server.
                MyData.put("last_name", textLastName.getText().toString());
                MyData.put("nationality", textViewNationality.getText().toString());
                MyData.put("phone_number", textViewPhone.getText().toString());
                MyData.put("email", textViewEmail.getText().toString());

                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);

        Toast.makeText(this, "Your information has been updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //--------------------------------------------------
        //UsuarioSesion userSession = UsuarioSesion.getInstance();
        userData=UsersController.getInstance();

        //Log.d("Name USER",userSession.getNameUser().toString());

        int idUsuario=Integer.parseInt(userData.getIdSession());

        url = "https://beach-finder.herokuapp.com/users"+"/"+idUsuario; //Donde se va a actualizar

         textName= findViewById(R.id.editTextName);
         textLastName= findViewById(R.id.EditTextLastName);
         textViewNationality= findViewById(R.id.EditTextNationality);
         textViewPhone= findViewById(R.id.EditTextPhone);
         textViewEmail= findViewById(R.id.EditTextViewEmail);

        textName.setText(userData.getNameSession());
        textLastName.setText(userData.getLast_nameSession());
        textViewNationality.setText(userData.getNationalitySession());
        textViewPhone.setText(userData.getPhone_numberSession());
        Log.d("Emai ES:",userData.getEmailSession());
        textViewEmail.setText(userData.getEmailSession());
        String urlImageProfile=userData.getProfile_pictureSession();


        //Agregar foto de perfil a View image
        ImageView image = findViewById(R.id.imageProfile);
        Picasso.get().load(urlImageProfile).into(image);
        //----------------------------------------
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
}
