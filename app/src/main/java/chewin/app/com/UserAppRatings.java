package chewin.app.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class UserAppRatings extends AppCompatActivity {

    //Declaration start
    ArrayList<String> hashmap;
    String clatitude,clongitude,restname;
    Button buttonratingsubmitapp;
    EditText editTextfeedbackapp;
    RatingBar ratingbarapp;
    //Declaration end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        //Initialize widget
        ratingbarapp=(RatingBar) findViewById(R.id.ratingbarapp);
        buttonratingsubmitapp = (Button) findViewById(R.id.buttonratingsubmitapp);
        editTextfeedbackapp=(EditText)findViewById(R.id.editTextfeedbackapp);

        // ClickListener to handle event
        buttonratingsubmitapp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!editTextfeedbackapp.equals(""))
                {

                    // Save rating in database
                    //Send value to FeedbackServlet
                    new Thread(new Runnable() {
                        public void run() {

                            try{
                                //android user app rating


                                URL url1 = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/FeedbackServlet");

                                //define HttpsURLConnection
                                URLConnection connection1 = url1.openConnection();
                                connection1.setDoOutput(true);

                                //get Shared Prefeernece
                                SharedPreferences uname=getSharedPreferences("loginusersdetails",0);

                                 //Create a query string
                                Uri.Builder builder1 = new Uri.Builder()
                                        .appendQueryParameter("rating", String.valueOf(ratingbarapp.getRating()))
                                        .appendQueryParameter("string", editTextfeedbackapp.getText().toString());

                                String query1 = builder1.build().getEncodedQuery();

                                OutputStream os1 = connection1.getOutputStream();
                                BufferedWriter writer1 = new BufferedWriter(
                                        new OutputStreamWriter(os1, "UTF-8"));
                                writer1.write(query1);
                                writer1.close();

                                //Get feedback value from server
                                BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                                String returnString1="";
                                String feedback1=null;
                                while ((returnString1 = in1.readLine()) != null)
                                {
                                    feedback1= returnString1;
                                }
                                in1.close();
                                final String a1=feedback1;

                                //Display feedback value from server

                                runOnUiThread(new Runnable() {
                                    public void run() {
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

                    //Intent to class WelcomeScreenLogin
                    Intent recieveDataIntent1 = getIntent();
                    String classnam = recieveDataIntent1.getStringExtra("class");
                    Intent i;
                    if(classnam.equals("welcome"))
                    {
                         i = new Intent(UserAppRatings.this, WelcomeScreenLogin.class);
                    }else
                    {
                        i = new Intent(UserAppRatings.this, WelcomeScreenLogin.class);
                    }
                    finish();
                    startActivity(i);

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Intent to OneRestaurant class
            case android.R.id.home:
                Intent i=new Intent(UserAppRatings.this,OneRestaurant.class);
                i.putExtra("hashmapdetails", hashmap);
                i.putExtra("restname", restname);
                i.putExtra("currentlat", clatitude);
                i.putExtra("currentlong",clongitude);
                finish();
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //Execute your code here
    Intent i = new Intent(UserAppRatings.this, WelcomeScreenLogin.class);
    startActivity(i);
    finish();
    }

}

