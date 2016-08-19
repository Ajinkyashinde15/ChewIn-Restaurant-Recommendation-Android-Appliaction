package chewin.app.com;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.Plus;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

//Activity to show after skip login
public class WelcomeScreen extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, NavigationView.OnNavigationItemSelectedListener {

    //Declare and initialize variables
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = WelcomeScreen.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private ArrayAdapter<String> listAdapter;
    private LocationSource.OnLocationChangedListener mListener;
    HashMap<String, ArrayList<String>> multiMap = new HashMap<String, ArrayList<String>>();
    HashMap<String, ArrayList<String>> multiMapf = new HashMap<String, ArrayList<String>>();
    double longitute;
    double latitude;
    ListView list;

    //Set PrefManager
    private PrefManager prefManager;

    private ImageView dp;
    private TextView username, email, dob, gender;
    private GoogleApiClient googleApiClient;
    private boolean fbLogged;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set Content View welcome_screen
        setContentView(R.layout.welcome_screen);

        //Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {

            //Setup map
            setUpMapIfNeeded();

            //Add permission if VERSION.SDK_INT > 9
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            //LocationManager
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
            } else {
                showGPSDisabledAlertToUser();
            }


            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(1 * 1000)        // 10 seconds, in milliseconds
                    .setFastestInterval(500); // 1 second, in milliseconds
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "Location services connection failed with code " + e.getMessage());
        }

        //Set drawer layout and navigationView
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view_skip_login);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Menu menu=navigationView.getMenu();
        MenuItem signout=menu.findItem(R.id.sign_out_menu);
        signout.setTitle("Log in");
        //nearby item
        if (id == R.id.nav_nearby) {
            Intent intent = new Intent(WelcomeScreen.this, WelcomeScreen.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_bookmark) {
            // Bundle b = new Bundle();
            //bookmark item

            Toast.makeText(getApplicationContext(),"Please login to use this feature",Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_checkin) {
            //Checkin item
            Toast.makeText(getApplicationContext(),"Please login to use this feature",Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_filter) {
            //Call for Intent class
            Intent i = new Intent("chewin.navigationbarchewin.FILTER");
            startActivity(i);

        }
        else if (id==R.id.nav_share)
        {
            //Share to facebook
            Toast.makeText(getApplicationContext(),"Please login to use this feature",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_feedback) {
            //Intent i = new Intent("chewin.navigationbarchewin.FEEDBACK");

            Toast.makeText(getApplicationContext(),"Please login to use this feature",Toast.LENGTH_LONG).show();

        }
        else if (id == R.id.nav_menusearch) {
            //Intent i = new Intent("chewin.navigationbarchewin.FEEDBACK");

            Toast.makeText(getApplicationContext(),"Please login to use this feature",Toast.LENGTH_LONG).show();

        }

        else if (id == R.id.sign_out_menu) {

            prefManager = new PrefManager(this);
            prefManager.setFirstTimeLaunch(true);
            AccessToken.setCurrentAccessToken(null);
            Profile.setCurrentProfile(null);
            Intent getOutIntent = new Intent(WelcomeScreen.this, WelcomeActivity.class);
            startActivity(getOutIntent);
            finish();


            }//end else if

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    //Check for enable GPS
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }


    public void onConnected(Bundle bundle) {
        Location location = null;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } else {
            // requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            Toast.makeText(this, "Should ask for the approval/denial here", Toast.LENGTH_LONG).show();
        }





        //Location location = FusedLocationApi.getLastLocation(mGoogleApiClient);
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            if (location == null) {
                FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            } else {
                handleNewLocation(location);
            }
            //End Location = FusedLocationApi
        }

    }

    private void handleNewLocation(Location location) {
        final double currentLatitude = location.getLatitude();
        final double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        //Set up current location
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mMap.addMarker(options);
        float zoomLevel = 10.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        try {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);


        }catch (Exception e)
        {

        }
        //Toast.makeText(WelcomeScreen.this,"Handle new Location = "+location.getLatitude()+" Langi= "+ location.getLongitude(),Toast.LENGTH_SHORT).show();
        longitute= location.getLongitude();
        latitude=location.getLatitude();

        //Call function doInBackground1 and return hashmap
        multiMapf = doInBackground1(longitute,latitude);

        //Declare an array hotels1
        final String[] hotels1 = new String[multiMapf.size()];
        int i = 0;
        for (String key : multiMapf.keySet()) {
            hotels1[i++] = key.toString();
        }

        //Declare an array imageId
        final Bitmap[] imageId = new Bitmap[multiMapf.size()];
        int j = 0;
        for (String key : multiMapf.keySet()) {
            try {
                ArrayList<String> a =multiMapf.get(key);
                URL newurl = new URL(a.get(1));
                Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                imageId[j++] = mIcon_val;  //Copy value into an array
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        //Declare an array rank1
        final String[] rank1 = new String[multiMapf.size()];
        i = 0;
        for (String key : multiMapf.keySet()) {
            ArrayList<String> a=multiMapf.get(key);
            rank1[i++] = a.get(4);
        }

        final String[] textrank = new String[multiMapf.size()];
        i = 0;
        for (String key : multiMapf.keySet()) {
            ArrayList<String> a = multiMapf.get(key);
            textrank[i++] = a.get(9);
        }

        //Define Custom adaptor and add value
        CustomList adapter = new CustomList(WelcomeScreen.this, hotels1, imageId,rank1,textrank);
        list=(ListView)findViewById(R.id.mainListView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(WelcomeScreen.this, "You Clicked at " +hotels1[+ position], Toast.LENGTH_SHORT).show();
                String stringText;

                ArrayList<String> a=multiMapf.get(hotels1[+ position]);
                //Toast.makeText(WelcomeScreen.this, " Lan " + a.get(4) + " Lat= " + a.get(5), Toast.LENGTH_LONG).show();

                Intent i=new Intent(WelcomeScreen.this,OneRestaurant.class);
                i.putExtra("hashmapdetails", a);
                i.putExtra("restname", hotels1[+ position]);
                i.putExtra("currentlat", String.valueOf(currentLatitude));
                i.putExtra("currentlong",String.valueOf(currentLongitude));
                startActivity(i);
            }
        });

        //Show restaurants location on map
        for (String key : multiMapf.keySet()) {
            ArrayList<String> location1 = multiMapf.get(key);
            double latit = Double.parseDouble(location1.get(2));
            double longi = Double.parseDouble(location1.get(3));

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latit, longi))
                    .title(key)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }

        Log.d("Location= "+ TAG, location.toString());
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    protected HashMap<String, ArrayList<String>> doInBackground1(double longitute, double latitude) {

        //Connect with zomato and getting nearby restaurants details
        HttpURLConnection urlConnection = null;
        String restaJsonStr = null;
        BufferedReader reader = null;
        URL url=null;
        try
        {
            String longit=String.valueOf(longitute);
            String latit=String.valueOf(latitude);

            Intent recieveDataIntent1 = getIntent();
            String filterflag = recieveDataIntent1.getStringExtra("flag");
            String distance =recieveDataIntent1.getStringExtra("distance");
            String nrestauratnts =recieveDataIntent1.getStringExtra("nrestauratnts");

            if(filterflag==null)
            {
                url = new URL("https://developers.zomato.com/api/v2.1/search?entity_type=city&count=15&lat=" + latit + "&lon=" + longit + "&radius=2000&sort=real_distance&order=asc");
            }else {
                url = new URL("https://developers.zomato.com/api/v2.1/search?entity_type=city&count="+nrestauratnts+"&lat=" + latit + "&lon=" + longit + "&radius="+distance+"&sort=real_distance&order=asc");
            }

            //URL url=new URL(url);
            //REST APIs call to zomatoes
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("user_key", "b6728b744dbec3f54fb5cce3ab6d62d2");
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "curl/7.43.0");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoOutput(false);
            urlConnection.connect();

            Log.v("RestAPI", "Built URI " + url.toString());

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;


            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }

            restaJsonStr = buffer.toString();
            JSONObject object = new JSONObject(restaJsonStr);
            //Access parent node restaurants
            JSONArray jsonArray = object.optJSONArray("restaurants");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);  //get individual restaurant details

                //get individual restaurant name
                JSONObject rs = jsonObject.getJSONObject("restaurant");
                String name=rs.optString("name").toString();
                String id=rs.optString("id").toString();

                //get individual restaurant cuisines
                String cuisines = rs.optString("cuisines").toString();
                String purl =rs.optString("thumb").toString();
                String phone_numbers=rs.optString("phone_numbers").toString();
                String rurl=rs.optString("url").toString();

                //get individual restaurant location
                JSONObject location = rs.getJSONObject("location");
                String latit1=location.optString("latitude").toString();
                String longi =location.optString("longitude").toString();
                String address =location.optString("address").toString();

                JSONObject rating = rs.getJSONObject("user_rating");
                String aggregate_rating=rating.optString("aggregate_rating").toString();
                String votes=rating.optString("votes").toString();
                String rating_text=rating.optString("rating_text").toString();

                ArrayList<String> rst=new ArrayList<String>();
                rst.add(cuisines);  //0
                rst.add(purl);  //1
                rst.add(latit1);  //2
                rst.add(longi);  //3
                rst.add(aggregate_rating);  //4
                rst.add(votes);  //5
                rst.add(address);  //6
                rst.add(phone_numbers);  //7
                rst.add(rurl);  //8
                rst.add(rating_text);  //9
                rst.add(id);  //10
                //add values into multiMap hashmap
                multiMap.put(name, rst);

            }

        }
        catch (Exception e)
        {
            Log.e("RestAPI", e.getMessage(), e); //Write debug log file with errors
            Toast.makeText(WelcomeScreen.this,"Exception= "+e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        //return multiMap hashmap
        return multiMap;
    }


}
