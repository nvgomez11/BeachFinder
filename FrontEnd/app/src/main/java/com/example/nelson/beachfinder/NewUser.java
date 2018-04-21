package com.example.nelson.beachfinder;

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

        String url = "https://proyecto1-pruebas.herokuapp.com/users";
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

    private void sendPost() throws Exception {

        String url = "http://localhost:3000/users";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Luis");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "name=Pokemon&last_name=Rusell&nationality=Peruan&profile_picture=pthodfadsf.jpg";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

    }

}
