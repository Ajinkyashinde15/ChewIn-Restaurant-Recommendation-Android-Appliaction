package chewin.app.com;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

public class WelcomeScreen extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, NavigationView.OnNavigationItemSelectedListener {

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


    //Ademola Kazeem
    private ImageView dp;
    private TextView username, email, dob, gender;
    private GoogleApiClient googleApiClient;
    private boolean fbLogged;

    //End Ademola Kazeem

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //AK GooglePlus
        buidNewGoogleApiClient();
        //End AK GooglePlus


        //setContentView(R.layout.activity_main);
        setContentView(R.layout.welcome_screen);


       // final String rDp, rUsername, rEmail, rDob, rGender;
        //Intent recieveDataIntent = getIntent();
        //rUsername = recieveDataIntent.getStringExtra("P_NAME");
        //rGender = recieveDataIntent.getStringExtra("P_GENDER");
       // rDp = recieveDataIntent.getStringExtra("P_PHOTOURL");
        //Log.d("Username text", rUsername);
       // Log.d("Gender text", rGender);
        //Log.d("url", rDp);



        //AK Liogin
        NavigationView nView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = nView.getHeaderView(0);
        //View view = findViewById(R.id.drawer_layout);
        dp = (ImageView) headerLayout.findViewById(R.id.profile_pic);
        username  = (TextView) headerLayout.findViewById(R.id.userName);
        //dp = (ImageView) findViewById(R.id.profile_pic);
        //email = (TextView) findViewById(R.id.emailId);
        //username = (TextView) findViewById(R.id.userName);
        //dob = (TextView) findViewById(R.id.dob);
        //gender = (TextView) findViewById(R.id.gender);


