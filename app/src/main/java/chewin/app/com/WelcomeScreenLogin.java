package chewin.app.com;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

//Activity to show after facebook or google plus login

public class
WelcomeScreenLogin extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, NavigationView.OnNavigationItemSelectedListener {

    //Declare and initialize variables
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = WelcomeScreenLogin.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private ArrayAdapter<String> listAdapter;
    private LocationSource.OnLocationChangedListener mListener;
    HashMap<String, ArrayList<String>> multiMap = new HashMap<String, ArrayList<String>>();
    HashMap<String, ArrayList<String>> gmultiMap = new HashMap<String, ArrayList<String>>();
    HashMap<String, ArrayList<String>> multiMapf = new HashMap<String, ArrayList<String>>();
    double longitute;
    double latitude;
    ListView list;

    private ImageView dp;
    private TextView username, email, dob, gender;
    private GoogleApiClient googleApiClient;
    private boolean fbLogged;
    //Set PrefManager
    private PrefManager prefManager;
    String rDp = null;
    String name = null;
    //post fb
    private ShareDialog shareDialog;
    //end post fb


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //AK GooglePlus
        buidNewGoogleApiClient();
        //End AK GooglePlus

        //set Content View welcome_screen
        setContentView(R.layout.activity_main);

        //Set NavigationView


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

       // NavigationView nView1 = (NavigationView) findViewById(R.id.nav_view);

        //Get the intent from the Welcome Activity
        Intent recieveDataIntent = getIntent();


        /////


        final String fbfalse = recieveDataIntent.getStringExtra("FBFALSE");
        final String gplusfalse = recieveDataIntent.getStringExtra("GPLUSFALSE");

        //&& recieveDataIntent.getStringExtra("GPLUSTRUE").equals("TRUE")
        if ((fbfalse != null && fbfalse.equalsIgnoreCase("FALSE"))) {
            rDp = recieveDataIntent.getStringExtra("bImage");
            name=recieveDataIntent.getStringExtra("uname");
            Log.d("Got Name:", recieveDataIntent.getStringExtra("uname"));
            Log.d("Got Image:", recieveDataIntent.getStringExtra("bImage"));
            fbLogged = false;
            username.setText(name);
            final String finalRDp = rDp;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(finalRDp);
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

            rDp = recieveDataIntent.getStringExtra("bImage");
            name=recieveDataIntent.getStringExtra("uname");
            fbLogged = true;
            username.setText(name);

            Picasso.with(WelcomeScreenLogin.this).load(rDp).into(dp);

        }
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        prefManager = new PrefManager(this);
        prefManager.setFirstTimeLaunch(false);






        /*if(recieveDataIntent == null){
            username.setText("...Your food on the menu");
        }*/

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_nearby) {
            //nearby item
            Toast.makeText(getApplicationContext(), "This feature is not available!", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.nav_bookmark) {
            // Bundle b = new Bundle();
            //bookmark item
            Intent intent = new Intent(WelcomeScreenLogin.this, BookmarkList.class);
            intent.putExtra("class", "bookmark");

            startActivity(intent);

        } else if (id == R.id.nav_checkin) {

            Intent intent = new Intent(WelcomeScreenLogin.this, CheckInList.class);
            intent.putExtra("class", "checkin");
           // finish();
            startActivity(intent);

        } else if (id == R.id.nav_filter) {
            Toast.makeText(getApplicationContext(), "This feature is not available!", Toast.LENGTH_SHORT).show();

        }
        else if (id == R.id.nav_share) {


            shareDialog = new ShareDialog(this);
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle("ChewIn Restaurant Mobile App")
                        .setContentDescription(
                                "ChewIn is a location-based, Menu-based Restaurant finder and Recommender")
                        .setContentUrl(Uri.parse("http://ucd-nlmsc-teamproject.github.io/Snapr-Team/"))
                        .setImageUrl(Uri.parse("http://ucd-nlmsc-teamproject.github.io/Snapr-Team/images/chewInLogo.PNG"))
                        .build();

                shareDialog.show(linkContent);
            }
        }
        else if (id == R.id.nav_feedback) {
            //Intent i = new Intent("chewin.navigationbarchewin.FEEDBACK");
            //startActivity(i);
            Bundle bun = new Bundle();
            bun.putString("name", "Please provide your feedback");
           // Intent i = new Intent(getApplicationContext(), feedback.class);
           // i.putExtras(bun);
            //finish();
            //startActivity(i);

            Intent ratingIntent=new Intent(WelcomeScreenLogin.this,UserAppRatings.class);
            ratingIntent.putExtra("class","welcomlogin");
            //finish();
            startActivity(ratingIntent);
        }
        else if (id == R.id.nav_menusearch) {
            //Intent i = new Intent("chewin.navigationbarchewin.FEEDBACK");
            //startActivity(i);
            //Bundle bun = new Bundle();
            //bun.putString("name", "Please provide your feedback");
            Intent i = new Intent(WelcomeScreenLogin.this, PostToFaceBook.class);
            i.putExtra("bImage",rDp);
            i.putExtra("uname",name);
            startActivity(i);

        }
        else if (id == R.id.sign_out_menu) {

            SharedPreferences settings = getApplicationContext().getSharedPreferences("loginusersdetails", Context.MODE_PRIVATE);
            settings.edit().clear().commit();
/*

            NavigationView nView = (NavigationView) findViewById(R.id.nav_view);
            View headerLayout = nView.getHeaderView(0);
            Menu menu = nView.getMenu();
            MenuItem navSignInOut = menu.findItem(R.id.sign_out_menu);
            if(recieveDataIntent == null){
                username.setText("...Your food on the menu");
                Log.d("No Intent", String.valueOf(recieveDataIntent));
                navSignInOut.setTitle("Log in");
            }
            //if(recieveDataIntent.hasExtra("P_NAME")){
            else if(recieveDataIntent != null){
                Log.d("IntentExist", String.valueOf(recieveDataIntent));
//            else if(recieveDataIntent.getExtras() != null) {
                navSignInOut.setTitle("Log Out");
                // navEditProfile.setVisible(true);
            }
*/


            Intent recieveDataIntent = getIntent();
            if(recieveDataIntent == null){
                Log.d("Not Logged in", "It didn't login in the first place");
               prefManager = new PrefManager(this);
                prefManager.setFirstTimeLaunch(true);
                Intent getOutIntent = new Intent(WelcomeScreenLogin.this, WelcomeActivity.class);
                startActivity(getOutIntent);
                finish();

            }
            //else if(recieveDataIntent.getExtras()!= null){
            else if(recieveDataIntent != null){

                //if (AccessToken.getCurrentAccessToken() != null) {
                if (AccessToken.getCurrentAccessToken() != null) {

                    LoginManager.getInstance().logOut();
                    prefManager = new PrefManager(this);
                    prefManager.setFirstTimeLaunch(true);
                    AccessToken.setCurrentAccessToken(null);
                    Profile.setCurrentProfile(null);

                    Log.d("logout", "I am login out now");
                    Intent intent = new Intent(WelcomeScreenLogin.this, WelcomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Successfully Signed out!", Toast.LENGTH_SHORT).show();
                    finish();

                }
                //else if (fbLogged == false) {
                else if (AccessToken.getCurrentAccessToken() == null) {
                    gPlusSignOut();
                }

            }//end else if
            //return false;
            //  }
            //end AK

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

    @Override
    public void onBackPressed() {
        //Execute your code here
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }

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


        // Send longi and latitude

 /*       new Thread(new Runnable() {
            public void run() {

                try{

                    URL url1 = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/AndroidServlet");

                    URLConnection connection1 = url1.openConnection();
                    connection1.setDoOutput(true);

                    Uri.Builder builder = new Uri.Builder()
                      //      .appendQueryParameter("username", finalRUsername)
                            .appendQueryParameter("longitude", String.valueOf(currentLongitude))
                            .appendQueryParameter("latitute", String.valueOf(currentLatitude));

                    String query1 = builder.build().getEncodedQuery();

                    OutputStream os1 = connection1.getOutputStream();
                    BufferedWriter writer1 = new BufferedWriter(
                            new OutputStreamWriter(os1, "UTF-8"));
                    writer1.write(query1);
//                            writer.flush();
                    writer1.close();

                    BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                    String returnString1="";
                    String feedback1=null;
                    while ((returnString1 = in1.readLine()) != null)
                    {
                        feedback1= returnString1;
                    }
                    in1.close();
                    final String a1=feedback1;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if(a1!=null)
                            {
                                Toast.makeText(getApplicationContext(),"Feedback from Server= "+ a1.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }catch(Exception e)
                {
                    Log.d("Exception",e.toString());
                    System.out.println("Conection Pasing Excpetion = " + e);
                    Log.e("Error= ", Log.getStackTraceString(e));
                }
            }

        }).start();
*/
        // end send logi and latitude

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


        CustomList adapter = new CustomList(WelcomeScreenLogin.this, hotels1, imageId,rank1,textrank);
        list=(ListView)findViewById(R.id.mainListView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(WelcomeScreenLogin.this, "You Clicked at " +hotels1[+ position], Toast.LENGTH_SHORT).show();
                String stringText;

                ArrayList<String> a=multiMapf.get(hotels1[+ position]);
                //Toast.makeText(WelcomeScreen.this, " Lan " + a.get(4) + " Lat= " + a.get(5), Toast.LENGTH_LONG).show();

                Intent i=new Intent(WelcomeScreenLogin.this,OneRestaurant.class);
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

        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            //Connect with server and getting recommonded restaurants details
            //REST APIs call to RecommendedRestaurants servlet

            URL url = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/rest/Service/RecommendedRestaurants");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            //Access parent node restaurants
            NodeList nodeList = doc.getElementsByTagName("restaurant");

            //Iterate the nodeList and print the info of nodeList objects

            for (int i = 0; i < nodeList.getLength(); i++) {

                //get individual restaurant name

                Node node = nodeList.item(i);

                String id,name,cuisines,purl,latit,longi,aggregate_rating,votes,address,phone_numbers,rurl,rating_text;

                Element fstElmnt = (Element) node;

                //get individual restaurant cuisines
                NodeList idList = fstElmnt.getElementsByTagName("id");
                Element idElement = (Element) idList.item(0);
                idList = idElement.getChildNodes();
                id=((Node) idList.item(0)).getNodeValue();

                //get individual restaurant name
                NodeList nameList = fstElmnt.getElementsByTagName("name");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                name=((Node) nameList.item(0)).getNodeValue();

                //get individual restaurant cuisines
                NodeList cuisinesList = fstElmnt.getElementsByTagName("cuisines");
                Element websiteElement = (Element) cuisinesList.item(0);
                cuisinesList = websiteElement.getChildNodes();
                cuisines=((Node) cuisinesList.item(0)).getNodeValue();

                //get individual restaurant thumb
                NodeList purlList = fstElmnt.getElementsByTagName("thumb");
                Element purlElement = (Element) purlList.item(0);
                purlList = purlElement.getChildNodes();
                purl=((Node) purlList.item(0)).getNodeValue();

                //get individual restaurant latitude
                NodeList latitList = fstElmnt.getElementsByTagName("latitude");
                Element latitElement = (Element) latitList.item(0);
                latitList = latitElement.getChildNodes();
                latit=((Node) latitList.item(0)).getNodeValue();

                //get individual restaurant longitude
                NodeList longiList = fstElmnt.getElementsByTagName("longitude");
                Element longiElement = (Element) longiList.item(0);
                longiList = longiElement.getChildNodes();
                longi=((Node) longiList.item(0)).getNodeValue();

                NodeList aggregate_ratingList = fstElmnt.getElementsByTagName("aggregateRating");
                Element aggregate_ratingElement = (Element) aggregate_ratingList.item(0);
                aggregate_ratingList = aggregate_ratingElement.getChildNodes();
                aggregate_rating=((Node) aggregate_ratingList.item(0)).getNodeValue();

                NodeList votesList = fstElmnt.getElementsByTagName("votes");
                Element votesElement = (Element) votesList.item(0);
                votesList = votesElement.getChildNodes();
                votes=((Node) votesList.item(0)).getNodeValue();

                NodeList addressList = fstElmnt.getElementsByTagName("address");
                Element addressElement = (Element) addressList.item(0);
                addressList = addressElement.getChildNodes();
                address=((Node) addressList.item(0)).getNodeValue();


                NodeList rurlList = fstElmnt.getElementsByTagName("url");
                Element rurlElement = (Element) rurlList.item(0);
                rurlList = rurlElement.getChildNodes();
                rurl=((Node) rurlList.item(0)).getNodeValue();

                NodeList rating_textList = fstElmnt.getElementsByTagName("ratingText");
                Element rating_textElement = (Element) rating_textList.item(0);
                rating_textList = rating_textElement.getChildNodes();
                rating_text=((Node) rating_textList.item(0)).getNodeValue();

                ArrayList<String> rst=new ArrayList<String>();
                rst.add(cuisines);  //0
                rst.add(purl);  //1
                rst.add(latit);  //2
                rst.add(longi);  //3
                rst.add(aggregate_rating);  //4
                rst.add(votes);  //5
                rst.add(address);  //6
                rst.add("");  //7
                rst.add(rurl);  //8
                rst.add(rating_text);  //9
                rst.add(id);  //10
                //add values into multiMap hashmap
                multiMap.put(name, rst);

            }


        } catch (Exception e) {
            System.out.println("XML Pasing Excpetion = " + e); //Write debug log file with errors
            Log.e("Error= ",Log.getStackTraceString(e));
        }

        //return multiMap hashmap
               return multiMap;
    }

    //Facebook Logins
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
        prefManager = new PrefManager(this);
        prefManager.setFirstTimeLaunch(true);
        AccessToken.setCurrentAccessToken(null);
        Profile.setCurrentProfile(null);

        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Successfully signed out of chewIn", Toast.LENGTH_SHORT).show();
        finish();
   }


    //End AK Logins

}
