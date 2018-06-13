package com.example.nelson.beachfinder;

import android.os.SystemClock;
import android.util.Log;

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
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by estadm on 12/6/2018.
 */

public class beachSelected {
    private String idRealSelectedBeach;

    private ArrayList<String> chosen_beach_info = new ArrayList<String>();
    private Boolean data=false;


    private String commentsBeachSelected="";
    public Boolean seDescargo=false;


    //Variable para usar Volley para APIs
    private RequestQueue mRequestQueue;
    private Cache cache;
    private Network network;
    private JsonArrayRequest jsonArrayRequest;
    private JsonObjectRequest jsonObjectRequestBeach;

    public String URL_api="https://vuela-tiquicia-airline.herokuapp.com/users";

    public String URL_api_beaches="https://beach-finder.herokuapp.com/beaches";

    private static beachSelected instanceBeach;

    public static beachSelected getInstance(){
        if(instanceBeach == null){
            instanceBeach = new beachSelected();
        }
        return instanceBeach;
    }

    public String getComments(){

        return chosen_beach_info.get(18);
    }


    public void setIdRealForBeach(String pidRealSelectedBeach)
    {
        idRealSelectedBeach=pidRealSelectedBeach;

    }



    public void downloadDataCommentsFromAPi(File getCacheDir) // Pasar getCacheDir()
    {

        //Arrelgar y quitar

        //USER_CREDENTIALS=new ArrayList<>();
        //USER_CREDENTIALS.add("");
        //--------------------Bloque para bajar users de API

        //request_json(activityName);
        //Instantiate the cache
        cache = new DiskBasedCache(getCacheDir, 1024 * 1024); // 1MB cap

        //Set up the network to use HttpURLConnection as the HTTP client.
        network = new BasicNetwork(new HurlStack());
        //Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);
        //Start the queue
        mRequestQueue.start();
        // Initialize a new JsonArrayRequest instance
        jsonObjectRequestBeach = new JsonObjectRequest(
                Request.Method.GET,
                //ip de la maquina, cel y compu deben estar en misma red
                URL_api_beaches+"/"+idRealSelectedBeach+".json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        // Do something with response
                        // Process the JSON
                        Log.d("mop",response.toString());
                        try{
                            // Loop through the array elements
                                // Get current json object
                                //JSONObject beach = response.getJSONObject();
                                //lista donde sera guardada la info
                                ArrayList<String> json_beach = new ArrayList<String>();
                                String id = response.getString("id");
                                Log.d("cola:",id);
                                String beachName = response.getString("beach_name");
                                Log.d("cola2:",beachName);
                                String location = response.getString("location");
                                String sand = response.getString("sand_color");
                                String description = response.getString("description");
                                String main_image = response.getString("main_image");
                                String secondary_image = response.getString("secondary_image");
                                String latitude = response.getString("latitude");
                                String longitude = response.getString("longitude");
                                String wave = response.getString("wave_type");
                                String snorkeling = response.getString("snorkeling");
                                String swimming = response.getString("swimming");
                                String shade = response.getString("shade");
                                String night_life = response.getString("night_life");
                                String camping_zone = response.getString("camping_zone");
                                String protected_area = response.getString("protected_area");
                                String cristal_water = response.getString("cristal_water");
                                String vegetation = response.getString("vegetation");
                                String comments = response.getString("comments");
                            Log.d("cola3:",comments);
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

                                commentsBeachSelected=comments;

                                seDescargo=true;
                        }catch (JSONException e){
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.d("Error","No pudo entrar al API /users: "+error);
                    }
                }

        );

        SystemClock.sleep(3000);
        // Adding request to request queue
        mRequestQueue.add(jsonObjectRequestBeach);


//-------------------- FIN Bloque para bajar users de API
        //return true;
    }



    public String downloadDataCommentsFromAPi2(File getCacheDir) // Pasar getCacheDir()
    {

        //Arrelgar y quitar

        //USER_CREDENTIALS=new ArrayList<>();
        //USER_CREDENTIALS.add("");
        //--------------------Bloque para bajar users de API

        //request_json(activityName);
        //Instantiate the cache
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(URL_api_beaches);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert urlConnection != null;
            urlConnection.disconnect();
        }
        return result.toString();
    }







    public String getIdRealForBeach() {
        return idRealSelectedBeach;
    }



    public ArrayList<String> getChosen_beach_info() {
        return chosen_beach_info;
    }

    public void setChosen_beach_info(ArrayList<String> pChosen_beach_info) {

        for(int i=0;i< pChosen_beach_info.size();i++)
        {
            chosen_beach_info.add(pChosen_beach_info.get(i));

        }
    }

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean pData) {
        data = pData;
    }

    public String getCommentsBeachSelected() {
        return commentsBeachSelected;
    }

    public void setCommentsBeachSelected(String commentsBeachSelected) {
        this.commentsBeachSelected = commentsBeachSelected;
    }
}
