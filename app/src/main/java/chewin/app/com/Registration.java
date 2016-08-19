package chewin.app.com;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

public class Registration extends Activity  implements View.OnClickListener {

   private DatePicker datePicker;
   private Calendar calendar;
   private Button datebutton,submit;
   private int year, month, day;
   private ImageButton ib;
   private Calendar cal;
   private EditText edittext223;
   CheckBox  italian, indian,irish,maxican,American,Spanish;
   RadioButton vegnovg;
   private boolean fbLogged;
   private RadioGroup radiovegnoveg;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.registration);

      final String[] cuisines = {""};

      //Set and intialize widgets
      radiovegnoveg=(RadioGroup) findViewById(R.id.radioGroup);
      edittext223=(EditText)findViewById(R.id.edittext223);
      italian = (CheckBox) findViewById(R.id.italian);
      indian = (CheckBox) findViewById(R.id.indian);
      irish = (CheckBox) findViewById(R.id.irish);
      maxican= (CheckBox) findViewById(R.id.maxican);
      American= (CheckBox) findViewById(R.id.American);
      Spanish= (CheckBox) findViewById(R.id.Spanish);

      ib = (ImageButton) findViewById(R.id.imageButton1);

     //define datebutton
      submit = (Button) findViewById(R.id.submitreg);

      //Create an instance of calendar
      calendar = Calendar.getInstance();
      year = calendar.get(Calendar.YEAR);
      month = calendar.get(Calendar.MONTH);
      day = calendar.get(Calendar.DAY_OF_MONTH);
      ib.setOnClickListener(this);

      //Define submit button
      submit = (Button) findViewById(R.id.submitreg);

      //Handle set OnClickListener action
      submit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            //Get the intent from the Welcome Activity
            Intent recieveDataIntent = getIntent();

            String rDp = null;
            String rUsername = null;
            String rEmail = null;
            final String rDob;
            final String rGender;
            String name = null;

            //set value getting from intent
            final String fbfalse = recieveDataIntent.getStringExtra("FBFALSE");
            final String gplusfalse = recieveDataIntent.getStringExtra("GPLUSFALSE");
            //&& recieveDataIntent.getStringExtra("GPLUSTRUE").equals("TRUE")

            //Check for login with Facebook or Login with google plus
            if ((fbfalse != null && fbfalse.equalsIgnoreCase("FALSE"))) {
               //rDp = recieveDataIntent.getStringExtra("P_PHOTOURL");
               //rAgeRange = recieveDataIntent.getStringExtra("P_AGE_RANGE");
               //rUsername = recieveDataIntent.getStringExtra("P_NAME");
               rEmail = recieveDataIntent.getStringExtra("P_EMAIL");
               rDob = recieveDataIntent.getStringExtra("P_DOB");
               //rGender = recieveDataIntent.getStringExtra("P_GENDER");

               name=recieveDataIntent.getStringExtra("P_NAME");
               rUsername=rEmail.substring(0,rEmail.indexOf('@'));

               fbLogged = false;

            } else if (gplusfalse != null && gplusfalse.equalsIgnoreCase("FALSE")) {


               rDp = recieveDataIntent.getStringExtra("P_PHOTOURL");
               //rAgeRange = recieveDataIntent.getStringExtra("P_AGE_RANGE");
               String fbId = recieveDataIntent.getStringExtra("FBID");
               //rname = recieveDataIntent.getStringExtra("P_NAME");
               rEmail = recieveDataIntent.getStringExtra("P_EMAIL");
               rDob = recieveDataIntent.getStringExtra("P_DOB");
               //rGender = recieveDataIntent.getStringExtra("P_GENDER");

               name=recieveDataIntent.getStringExtra("P_NAME");
               rUsername=rEmail.substring(0,rEmail.indexOf('@'));

               fbLogged = true;

               rDp = "https://graph.facebook.com/" + fbId + "/picture?type=large";
               //Picasso.with(Registration.this).load(imageurl).into(dp);

            }

            //Check and get cheked check box value
            if(indian.isChecked() == true) {
               cuisines[0].concat("indian,");
            }
            if(irish.isChecked() == true) {
               cuisines[0].concat("irish,");
            }
            if(italian.isChecked() == true) {
               cuisines[0].concat("italian,");
            }
            if(maxican.isChecked() == true) {
               cuisines[0].concat("maxican,");
            }
            if(American.isChecked() == true) {
               cuisines[0].concat("american,");
            }
            if(Spanish.isChecked() == true) {
               cuisines[0].concat("spanish");
            }
            cuisines[0] ="indian,irish,spanish";
            final String a="indian,irish,spanish";
            int selectedId=radiovegnoveg.getCheckedRadioButtonId();
            vegnovg=(RadioButton)findViewById(selectedId);

            //Toast and display message
            Toast.makeText(getApplicationContext(),"Users information succesfully saved",Toast.LENGTH_SHORT).show();

            //send user data to the server
            final String finalRUsername = rUsername;
            final String finalName = name;
            final String finalREmail = rEmail;
            final String finalRDp = rDp;
            new Thread(new Runnable() {
               public void run() {

                  try{
                     //android registration start
                     //Call AndroidServlet to built user profile
                     URL url1 = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/AndroidServlet");
                     URLConnection connection1 = url1.openConnection();
                     connection1.setDoOutput(true);

                     String[] split= finalName.split("");
                     String fname=finalRUsername.substring(0,6);
                     String lname=finalRUsername.substring(7,10);

                     //Create queystring and set values
                     Uri.Builder builder = new Uri.Builder()
                             .appendQueryParameter("username", finalRUsername)
                             .appendQueryParameter("fname", fname)
                             .appendQueryParameter("sname",lname)
                             .appendQueryParameter("dob", String.valueOf(edittext223.getText()))
                             .appendQueryParameter("gender","1")
                             .appendQueryParameter("veg","1")
                             .appendQueryParameter("email", finalREmail)
                             .appendQueryParameter("cuisines", a.toString());

                     String query1 = builder.build().getEncodedQuery();

                     OutputStream os1 = connection1.getOutputStream();
                     BufferedWriter writer1 = new BufferedWriter(
                             new OutputStreamWriter(os1, "UTF-8"));
                     writer1.write(query1); //Write querystring and send to AndroidServlet
                     writer1.close();  //Close BufferedWriter

                     //BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                     //Get feedback from server
                     //String returnString1="";
                     //String feedback1=null;
                     //while ((returnString1 = in1.readLine()) != null)
                     //{
                       // feedback1= returnString1;
                     //}
                     //in1.close();
                     //final String a1=feedback1;

                     //print feedback got from server
                     runOnUiThread(new Runnable() {
                        public void run() {
                       //    if(a1!=null)
                         //  {
                           //   Toast.makeText(getApplicationContext(),"Feedback from Server= "+ a1.toString(),Toast.LENGTH_LONG).show();
                           //}
                        }
                     });

                  }catch(Exception e)
                  {
                     Log.d("Exception",e.toString()); //Write debug log file with errors
                     System.out.println("Conection Pasing Excpetion = " + e);
                     Log.e("Error= ", Log.getStackTraceString(e));
                  }

                  //Intent to class WelcomeScreenLogin
                  Intent sendDataIntent = new Intent(Registration.this, WelcomeScreenLogin.class);
                  sendDataIntent.putExtra("uname", finalName);
                  sendDataIntent.putExtra("bImage", finalRDp);
                  sendDataIntent.putExtra("FBFALSE", fbfalse);
                  sendDataIntent.putExtra("GPLUSFALSE", gplusfalse);
                  startActivity(sendDataIntent);
                  finish();
                  //Toast.makeText(getApplicationContext()," Registration uname= "+finalName+" "+finalRDp,Toast.LENGTH_LONG).show();
                Log.d("registration", "Registration uname= "+finalName+" "+finalRDp);

               }

            }).start();
         }
      });
   }

   @Override
   @Deprecated
   protected Dialog onCreateDialog(int id) {
      return new DatePickerDialog(this, datePickerListener, year, month, day);
   }

   //Calll DatePicker widget
   private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
      public void onDateSet(DatePicker view, int selectedYear,
                            int selectedMonth, int selectedDay) {
         edittext223.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                 + selectedYear);
      }
   };

   @Override
   public void onClick(View view) {
      showDialog(0);
   }
}
