package chewin.app.com;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


//Welcome activity after splash screen (facebook and google plus)
public class WelcomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    //Initialize variables
    private TextView[] dots;
    //private int[] layouts;
    private Button btnSkip;
    //private Button btnNext;
    private PrefManager prefManager;
    //google plus login
    private SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions googleSignInOptions;
    private static final int REQUEST_CODE_LOGIN = 10;
    private static final String P_NAME = "P_NAME";
    private static final String P_EMAIL = "P_EMAIL";
   // private static final String P_DOB = "P_DOB";
    private static final String P_GENDER = "P_GENDER";
    private static final String P_PHOTOURL = "P_PHOTOURL";
    private static final String FBTRUE = "FBTRUE";
    private static final String FBFALSE = "FBFALSE";
    private static  final String GPLUSFALSE = "GPLUSFALSE";
    private static  final String GPLUSTRUE = "GPLUSTRUE";
    private boolean isFbBtnClicked = false;
    SharedPreferences sharedPreferences;

    //Start video declaration
    GifView gifView;
    //end video declaration



    //fb
    private CallbackManager callbackManager = null;
    private LoginButton fbLoginButton;

    //new fb

    //CallbackManager callbackManager;
   /* Button share,details;
    ShareDialog shareDialog;
    LoginButton login;
    ProfilePictureView profilePic;
    Dialog details_dialog;
    TextView details_txt;
    */

    //end new fb
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            //fb
        initializeFacebookSDK();
        //end fb
         sharedPreferences=getSharedPreferences("loginusersdetails",Context.MODE_PRIVATE);
        //Checking for first time launch - before calling setContentView()


        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);
        //*******************************background***************************************************
         gifView = (GifView) findViewById(R.id.gif_view);
         //*******************************End background*********************************************
        //*******************************Skip********************************************************
       // viewPager = (ViewPager) findViewById(R.id.view_pager);
        //dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });
         //*******************************End Skip***************************************************


         //************************start google plus login*******************************************
        buildGPlus();
        custimizeGPlusFbSignBtn();
        setBtnClickListeners();
       //**************************End google plus login*******************************************

         prefManager = new PrefManager(this);
         if (!prefManager.isFirstTimeLaunch() && AccessToken.getCurrentAccessToken() == null) {
             Log.d("Skip Login", "Skip Login part so go to WelcomeScreen");
             launchHomeScreen();
             finish();
         }
         if (!prefManager.isFirstTimeLaunch() && AccessToken.getCurrentAccessToken() != null) {
             Log.d("Facebook", "The Facebook is logged in. So go to WelcomeScreenLogin");
             RequestFacebookData();
             //Intent intnt = new Intent(WelcomeActivity.this, WelcomeScreenLogin.class);
             //intnt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
             //startActivity(intnt);
             //finish();
         }


    }//**********end onCreate

