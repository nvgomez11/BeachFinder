package com.example.nelson.beachfinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.EditText;
import android.widget.GridView;
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

public class Comments extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> beach_CommentAutor = new ArrayList<String>();
    ArrayList<String> beach_comment = new ArrayList<String>();

     EditText author;
     EditText comment;
     EditText luis;


    UsersController usersData;
    beachSelected beachData;//Almacena playa selecionada

    GridView gridViewComments;
    GridCommentsAdapter adapterComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridViewComments  = findViewById(R.id.grid_viewComment);




    }
    @Override
    public void onStart()
    {
        super.onStart();
        beachSelected beachData=beachSelected.getInstance();

        String comentariosLista[]=beachData.getComments().split("_");
        //En este momento comentarios del API tiene forma así:
        //[Autor:Comentario|Autor2:Comentario2]

        for (String comentario:comentariosLista) {
            String tempComent[]=comentario.split(":");
            beach_comment.add(tempComent[1]);
            beach_CommentAutor.add(tempComent[0]+" said...");
        }


        adapterComment = new GridCommentsAdapter(this,beach_CommentAutor,beach_comment);

        gridViewComments.setAdapter(adapterComment);
    }


    @Override
    public void onResume()
    {
        super.onResume();

        usersData=UsersController.getInstance();
        beachData=beachSelected.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        Comments.this);

                LayoutInflater inflater = Comments.this.getLayoutInflater();

                final View myLayout = inflater.inflate(R.layout.diaglo_new_comment,null);
                alertDialogBuilder.setView(myLayout);

                author= (EditText) myLayout.findViewById(R.id.EditAuthor);
                comment=(EditText) myLayout.findViewById(R.id.EditComment);




                // set title
                alertDialogBuilder.setTitle("Create comment");



                final String temp=usersData.getNameSession().toString()+" "+usersData.getLast_nameSession();
                author.setText(temp);

                Log.d("COMENTAR",comment.getText().toString());

                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes for add a new comment to this beach!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity

                                //---------Hacer POST al API beaches
                                RequestQueue MyRequestQueue = Volley.newRequestQueue(Comments.this);


                                String url = "https://beach-finder.herokuapp.com/beaches/"+beachData.getIdRealForBeach();
                                Log.d("v","");
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
                                        Log.d("Aguila:",SelectedBeach.comments);
                                        if (!beachData.getCommentsBeachSelected().equals("")) {
                                            String tempComentario = beachData.getCommentsBeachSelected() + "_" + author.getText().toString() + ":" + comment.getText().toString();
                                            MyData.put("comments", tempComentario); //Add the data you'd like to send to the server.
                                        }else
                                        {
                                            String tempComentario=author.getText().toString() + ":" + comment.getText().toString();
                                            MyData.put("comments", tempComentario);
                                        }



                                        return MyData;
                                    }
                                };
                                MyRequestQueue.add(MyStringRequest);

                                beachData.downloadDataCommentsFromAPi(getCacheDir());
                                Toast.makeText(Comments.this, "Thank you very much! Your commment has been added", Toast.LENGTH_SHORT).show();
                                SystemClock.sleep(3000);


                                //GridCommentsAdapter adapterComment = new GridCommentsAdapter(Comments.,beach_CommentAutor,beach_comment);

                                //gridViewComments.setAdapter(adapterComment);

                                Intent intent = new Intent(Comments.this,SelectedBeach.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT); //(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET; Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                                // //---------Hacer POST al API beaches

                                Comments.this.finish();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        //Boton de UP
        // If your minSdkVersion is 11 or higher use:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        */

        //-------------Mostrar comentario en GridView

        /*if(c)
        {

            Toast.makeText(this, "No hay comentario por el momentos", Toast.LENGTH_SHORT).show();
            // Do something with the empty list here.
        }else
        {*/
        //GridView gridViewComments  = findViewById(R.id.grid_viewComment);
        Intent intent = getIntent();
        String comentarios = intent.getStringExtra("selected_beach");

        beachData.downloadDataCommentsFromAPi(getCacheDir());
        try {
            Thread.sleep(5000);
            Log.d("Caballo","Entro al Try");
        } catch (InterruptedException e) {
            Log.d("Caballo","Entro al CARTCH");
            e.printStackTrace();
        }
        ///Log.d("Bicho:",));
        Log.d("API beachesComments",beachData.getComments());



        String comentariosLista[]=beachData.getComments().split("_");
        //En este momento comentarios del API tiene forma así:
        //[Autor:Comentario|Autor2:Comentario2]

        for (String comentario:comentariosLista) {
            String tempComent[]=comentario.split(":");
            beach_comment.add(tempComent[1]);
            beach_CommentAutor.add(tempComent[0]+" said...");
        }



        gridViewComments.setAdapter(adapterComment);

        //}
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
}