        //Get the intent from the Welcome Activity
        Intent recieveDataIntent = getIntent();
        final String rDp, rUsername, rEmail, rDob, rGender;
        String fbfalse = recieveDataIntent.getStringExtra("FBFALSE");
        String gplusfalse = recieveDataIntent.getStringExtra("GPLUSFALSE");
        //&& recieveDataIntent.getStringExtra("GPLUSTRUE").equals("TRUE")
        if ((fbfalse != null && fbfalse.equalsIgnoreCase("FALSE"))) {
            rDp = recieveDataIntent.getStringExtra("P_PHOTOURL");
            //rAgeRange = recieveDataIntent.getStringExtra("P_AGE_RANGE");
            rUsername = recieveDataIntent.getStringExtra("P_NAME");
            rEmail = recieveDataIntent.getStringExtra("P_EMAIL");
            rDob = recieveDataIntent.getStringExtra("P_DOB");
            //rGender = recieveDataIntent.getStringExtra("P_GENDER");

            fbLogged = false;

            //email.setText(rEmail);
           username.setText(rUsername);
            //dob.setText(rDob);
            //gender.setText(rGender);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(rDp);
                        InputStream inputStream = url.openConnection().getInputStream();
                        final Bitmap urlBitmap = BitmapFactory.decodeStream(inputStream);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dp.setImageBitmap(urlBitmap);
                            }
                        });


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else if (gplusfalse != null && gplusfalse.equalsIgnoreCase("FALSE")) {


            rDp = recieveDataIntent.getStringExtra("P_PHOTOURL");
            //rAgeRange = recieveDataIntent.getStringExtra("P_AGE_RANGE");
            String fbId = recieveDataIntent.getStringExtra("FBID");
            rUsername = recieveDataIntent.getStringExtra("P_NAME");
            rEmail = recieveDataIntent.getStringExtra("P_EMAIL");
            rDob = recieveDataIntent.getStringExtra("P_DOB");
            //rGender = recieveDataIntent.getStringExtra("P_GENDER");

            fbLogged = true;

            //email.setText(rEmail);
            username.setText(rUsername);
            //dob.setText(rDob);
            //gender.setText(rGender);
            String imageurl = "https://graph.facebook.com/" + fbId + "/picture?type=large";
            Picasso.with(WelcomeScreen.this).load(imageurl).into(dp);


        }
        /*//fb
        (findViewById(R.id.sign_out_menu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (AccessToken.getCurrentAccessToken() != null) {
                if (fbLogged == true) {
                    LoginManager.getInstance().logOut();
                    Log.d("logout", "I am login out now");

                    Intent intent = new Intent(WelcomeScreen.this, WelcomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Successfully Signed out!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (fbLogged == false) {
                    gPlusSignOut();
                }
            }
            //}
        });*/
        //end fb


        //End AK Login


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {

            setUpMapIfNeeded();

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }


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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            // Bundle b = new Bundle();

            Intent intent = new Intent(WelcomeScreen.this, BookmarkList.class);
            intent.putExtra("class", "bookmark");
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(WelcomeScreen.this, CheckInList.class);
            intent.putExtra("class", "checkin");
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent i = new Intent("chewin.navigationbarchewin.FILTER");
            startActivity(i);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.edit_profile) {


            /*

             Intent sendDataIntent = new Intent(WelcomeActivity.this, WelcomeScreen.class);
                        name = json.getString("name");
                        email = json.getString("email");
                        dob = json.getString("birthday");
                       gender = json.getString("gender");
                       String fbId = json.getString("id");

                        Log.d("name", name);
                        Log.d("email", email);

                        sendDataIntent.putExtra(P_NAME, name);
                        sendDataIntent.putExtra(P_EMAIL, email);
                        sendDataIntent.putExtra(P_DOB, dob);
                        sendDataIntent.putExtra(P_GENDER, gender);

                        sendDataIntent.putExtra("FBID", fbId);
                        sendDataIntent.putExtra(GPLUSFALSE, "FALSE");
                        sendDataIntent.putExtra(FBTRUE, "TRUE");

                        //sendDataIntent.putExtra(P_PHOTOURL, dpUrl);
                        startActivity(sendDataIntent);
             */


            Intent intent = new Intent(this, UserEditActivity.class);
            startActivity(intent);








        } else if (id == R.id.sign_out_menu) {

            (findViewById(R.id.sign_out_menu)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if (AccessToken.getCurrentAccessToken() != null) {
                    if (fbLogged == true) {
                        LoginManager.getInstance().logOut();
                        Log.d("logout", "I am login out now");

                        Intent intent = new Intent(WelcomeScreen.this, WelcomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successfully Signed out!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    if (fbLogged == false) {
                        gPlusSignOut();
                    }
                }
                //}
            });


        }

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

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
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



        //int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        //if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //Execute location service call if user has explicitly granted ACCESS_FINE_LOCATION..
        //}




    }

    private void handleNewLocation(Location location) {
        final double currentLatitude = location.getLatitude();
        final double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
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

            //AK Fix
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
            //End AK Fix
            mMap.setMyLocationEnabled(true);


        }catch (Exception e)
        {

        }
        //Toast.makeText(WelcomeScreen.this,"Handle new Location = "+location.getLatitude()+" Langi= "+ location.getLongitude(),Toast.LENGTH_SHORT).show();
        longitute= location.getLongitude();
        latitude=location.getLatitude();
        multiMapf = doInBackground1(longitute,latitude);

        final String[] hotels1 = new String[multiMapf.size()];
        int i = 0;
        for (String key : multiMapf.keySet()) {
            hotels1[i++] = key.toString();
        }

        final Bitmap[] imageId = new Bitmap[multiMapf.size()];
        int j = 0;
        for (String key : multiMapf.keySet()) {
            try {
                ArrayList<String> a = new ArrayList<String>();
                URL newurl = new URL(a.get(1));
                Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                imageId[j++] = mIcon_val;
            }catch (Exception e)
            {}
        }

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

        // Do some validation here
        HttpURLConnection urlConnection = null;
        String restaJsonStr = null;
        BufferedReader reader = null;
        try
        {
            String longit=String.valueOf(longitute);
            String latit=String.valueOf(latitude);

            URL url = new URL("https://developers.zomato.com/api/v2.1/search?entity_type=city&count=15&lat="+latit+"&lon="+longit+"&radius=2000&sort=real_distance&order=asc");
            //URL url=new URL(uri);
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
            JSONArray jsonArray = object.optJSONArray("restaurants");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONObject rs = jsonObject.getJSONObject("restaurant");
                String name=rs.optString("name").toString();
                String cuisines = rs.optString("cuisines").toString();
                String purl =rs.optString("thumb").toString();
                String phone_numbers=rs.optString("phone_numbers").toString();
                String rurl=rs.optString("url").toString();

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

                multiMap.put(name, rst);

                //Toast.makeText(WelcomeScreen.this,i+"."+" Title = "+ name +" Cuisine= "+cuisines+" Purl= "+purl+" Lat= "+latit+" Long= "+longi,Toast.LENGTH_LONG).show();
                // Log.d("RestAPI",i+"."+"Title = " + name + "Cuisine= " + cuisines + "Purl= " + purl+" Lat= "+latit+" Long= "+longi);
            }

        }
        catch (Exception e)
        {
            Log.e("RestAPI", e.getMessage(), e);
            Toast.makeText(WelcomeScreen.this,"Exception= "+e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return multiMap;
    }






    //AK Logins
    private void buidNewGoogleApiClient(){

        googleApiClient =  new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API,Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }
    public void signOutNow(View view) {
        gPlusSignOut();

    }

    private void gPlusSignOut() {
        googleApiClient.disconnect();
        googleApiClient.connect();
        Log.i("Logged out", "Sign out Done");
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Successfully signed out of chewIn", Toast.LENGTH_SHORT).show();
        finish();
   }


    //End AK Logins

}