//fb
protected void initializeFacebookSDK(){
    FacebookSdk.sdkInitialize(getApplicationContext());
   // callbackManager = CallbackManager.Factory.create();
}

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    //start conditional FB/Google plus
    private void buildGPlus() {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API,
                googleSignInOptions)
                .addApi(Plus.API)
                .build();

    }

    private void setBtnClickListeners(){
        // Button listeners
        signInButton.setOnClickListener(this);
        fbLoginButton.setOnClickListener(this);
    }

    //@Override

    //Define on Click method
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                Toast.makeText(this, "Signing in using google plus in progress", Toast.LENGTH_SHORT).show();
                gPlusSignIn();
                break;
            case R.id.login_button:
                Toast.makeText(this, "Signing in using facebook in progress", Toast.LENGTH_LONG).show();
                isFbBtnClicked = true;
                facebookSignIn();

                break;

        }
    }

    //If user choose sign with facebook
    private void facebookSignIn() {

        //fb
       callbackManager = CallbackManager.Factory.create();

        /*login = (LoginButton)findViewById(R.id.login_button);
        profilePic = (ProfilePictureView)findViewById(R.id.picture);
        shareDialog = new ShareDialog(this);
        share = (Button)findViewById(R.id.share);
        details = (Button)findViewById(R.id.details);
        */
        fbLoginButton.setReadPermissions("public_profile email");

        /*share.setVisibility(View.INVISIBLE);
        details.setVisibility(View.INVISIBLE);
        details_dialog = new Dialog(this);
        details_dialog.setContentView(R.layout.dialog_details);
        details_dialog.setTitle("Details");
        details_txt = (TextView)details_dialog.findViewById(R.id.details);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details_dialog.show();
            }
        });

        if(AccessToken.getCurrentAccessToken() != null){
            RequestData();
            share.setVisibility(View.VISIBLE);
            details.setVisibility(View.VISIBLE);
        }
        /*login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        if(AccessToken.getCurrentAccessToken() != null) {
                    share.setVisibility(View.INVISIBLE);
                    details.setVisibility(View.INVISIBLE);
                    profilePic.setProfileId(null);
                }
            }
        });*/
        /*share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareLinkContent content = new ShareLinkContent.Builder().build();
                Log.d("Share", "Before show shared Content!");
                shareDialog.show(content);

            }
        });*/
       fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if(AccessToken.getCurrentAccessToken() != null){
                    RequestFacebookData();
                    //share.setVisibility(View.VISIBLE);
                    //details.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
            }
        });


    }

    private void gPlusSignIn() {

        Intent loginIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(loginIntent, REQUEST_CODE_LOGIN);
    }
    //end conditional FB/Google plus


    //new new fb

    private void custimizeGPlusFbSignBtn(){
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        //.addScope(Plus.SCOPE_PLUS_LOGIN)
        //signInButton.setSize(SignInButton.SIZE_ICON_ONLY);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(new Scope[]{Plus.SCOPE_PLUS_LOGIN});
        //signInButton.setScopes(googleSignInOptions.getScopeArray());

        fbLoginButton = (LoginButton)findViewById(R.id.login_button);
    }
    //end new new fb


    //new fb
    public void RequestFacebookData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                final String[] name = new String[1];
                String dpUrl = null;
                final String email;
                final String gender;
                try {
                    if(json != null){

                        //String text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
                        //String text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link")
                        //        +"<br><br><b>Birthday :</b> "+json.getString("birthday") + "<br><br><b>Gender :</b> "+json.getString("gender");
                        //details_txt.setText(Html.fromHtml(text));
                        //profilePic.setProfileId(json.getString("id"));

                        //Set SharedPreferences variables
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        name[0] = json.getString("name");
                        editor.putString("username", name[0]);
                        email = json.getString("email");
                       // dob = json.getString("birthday");
                       gender = json.getString("gender");
                       String fbId = json.getString("id");
                        dpUrl = "https://graph.facebook.com/" + fbId + "/picture?type=large";

                        //Print Logcat with name and email
                        Log.d("name", name[0]);
                        Log.d("email", email);


                        //start existing user
                        final String finalDpUrl = dpUrl;
                        new Thread(new Runnable() {
                            public void run() {

                                try{
                                    //android welcomeActivity

                                    //Check for existing user
                                    Log.d("Run the server", "Before runing the server REST");

                                    URL url1 = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/AndroidServlet");

                                    URLConnection connection1 = url1.openConnection();
                                    connection1.setDoOutput(true);
                                    name[0] =email.substring(0,email.indexOf('@'));

                                    Uri.Builder builder = new Uri.Builder()
                                            .appendQueryParameter("username", name[0]);
                                    String query1 = builder.build().getEncodedQuery();

                                    //Read acknowledgement fromm server
                                    OutputStream os1 = connection1.getOutputStream();
                                    BufferedWriter writer1 = new BufferedWriter( new OutputStreamWriter(os1, "UTF-8"));
                                    writer1.write(query1);
                                    writer1.flush();
                                    writer1.close();

                                    BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                                    String returnString1="";
                                    String feedback1=null;

                                    //Get feedback from server
                                    while ((returnString1 = in1.readLine()) != null)
                                    {
                                        feedback1= returnString1;
                                    }
                                    in1.close();

                                    //Check - if return value is Yes then user is already exist else return no
                                    //final String a1="Yes";
                                    final String a1=feedback1;
                                    Log.d("A1", a1);
                                    //final String a1="Yes";
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            if(a1!=null)
                                            {
                                                if(a1.toString().equals("No"))
                                                {
                                                    // user is not exist then build profile of user

                                                    Intent sendDataIntent = new Intent(WelcomeActivity.this, Registration.class);
                                                    sendDataIntent.putExtra(P_NAME, name[0]);
                                                    sendDataIntent.putExtra(P_EMAIL, email);
                                                    // sendDataIntent.putExtra(P_DOB, dob);
                                                    sendDataIntent.putExtra(P_GENDER, gender);
                                                    sendDataIntent.putExtra(P_PHOTOURL, finalDpUrl);

                                                    sendDataIntent.putExtra(GPLUSFALSE, "FALSE");
                                                    sendDataIntent.putExtra(FBTRUE, "TRUE");
                                                    Toast.makeText(getApplicationContext(),"You are new user",Toast.LENGTH_LONG).show();
                                                    startActivity(sendDataIntent);
                                                    Log.d("a1 is No", "senDataIntent is for No");
                                                    finish();
                                                }else if(a1.toString().equals("Yes"))
                                                {
                                                    //  Yes user is already exist

                                                    Intent sendDataIntent = new Intent(WelcomeActivity.this, WelcomeScreenLogin.class);
                                                    sendDataIntent.putExtra("uname", name[0]);
                                                    sendDataIntent.putExtra(P_EMAIL, email);
                                                    // sendDataIntent.putExtra(P_DOB, dob);                                                Toast.makeText(getApplicationContext(),"Feedback from Server= "+ a1.toString(),Toast.LENGTH_LONG).show();
                                                    Toast.makeText(getApplicationContext(),"You are existing user",Toast.LENGTH_LONG).show();
                                                    sendDataIntent.putExtra("bImage", finalDpUrl);

                                                    sendDataIntent.putExtra(P_GENDER, gender);

                                                    sendDataIntent.putExtra(GPLUSFALSE, "FALSE");
                                                    sendDataIntent.putExtra(FBTRUE, "TRUE");

                                                    Log.d("a1 is Yes", "senDataIntent is for Yes");
                                                    startActivity(sendDataIntent);
                                                    finish();
                                                }
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


                        finish();
          }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture, gender");
        request.setParameters(parameters);
        request.executeAsync();
    }
    //end fb




    //CONTINUE google plus login

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_LOGIN){
            GoogleSignInResult googleSignInResult =  Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
            Person person = Plus.PeopleApi.getCurrentPerson(googleApiClient);

            setUserInformation(person, googleSignInAccount);

            if (!googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
            Toast.makeText(this, "Connected!", Toast.LENGTH_SHORT).show();

        }

        //fb
        //super.onActivityResult(requestCode, resultCode, data);
        if (isFbBtnClicked) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
       // Log.d("data",data.toString());
        //end fb

    }

    //END google plus login


    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, WelcomeScreen.class));
        finish();
    }

    /**
     * Making notification bar transparent
     */

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }


    public void setUserInformation(Person person, GoogleSignInAccount googleSignInAccount) {
        try{
            String name, dpUrl, email,  gender = "";
            Intent sendDataIntent = new Intent(WelcomeActivity.this, Registration.class);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            name = googleSignInAccount.getDisplayName();
            editor.putString("username",name);
            email = googleSignInAccount.getEmail();
           // dob = person.getBirthday();
            if(person.getGender() == 0){
                gender = "Male";
                sendDataIntent.putExtra(P_GENDER, gender);
            }
            if(person.getGender() == 1){
                gender = "Female";
                sendDataIntent.putExtra(P_GENDER, gender);
            }
            dpUrl = googleSignInAccount.getPhotoUrl().toString();

            sendDataIntent.putExtra(P_NAME, name);
            sendDataIntent.putExtra(P_EMAIL, email);
            //sendDataIntent.putExtra(P_DOB, dob);
            sendDataIntent.putExtra(P_GENDER, gender);
            sendDataIntent.putExtra(P_PHOTOURL, dpUrl);
            sendDataIntent.putExtra(GPLUSTRUE,"TRUE");
            sendDataIntent.putExtra(FBFALSE, "FALSE");
            startActivity(sendDataIntent);
            finish();

        }
        catch (Exception exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    //@override
    protected void onResume(){
        super.onResume();
        if (googleApiClient.isConnected()) {
            googleApiClient.connect();
        }

        //fb
        AppEventsLogger.activateApp(this);

        //end fb
    }




}
