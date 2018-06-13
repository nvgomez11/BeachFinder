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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;

public class About_section extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button costaRica;
    Button us;

    String costaRica_description = "Costa Rica is as well known for its beautiful natural landscape and bustling biodiversity as it is for its wonderful, happy residents. Here are 16 interesting facts about Costa Rica that help make the country a truly singular place. Tourists and locals alike are drawn to Costa Rica’s natural beauty—and are committed to preserving it. With 20 national parks, 8 biological reserves, animal refuges, and protected areas, 26 percent of Costa Rica’s land is protected. All that natural beauty and the diverse landscape with two oceans and access to countless adventure activities have made Costa Rica a great vacation destination. In 1995, tourism overtook bananas to become Costa Rica’s leading foreign exchange earner. Tourism reached an all-time high for Costa Rica in 2013 with 2.4 million visitors. The United Nations Educational, Scientific and Cultural Organization (UNESCO) has designated four locations in Costa Rica as World Heritage Sites for their universal cultural and natural value. They are: La Amistad National Park, Cocos Island National Park, Area de Conservación Guanacaste, and the Precolumbian Chiefdom Settlements with Stone Spheres of the Diquís. Costa Rica dissolved its national army in 1948, and the abolition of the military was written into the national constitution in 1949. Twenty-one countries, including the United States, signed the Inter-American Treaty of Reciprocal Assistance in 1947, pledging to provide military support to Costa Rica (and any other signee) should they need it. In 1980, the United Nations University for Peace was created and housed in Costa Rica. According to the World Bank, Costa Rica's life expectancy at birth is 80 years. This figure is higher than that of the United States (which is 79). The Nicoya region of Costa Rica is also one of five Blue Zones—“longevity hotspots” populated by the longest-living people in the world—on the globe. All that natural beauty and happiness must be good for you!. Of these, approximately 112 have shown some type of activity—60 are considered dormant, which means they don't currently show signs of activity, but could possibly become active again. Arenal is the most active volcano in Central America, while Poás is the second widest volcanic crater in the world, and Irazú is Costa Rica’s tallest volcano. At 19,730 square miles, Costa Rica occupies slightly less territory than Lake Michigan (which measures 22,394 square miles). The country contains 801 miles (1,290 km) of coastline. Costa Rica may not be a large country, but it packs a lot of life into its borders. While Costa Rica only occupies .03 percent of the world's surface, it boasts the globe's highest biodiversity density. The country is home to more than 500,000 species! And, with nearly 3 percent of the world’s biodiversity contained in its borders, Corcovado National Park has been deemed the most biologically intense place on the planet. Seriously—there are so many butterflies. Costa Rica contains approximately 90 percent of the butterfly species found in Central America, 66 percent of all neo-tropical butterflies, and about 18 percent of all butterfly species in the world.";

    private ImageView imageUser;
    private TextView nameUser;
    private TextView emailUser;
    private View navHeader;


    UsersController userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_section);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //-----------------------------------------------------
        //---------Para conectar a google silenciosamente y cargar foto nombre email
        navHeader = navigationView.getHeaderView(0);
        imageUser = (ImageView) navHeader.findViewById(R.id.imageViewGoogle_user);
        nameUser = (TextView) navHeader.findViewById(R.id.nameGoogle_user);
        emailUser = (TextView) navHeader.findViewById(R.id.emailGoogle_user);
        //-------------------------------

        costaRica = findViewById(R.id.Costa_rica_btn);
        us = findViewById(R.id.us_btn);

        costaRica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),About_selected.class);
                intent.putExtra("title","Costa Rica");
                intent.putExtra("desc",costaRica_description);
                startActivity(intent);
            }
        });

        us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),About_selected.class);
                intent.putExtra("title","Us");
                intent.putExtra("desc","BeachFinder is an application developed by students of Costa Rica Institute of Technology. Version 1.0. This app will be a great tool which help you to find the best places to stay in Costa Rica");
                startActivity(intent);
            }
        });


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
}
