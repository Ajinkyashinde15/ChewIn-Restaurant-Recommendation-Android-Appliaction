package chewin.app.com;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
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

import java.io.IOException;


public class WelcomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    /*private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    */
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
    private static final String P_DOB = "P_DOB";
    private static final String P_GENDER = "P_GENDER";
    private static final String P_PHOTOURL = "P_PHOTOURL";
    private static final String FBTRUE = "FBTRUE";
    private static final String FBFALSE = "FBFALSE";
    private static  final String GPLUSFALSE = "GPLUSFALSE";
    private static  final String GPLUSTRUE = "GPLUSTRUE";
    private boolean isFbBtnClicked = false;
    //Start video declaration
    //TextView textViewInfo;
    GifView gifView;
    //end video declaration


       //end Google plus


    //end google plus login

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

        //Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }



        setContentView(R.layout.activity_welcome);


         //*******************************background***************************************************
         gifView = (GifView) findViewById(R.id.gif_view);
         //textViewInfo = (TextView) findViewById(R.id.textinfo);
         //String stringInfo = "";
         //stringInfo += "Duration: " + gifView.getMovieDuration() + "\n";
         //stringInfo += "W x H: " + gifView.getMovieWidth() + " x " + gifView.getMovieHeight() + "\n";
         //textViewInfo.setText(stringInfo);



         //Display display = this.getWindowManager().getDefaultDisplay();
         //Point size = new Point();
         //display.getSize(size);
         //int screenWidth = size.x;


         //Get the width of the screen
         //DisplayMetrics displaymetrics = new DisplayMetrics();
         //this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
         //int screenWidth = displaymetrics.widthPixels;



         // Point size = new Point();
         //getWindowManager().getDefaultDisplay().getSize(size);
         // int screenWidth = size.x;
         //int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
         //Log.d("testing gLayout", "before GifView getLayoutParams");
         //Get the SurfaceView layout parameters
         //android.view.ViewGroup.LayoutParams lp = gifView.getLayoutParams();
         //Set the width of the SurfaceView to the width of the screen
         //lp.width = screenWidth;
         //Set the height of the SurfaceView to match the aspect ratio of the video
         //be sure to cast these as floats otherwise the calculation will likely be 0
         //lp.height = (int) (((float)gifView.getMovieHeight() / (float)gifView.getMovieWidth()) * (float)screenWidth);
         //Commit the layout parameters
         //gifView.setLayoutParams(lp);
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

         /*signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(loginIntent, REQUEST_CODE_LOGIN);

            }
        });*/
         //**************************End google plus login*******************************************

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
        //findViewById(R.id.login_button).setOnClickListener(this);
        //findViewById(R.id.sign_in_button).setOnClickListener((View.OnClickListener) this);
    }

    //@Override
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

    private void facebookSignIn() {

        //fb
       callbackManager = CallbackManager.Factory.create();

        /*login = (LoginButton)findViewById(R.id.login_button);
        profilePic = (ProfilePictureView)findViewById(R.id.picture);
        shareDialog = new ShareDialog(this);
        share = (Button)findViewById(R.id.share);
        details = (Button)findViewById(R.id.details);
        */
        fbLoginButton.setReadPermissions("public_profile email user_birthday");

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
                String name, dpUrl, email, dob, gender;
                try {
                    if(json != null){

                        //String text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
                        //String text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link")
                        //        +"<br><br><b>Birthday :</b> "+json.getString("birthday") + "<br><br><b>Gender :</b> "+json.getString("gender");
                        //details_txt.setText(Html.fromHtml(text));
                        //profilePic.setProfileId(json.getString("id"));


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
          }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture, gender, birthday");
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
            String name, dpUrl, email, dob, gender = "";
            Intent sendDataIntent = new Intent(WelcomeActivity.this, WelcomeScreen.class);
            name = googleSignInAccount.getDisplayName();
            email = googleSignInAccount.getEmail();
            dob = person.getBirthday();
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
            sendDataIntent.putExtra(P_DOB, dob);
            sendDataIntent.putExtra(P_GENDER, gender);
            sendDataIntent.putExtra(P_PHOTOURL, dpUrl);
            sendDataIntent.putExtra(GPLUSTRUE,"TRUE");
            sendDataIntent.putExtra(FBFALSE, "FALSE");
            startActivity(sendDataIntent);

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
