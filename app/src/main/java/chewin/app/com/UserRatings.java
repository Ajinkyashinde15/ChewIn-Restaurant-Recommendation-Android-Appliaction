package chewin.app.com;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

//Define class UserRatings to rate restaurant
public class UserRatings extends AppCompatActivity {

    //Initializing variable
    ArrayList<String> hashmap;
    String clatitude,clongitude,restname;
    Button buttonrating;
    EditText editTextfeedback;
    RatingBar ratingBaruserrating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratingsandfeedback);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        //Get Intent values
        Intent intent = getIntent();
        hashmap= intent.getStringArrayListExtra("hashmapdetails");
        clatitude = intent.getStringExtra("currentlat");
        clongitude = intent.getStringExtra("currentlong");
        restname = intent.getStringExtra("restname");

        //Initialize widget
        ratingBaruserrating=(RatingBar) findViewById(R.id.ratingBaruserrating);
        buttonrating = (Button) findViewById(R.id.buttonrating);
        editTextfeedback=(EditText)findViewById(R.id.editTextfeedback);

        //Define event handler ClickListener
        buttonrating.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Check if user feedback not is blank
                if(!editTextfeedback.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Feedback has been saved ",Toast.LENGTH_LONG).show();

                    // Save rating in database

                    new Thread(new Runnable() {
                        public void run() {

                            try{
                                //android user rating

                                //Call and send values to RatingServlet
                                URL url1 = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/RatingServlet");

                                URLConnection connection1 = url1.openConnection();
                                // HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                                connection1.setDoOutput(true);
                                SharedPreferences uname=getSharedPreferences("loginusersdetails",0);

                                //Crate a query string
                                Uri.Builder builder1 = new Uri.Builder()
                                        .appendQueryParameter("username", uname.getString("username","").toString())
                                        .appendQueryParameter("id",hashmap.get(10))
                                        .appendQueryParameter("rating", String.valueOf(ratingBaruserrating.getRating()));

                                String query1 = builder1.build().getEncodedQuery();

                                OutputStream os1 = connection1.getOutputStream();
                                BufferedWriter writer1 = new BufferedWriter(
                                        new OutputStreamWriter(os1, "UTF-8"));
                                writer1.write(query1);
                                writer1.close();

                                //Read input from RatingServlet
                                BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                                String returnString1="";
                                String feedback1=null;

                                //Read feedback
                                while ((returnString1 = in1.readLine()) != null)
                                {
                                    feedback1= returnString1;
                                }
                                in1.close();

                                //Display feedback
                                final String a1=feedback1;
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                    if(a1!=null)
                                        Toast.makeText(getApplicationContext(),"Feedback from Server= "+ a1.toString(),Toast.LENGTH_LONG).show();
                                    }
                                });

                            }catch(Exception e)
                            {
                                Log.d("Exception",e.toString()); //Write debug log file with errors
                                System.out.println("Conection Pasing Excpetion = " + e);
                                Log.e("Error= ", Log.getStackTraceString(e));
                            }

                        }
                    }).start();

                    ///Intent to OneRestaurant class
                    Intent i=new Intent(UserRatings.this,OneRestaurant.class);
                    i.putExtra("hashmapdetails", hashmap);
                    i.putExtra("restname", restname);
                    i.putExtra("currentlat", clatitude);
                    i.putExtra("currentlong",clongitude);
                    finish();
                    startActivity(i);

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Intent to OneRestaurant class
                Intent i=new Intent(UserRatings.this,OneRestaurant.class);
                i.putExtra("hashmapdetails", hashmap);
                i.putExtra("restname", restname);
                i.putExtra("currentlat", clatitude);
                i.putExtra("currentlong",clongitude);
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

