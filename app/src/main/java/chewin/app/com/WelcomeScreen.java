package chewin.app.com;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

public class WelcomeScreen extends android.support.v4.app.FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        try {

            setUpMapIfNeeded();

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }


            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
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

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link (HashMap)} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
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

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     * @param
     */


    private void setUpMap() {

        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }


    public void onConnected(Bundle bundle) {
        Location location = null;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        else
        {
            // requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            Toast.makeText(this,"Should ask for the approval/denial here", Toast.LENGTH_LONG).show();
        }

        //Location location = FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }
    }

    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mMap.addMarker(options);
        float zoomLevel = 10.0f; //This goes up to 21
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        Toast.makeText(WelcomeScreen.this,"Handle new Location = "+location.getLatitude()+" Langi= "+ location.getLongitude(),Toast.LENGTH_SHORT).show();
        longitute= location.getLongitude();
        latitude=location.getLatitude();
        multiMapf = doInBackground1(longitute,latitude);

        final ListView mainListView = (ListView) findViewById(R.id.mainListView);
        //String[] hotels = new String[100];

        String[] hotels1 = new String[multiMapf.size()];
        int i = 0;
        for (String key : multiMapf.keySet()) {
            hotels1[i++] = key.toString();
        }

        int j = 0;

        ArrayList<String> hotelList = new ArrayList<String>();
        for (int i1 = 0; i1 < hotels1.length; i1++) {
            hotelList.add(hotels1[i1]);
        }

        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, R.id.rowTextView, hotelList);
        // Add more planets. If you passed a String[] instead of a List<String>
        // into the ArrayAdapter constructor, you must not add more items.
        // Otherwise an exception will occur.

        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter(listAdapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stringText;

                //in normal case
                //stringText = ((TextView) view).getText().toString();

                //in case if listview has separate item layout
                TextView textview = (TextView) view.findViewById(R.id.rowTextView);
                stringText = textview.getText().toString();
                ArrayList<String> a = multiMapf.get(stringText);
                //show selected
                Toast.makeText(WelcomeScreen.this, stringText + " Lan " + a.get(2) + " Lat= " + a.get(3), Toast.LENGTH_LONG).show();
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
                String purl =rs.optString("photos_url").toString();

                JSONObject location = rs.getJSONObject("location");
                String latit1=location.optString("latitude").toString();
                String longi =location.optString("longitude").toString();

                //JSONObject rating = rs.getJSONObject("user_rating");
                //String aggregate_rating=rating.optString("aggregate_rating").toString();
                //String rating_text=rating.optString("rating_text").toString();

                ArrayList<String> rst=new ArrayList<String>();
                rst.add(cuisines);
                rst.add(purl);
                rst.add(latit1);
                rst.add(longi);
                //rst.add(aggregate_rating);
                //rst.add(rating_text);

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
}
