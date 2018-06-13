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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by estadm on 12/6/2018.
 */

public class beachSelected {
    private String idRealSelectedBeach;

    private ArrayList<String> chosen_beach_info = new ArrayList<String>();
    private Boolean data=false;


    //Variable para usar Volley para APIs
    private RequestQueue mRequestQueue;
    private Cache cache;
    private Network network;
    private JsonArrayRequest jsonArrayRequest;

    public String URL_api="https://vuela-tiquicia-airline.herokuapp.com/users";

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
        jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                //ip de la maquina, cel y compu deben estar en misma red
                URL_api+".json",
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
                                JSONObject user = response.getJSONObject(i);
                                //lista donde sera guardada la info
                                ArrayList<String> json_user = new ArrayList<String>();
                                String id = user.getString("id");
                                String name = user.getString("name");
                                String last_name = user.getString("last_name");
                                String email = user.getString("email");
                                String password = user.getString("password");
                                String profile_picture = user.getString("profile_picture");
                                String id_flights = user.getString("id_flights");
                                String record_kilometers = user.getString("record_kilometers");


                                //Log.d("JSON_VAR:",profile_picture+"pruebaetc");

                                json_user.add(id);
                                json_user.add(name);
                                json_user.add(last_name);
                                json_user.add(email);
                                String tempPassword=(password);
                                json_user.add(tempPassword);
                                json_user.add(profile_picture);
                                json_user.add(id_flights);
                                json_user.add(record_kilometers);




                                //all_json_users.add(json_user);


                                //Actualizar todos los credenciales para el login
                                //USER_CREDENTIALS.add(json_user.get(0)+":"+json_user.get(3)+":"+json_user.get(4));
                                //USER_Data.add(json_user.get(1)+":"+json_user.get(2)+":"+json_user.get(4)+":"+json_user.get(5)+":"+json_user.get(6));
                                //Log.d("USERS-JSON:",USER_CREDENTIALS.get(i));
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
                        Log.d("Error","No pudo entrar al API /users: "+error);
                    }
                }

        );

        SystemClock.sleep(3000);
        // Adding request to request queue
        mRequestQueue.add(jsonArrayRequest);


//-------------------- FIN Bloque para bajar users de API
        //return true;
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
}
