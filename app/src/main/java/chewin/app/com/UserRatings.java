package chewin.app.com;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Ajinkya on 7/7/2016.
 */
public class UserRatings extends AppCompatActivity {
     ArrayList<String> hashmap;
    String clatitude,clongitude,restname;
    Button submit;
    EditText editTextfeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedbackandratings);
 //       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("User Ratings");
        // etc...
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        hashmap= intent.getStringArrayListExtra("hashmapdetails");
        clatitude = intent.getStringExtra("currentlat");
        clongitude = intent.getStringExtra("currentlong");
        restname = intent.getStringExtra("restname");

        submit = (Button) findViewById(R.id.buttonrating);
        editTextfeedback=(EditText)findViewById(R.id.editTextfeedback);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!editTextfeedback.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Feedback has been saved ",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(UserRatings.this,OneRestaurant.class);
                    i.putExtra("hashmapdetails", hashmap);
                    i.putExtra("restname", restname);
                    i.putExtra("currentlat", clatitude);
                    i.putExtra("currentlong",clongitude);
                    startActivity(i);

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
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

