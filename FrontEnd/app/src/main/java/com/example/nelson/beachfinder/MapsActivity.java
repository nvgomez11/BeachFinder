package com.example.nelson.beachfinder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    ArrayList<String> titlesArray = new ArrayList<String>();

    LocationManager locationManager;
    LocationListener locationListener;

    LatLng lat_long_beach;
    //Marker mLat_long_beach;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, locationListener);
                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        // Do other setup activities here too, as described elsewhere in this tutorial.

        // Build the Play services client for use by the Fused Location Provider and the Places API.
        // Use the addApi() method to request the Google Places API and the Fused Location Provider.

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        //move camara to Costa Rica and zoom 7
        LatLng costa_rica = new LatLng(10.346093801369888, -84.40597112187498);
        float zoomlevel = 7;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(costa_rica, zoomlevel));



 /*     //add markers
        LatLng tamarindo_beach = new LatLng(10.297445,-85.832117);
        mMap.addMarker(new MarkerOptions().position(tamarindo_beach).title("Tamarindo beach"));

        LatLng jaco_beach = new LatLng( 9.62024,-84.621749);
        mMap.addMarker(new MarkerOptions().position(jaco_beach).title("Jaco Beach"));*/
        mMap.setOnInfoWindowClickListener(getInfoWindowClickListener());

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                //mMap.clear();
                mMap.addMarker(new MarkerOptions().position(currentLocation).title("You were here"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

                for (int i = 0; i < SearchActivity.all_json_beaches.size(); i++) {
                    ArrayList<String> beach;
                    beach = SearchActivity.all_json_beaches.get(i);
                    String id = beach.get(0);
                    String beachName = beach.get(1);
                    String latitude = beach.get(7);
                    String longitude = beach.get(8);

                    lat_long_beach = new LatLng(Float.parseFloat(latitude), Float.parseFloat(longitude));
                    Marker mLat_long_beach = mMap.addMarker(new MarkerOptions().position(lat_long_beach).title(beachName));
                    mLat_long_beach.setTag(id);
                    titlesArray.add(mLat_long_beach.getTitle());
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, 0);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            mMap.clear();
            LatLng location = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(location).title("You're here"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }


    //listener del click de la ventana
    public GoogleMap.OnInfoWindowClickListener getInfoWindowClickListener() {
        return new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //Toast.makeText(getApplicationContext(), "Clicked a window with title..." + marker.getTitle(), Toast.LENGTH_SHORT).show();
                for(int i=0; i<SearchActivity.all_json_beaches.size();i++){
                    ArrayList<String> beach = SearchActivity.all_json_beaches.get(i);
                    if(beach.get(1).compareTo(marker.getTitle())==0){
                        Intent intent = new Intent(getApplicationContext(),SelectedBeach.class);
                        intent.putStringArrayListExtra("selected_beach",beach);
                        startActivity(intent);
                    }
                }
            }
        };
    }
}
