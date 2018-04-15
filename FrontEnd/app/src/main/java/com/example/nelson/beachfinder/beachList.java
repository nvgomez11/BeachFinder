package com.example.nelson.beachfinder;

import android.content.Context;
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

    static ArrayList<String> data_selected_radioButton;

    ArrayList<ArrayList> all_beaches_apply = new ArrayList<ArrayList>();

    ArrayList<String> beaches_titles = new ArrayList<String>();
    ArrayList<String> beaches_descriptions = new ArrayList<String>();
    ArrayList<String> beaches_icons = new ArrayList<String>();

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
        GridView gridView  = findViewById(R.id.grid_view);
        Intent intent = getIntent();
        String activityName = intent.getStringExtra("activity_name");

        if(activityName.compareTo("search")==0){
            data_selected_radioButton = AdvancedSearch.data_selected_list;
            if(data_selected_radioButton.isEmpty()!=true){
                Intent intentBeachWritten_name = getIntent();
                //nombre que escribio la persona de la playa para buscar
                String beachName = intentBeachWritten_name.getStringExtra("beachWritten_name");
                for(int i=0; i<data_selected_radioButton.size();i++){
                    Log.d("Vengo de search",data_selected_radioButton.get(i));
                }
                Log.d("Vengo de search",beachName.toString());
                select_the_only_beach_apply(beachName);
            }
        }else{
            data_selected_radioButton = AdvancedSearch.data_selected_list;
            if(data_selected_radioButton.isEmpty()!=true){
                for(int i=0; i<data_selected_radioButton.size();i++){
                    Log.d("Vengo de advanced",data_selected_radioButton.get(i));
                }
            }
            select_all_beaches_that_apply();
        }

        fill_titles_desc_icon();
        for(int i=0; i<beaches_titles.size(); i++){
            Log.d("met_beaches",beaches_titles.get(i));
        }

        GridAdapter adapter = new GridAdapter(this,beaches_titles,beaches_descriptions);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();
            }
        });


        Log.d("edward",String.valueOf(SearchActivity.all_json_beaches.size()));
        for(int i=0;i<SearchActivity.all_json_beaches.size();i++){
            ArrayList<String> listita =  SearchActivity.all_json_beaches.get(i);
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

    public void select_the_only_beach_apply(String beachName){
        ArrayList<String> single_beach_apply = new ArrayList<String>();
        for(int i = 0; i<SearchActivity.all_json_beaches.size();i++){
            ArrayList beach = SearchActivity.all_json_beaches.get(i);
            String beachName_inJson = String.valueOf(beach.get(1));
            String beachName_loweCase = beachName.toLowerCase();
            String beachName_inJson_loweCase = beachName_inJson.toLowerCase();
            if(beachName_loweCase.compareTo(beachName_inJson_loweCase)==0){
                all_beaches_apply.add(beach);
                break;
            }
        }
    }

    public void select_all_beaches_that_apply(){
        for(int i = 0; i<SearchActivity.all_json_beaches.size();i++){
            ArrayList beach = SearchActivity.all_json_beaches.get(i);

            String location= String.valueOf(beach.get(2));
            String sand= String.valueOf(beach.get(3));
            String tide= String.valueOf(beach.get(9));
            String swimming= string_to_bool(String.valueOf(beach.get(11)));
            String night_life= string_to_bool(String.valueOf(beach.get(13)));
            String camping= string_to_bool(String.valueOf(beach.get(14)));
            String shade= string_to_bool(String.valueOf(beach.get(12)));
            String vegetation= String.valueOf(beach.get(17));
            String snork= string_to_bool(String.valueOf(beach.get(10)));
            String protected_area= string_to_bool(String.valueOf(beach.get(15)));
            String cristal= string_to_bool(String.valueOf(beach.get(16)));

            String location_selected = data_selected_radioButton.get(0);
            String sand_selected = data_selected_radioButton.get(1);
            String tide_selected = data_selected_radioButton.get(2);
            String swimming_selected = data_selected_radioButton.get(3);
            String nightlife_selected = data_selected_radioButton.get(4);
            String camping_selected = data_selected_radioButton.get(5);
            String shade_selected = data_selected_radioButton.get(6);
            String vegetation_selected = data_selected_radioButton.get(7);
            String snork_selected = data_selected_radioButton.get(8);
            String protected_area_selected = data_selected_radioButton.get(9);
            String cristal_selected = data_selected_radioButton.get(10);

            if((location.compareTo(location_selected)==0) || (location_selected.compareTo("Any")==0)){
                if((sand.compareTo(sand_selected)==0) || (sand_selected.compareTo("Any")==0)){
                    if((tide.compareTo(tide_selected)==0) || (tide_selected.compareTo("Any")==0)){
                        if((swimming.compareTo(swimming_selected)==0) || (swimming_selected.compareTo("Any")==0)){
                            if((night_life.compareTo(nightlife_selected)==0) || (nightlife_selected.compareTo("Any")==0)){
                                if((camping.compareTo(camping_selected)==0) || (camping_selected.compareTo("Any")==0)){
                                    if((shade.compareTo(shade_selected)==0) || (shade_selected.compareTo("Any")==0)){
                                        if((vegetation.compareTo(vegetation_selected)==0) || (vegetation_selected.compareTo("Any")==0)){
                                            if((snork.compareTo(snork_selected)==0) || (snork_selected.compareTo("Any")==0)){
                                                if((protected_area.compareTo(protected_area_selected)==0) || (protected_area_selected.compareTo("Any")==0)){
                                                    if((cristal.compareTo(cristal_selected)==0) || (cristal_selected.compareTo("Any")==0)){
                                                        all_beaches_apply.add(beach);
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
        }
    }

    void fill_titles_desc_icon(){
        for(int i=0;i<all_beaches_apply.size();i++){
            ArrayList beach = all_beaches_apply.get(i);
            String title = beach.get(1).toString();
            String description = beach.get(4).toString();
            beaches_titles.add(title);
            beaches_descriptions.add(description);
        }
    }

    String string_to_bool(String b){
        if(b.toString().compareTo("true")==0){
            return "Yes";
        }
        if(b.toString().compareTo("false")==0){
            return "No";
        }
        return "Any";
    }
}
