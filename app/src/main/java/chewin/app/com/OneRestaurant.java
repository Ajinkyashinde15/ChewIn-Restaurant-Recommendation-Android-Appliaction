package chewin.app.com;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ajinkya on 6/26/2016.
 */
public class OneRestaurant extends Activity
{
    RatingBar rtbProductRating;
    TextView rsname,reviewtext,distance,cuisuine,addressText,callText,webtext,emailText;
    ImageButton googleMapButton;
    ImageView restoimage;
    DBHelper dbhelp;
    SQLiteDatabase db;
    Cursor c;

    private BottomBar mBottomBar;
    int count=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_restaurant);

        dbhelp=new DBHelper(getApplicationContext(), "restaurant", null,1);
        db=dbhelp.getWritableDatabase();

        rtbProductRating=(RatingBar)findViewById(R.id.rtbProductRating);
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

        Intent intent = getIntent();
        final ArrayList<String> hashmap = intent.getStringArrayListExtra("hashmapdetails");
        final String clatitude = intent.getStringExtra("currentlat");
        final String clongitude = intent.getStringExtra("currentlong");
        final String restname = intent.getStringExtra("restname");

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
        rtbProductRating.setNumStars(Integer.parseInt(hashmap.get(5)));

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
                Intent gintent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + Double.parseDouble(clatitude) + "," + Double.parseDouble(clongitude) + "&daddr=" + hashmap.get(2) + "," + hashmap.get(3)));
                gintent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(gintent);
            }
        });

        //show phone no
        callText.setText(hashmap.get(7));
        Toast.makeText(OneRestaurant.this," Phone No= "+hashmap.get(7),Toast.LENGTH_LONG).show();
        callText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse(hashmap.get(7));
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
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
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "ajinkyashinde15@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "ChewIn Restaurant Finder");
                intent.putExtra(Intent.EXTRA_TEXT, "Food Delivery");
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        //Bottom Bar
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bb_menu_home) {
                    if (count > 1) {
                        Intent i = new Intent(OneRestaurant.this, WelcomeScreen.class);
                        startActivity(i);
                        count++;
                    }
                }
                if (menuItemId == R.id.bb_menu_bookmark)
                {

                    ContentValues cv= new ContentValues();

                    cv.put("username", "demo");
                    cv.put("restaurant_name",restname);
                    cv.put("latitude", hashmap.get(2));
                    cv.put("longitude", hashmap.get(3));

                    db.insert("bookmark", null, cv);

                    Toast.makeText(getApplicationContext(), "Bookmark has been saved ", Toast.LENGTH_SHORT).show();
                    count++;
                }
                if (menuItemId == R.id.bb_menu_checkin) {

                    ContentValues cv= new ContentValues();

                    cv.put("username", "demo");
                    cv.put("restaurant_name",restname);
                    cv.put("latitude", hashmap.get(2));
                    cv.put("longitude", hashmap.get(3));
                    db.insert("checkin", null, cv);

                    Toast.makeText(getApplicationContext(), "Post on facebook", Toast.LENGTH_SHORT).show();
                    count++;
                }
                if (menuItemId == R.id.bb_menu_rating) {

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
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");

    }
}
