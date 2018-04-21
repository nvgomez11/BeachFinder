package com.example.nelson.beachfinder;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class NewUser extends AppCompatActivity {
    //---------REQUEST -->GET
    static ArrayList<ArrayList> all_json_users = new ArrayList<ArrayList>();
    RequestQueue mRequestQueue;
    Cache cache;
    Network network;
    JsonArrayRequest jsonArrayRequest;
    //---------

    public void createUser(View view){
        final EditText newName= findViewById(R.id.EditTextInput_name);
        final EditText newLastName= findViewById(R.id.EditTextInput_Lastname);
        final EditText newEmail= findViewById(R.id.EditTextInput_email);
        final EditText newPassword= findViewById(R.id.EditTextInput_password);

        //--------------------Bloque para bajar users de API
        /*URL url=null;
        try {
            URL url2 = new URL("http://localhost:3000/users");
            url=url2;
        }
        catch (Exception e)
        {
            Log.d("ERROR:","No funciono POST ")
        }*/
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        String url = "https://beach-finder.herokuapp.com/users";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                MyData.put("name", newName.getText().toString()); //Add the data you'd like to send to the server.
                MyData.put("last_name", newLastName.getText().toString());
                MyData.put("email", newEmail.getText().toString());
                MyData.put("password", newPassword.getText().toString());

                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);

        Log.d("FIN:","TERMINO COD");


//-------------------- FIN Bloque para bajar users de API
        Toast.makeText(this, "Your account has been created", Toast.LENGTH_SHORT).show();
        final Intent intent = new Intent(this,LoginActivity.class);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(Toast.LENGTH_LONG+1); // As I am using LENGTH_LONG in Toast
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //WebView myWebView = (WebView) findViewById(R.id.webview);
        //myWebView.loadUrl("https://beach-finder.herokuapp.com/users/new");


    }



}
