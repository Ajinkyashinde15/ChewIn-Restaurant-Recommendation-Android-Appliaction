package chewin.app.com;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class OneRestaurant extends Activity
{
    //RatingBar rtbProductRating;
    TextView rtbProductRating,rsname,reviewtext,distance,cuisuine,addressText,callText,webtext,emailText;  //create instance TextView
    ImageButton googleMapButton; //Image Button
    ImageView restoimage;
    DBHelper dbhelp;
    SQLiteDatabase db;
    Cursor c;

    private BottomBar mBottomBar;

    //facebook post
    private ShareDialog shareDialog;
    //End facebook post

    int count=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_restaurant);

        dbhelp=new DBHelper(getApplicationContext(), "restaurant", null,1);
        db=dbhelp.getWritableDatabase();

        //Declares and maps android widgets
        rtbProductRating=(TextView)findViewById(R.id.rtbProductRating);
        rsname=(TextView)findViewById(R.id.rsname);
        reviewtext=(TextView)findViewById(R.id.reviewtext);
        distance=(TextView)findViewById(R.id.distance);
        cuisuine=(TextView)findViewById(R.id.cuisuine);
        addressText=(TextView)findViewById(R.id.addressText);
        callText=(TextView)findViewById(R.id.callText);
        webtext=(TextView)findViewById(R.id.webtext);
        emailText=(TextView)findViewById(R.id.emailText);
        restoimage=(ImageView)findViewById(R.id.restoimage);
        googleMapButton=(ImageButton)findViewById(R.id.googleMapButton);

        //Get Intent value
        Intent intent = getIntent();
        final ArrayList<String> hashmap = intent.getStringArrayListExtra("hashmapdetails");
        final String clatitude = intent.getStringExtra("currentlat");
        final String clongitude = intent.getStringExtra("currentlong");
        final String restname = intent.getStringExtra("restname");

        //Start Set Value
        //Thumb image
        try {
            URL newurl = new URL(hashmap.get(1));
            Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            restoimage.setImageBitmap(mIcon_val);
        }catch (Exception e1)
        {}

        //Resto Name
        rsname.setText(restname);

        //Star Rating
        rtbProductRating.setText("Ratings- "+hashmap.get(4));

        //No of Review
        reviewtext.setText(hashmap.get(5) + " Reviews");

        //Distance
        float[] results = new float[1];
        Location.distanceBetween(Double.parseDouble(clatitude), Double.parseDouble(clongitude), Double.parseDouble(hashmap.get(2)), Double.parseDouble(hashmap.get(3).toString()), results);
        distance.setText(" " + results[0] + " meters");

        //Display cuisuines
        cuisuine.setText(hashmap.get(0));

        //Display Address
        addressText.setText(hashmap.get(6));

        //Show Map
        googleMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + Double.parseDouble(clatitude) + "," + Double.parseDouble(clongitude) + "&daddr=" + hashmap.get(2) + "," + hashmap.get(3)));
                gintent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(gintent);
            }
        });

        //show phone no
       // callText.setText(hashmap.get(7));
        callText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0872306803"));
                startActivity(intent);
            }
        });

        //show website

        webtext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(hashmap.get(8)));
                startActivity(viewIntent);
            }
        });

        //show email address
        emailText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//Call email intent
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "ajinkyashinde15@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "ChewIn Restaurant Finder");
                intent.putExtra(Intent.EXTRA_TEXT, "Food Delivery");
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        //Set Bottom Bar
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override

            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bb_menu_home) {
                    if (count > 1) {
                        Intent i = new Intent(OneRestaurant.this, WelcomeScreenLogin.class);
                        startActivity(i);
                        finish();
                        count++;
                    }
                }
                if (menuItemId == R.id.bb_menu_bookmark)  //Bookmark botttom bar
                {

                    ContentValues cv= new ContentValues();

                    cv.put("username", "demo");
                    cv.put("restaurant_name",restname);
                    cv.put("latitude", hashmap.get(2));
                    cv.put("longitude", hashmap.get(3));

                    db.insert("bookmark", null, cv);

                    Toast.makeText(getApplicationContext(), "Bookmark has been saved ", Toast.LENGTH_SHORT).show();

                    new Thread(new Runnable() {
                        public void run() {

                            try{
                                //android bookmark
                                //Send value to BookmarkServlet
                                URL url1 = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/BookmarkServlet");

                                URLConnection connection1 = url1.openConnection();
                                // HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                                connection1.setDoOutput(true);
                                SharedPreferences uname=getSharedPreferences("loginusersdetails",0);

                                //Create a QueryString with values username,id,restaurant name,logitute, latitute
                                Uri.Builder builder1 = new Uri.Builder()
                                        .appendQueryParameter("username", uname.getString("username","").toString())
                                        .appendQueryParameter("id",hashmap.get(10))
                                        .appendQueryParameter("rsname", restname)
                                        .appendQueryParameter("longitute",hashmap.get(2))
                                        .appendQueryParameter("latitute", hashmap.get(3));

                                String query1 = builder1.build().getEncodedQuery();

                                OutputStream os1 = connection1.getOutputStream();
                                BufferedWriter writer1 = new BufferedWriter(
                                        new OutputStreamWriter(os1, "UTF-8"));
                                writer1.write(query1);
                                writer1.close(); //close BufferedWriter

                                BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                                String returnString1="";
                                String feedback1=null;

                                //Waiting for until get feedback from server
                                while ((returnString1 = in1.readLine()) != null)
                                {
                                    feedback1= returnString1;
                                }
                                in1.close();
                                final String a1=feedback1;
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                    if(a1!=null)
                                        //Show feedback from server
                                        Toast.makeText(getApplicationContext(),"Feedback from Server= "+ a1.toString(),Toast.LENGTH_LONG).show();
                                    }
                                });

                            }catch(Exception e)
                            {
                                Log.d("Exception",e.toString());  //Write debug log file with errors
                                System.out.println("Conection Pasing Excpetion = " + e);
                                Log.e("Error= ", Log.getStackTraceString(e));
                            }

                        }
                    }).start();

                    count++;
                }

                if (menuItemId == R.id.bb_menu_checkin) {  //BottomBar for Check in

                    ContentValues cv= new ContentValues();

                    cv.put("username", "demo");
                    cv.put("restaurant_name",restname);
                    cv.put("latitude", hashmap.get(2));
                    cv.put("longitude", hashmap.get(3));
                    db.insert("checkin", null, cv);

                    new Thread(new Runnable() {
                        public void run() {

                            try{
                                //android bookmark
                                //Send details to CheckIn servlet
                                URL url1 = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/CheckInServlet");

                                URLConnection connection1 = url1.openConnection();
                                // HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                                connection1.setDoOutput(true);
                                SharedPreferences uname=getSharedPreferences("loginusersdetails",0);

                                //Create query string
                                Uri.Builder builder1 = new Uri.Builder()
                                        .appendQueryParameter("username", uname.getString("username","").toString())
                                        .appendQueryParameter("id",hashmap.get(10))
                                        .appendQueryParameter("rsname", restname)
                                        .appendQueryParameter("longitute",hashmap.get(2))
                                        .appendQueryParameter("latitute", hashmap.get(3));

                                String query1 = builder1.build().getEncodedQuery();

                                OutputStream os1 = connection1.getOutputStream();
                                BufferedWriter writer1 = new BufferedWriter(
                                        new OutputStreamWriter(os1, "UTF-8"));
                                writer1.write(query1);
                                writer1.close();

                                BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                                String returnString1="";
                                String feedback1=null;

                                //Wait for feedback from server
                                while ((returnString1 = in1.readLine()) != null)
                                {
                                    feedback1= returnString1;
                                }
                                in1.close();
                                final String a1=feedback1;
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        if(a1!=null){
                                        //Print and display feedback from server
                                        Toast.makeText(getApplicationContext(),"Feedback from Server= "+ a1.toString(),Toast.LENGTH_LONG).show();
                                    }
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

                    Toast.makeText(getApplicationContext(), "Post on facebook", Toast.LENGTH_SHORT).show();
                    count++;

                    String restname1= restname;
                    String longi=hashmap.get(2);
                    String latitute=hashmap.get(3);


                    //start post on facebook
                    shareDialog = new ShareDialog(OneRestaurant.this);
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle("ChewIn Restaurant Mobile App")
                                .setContentDescription(
                                        "I am eating at "+restname1)
                                .setContentUrl(Uri.parse("http://ucd-nlmsc-teamproject.github.io/Snapr-Team/"))
                                .setImageUrl(Uri.parse("http://ucd-nlmsc-teamproject.github.io/Snapr-Team/images/chewInLogo.PNG"))
                                .build();

                        shareDialog.show(linkContent);
                    }

                    //end post on facebook


                }
                if (menuItemId == R.id.bb_menu_rating) {
                    //Rate restaurants
                    //Intent to UserRatings class
                    Intent ratingIntent=new Intent(OneRestaurant.this,UserRatings.class);
                    ratingIntent.putExtra("hashmapdetails", hashmap);
                    ratingIntent.putExtra("restname", restname);
                    ratingIntent.putExtra("currentlat", clatitude);
                    ratingIntent.putExtra("currentlong", clongitude);
                    startActivity(ratingIntent);

                    count++;

                }

                count++;
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bb_menu_bookmark) {
                    // The user reselected the "Recents" tab. React accordingly.
                }
            }


        });
        //Decorateion Bottom Bar
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");

    }
}
