package chewin.app.com;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity { //Starting screen splashscreen display


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);  //Set view activity_splash_screen

        //Define and initialize widget value
        final ImageView imgView = (ImageView)findViewById(R.id.imageView);
        final TextView txtMt = (TextView) findViewById(R.id.txtMoto);

        //Define Animation
        final Animation slideLeft = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.right_to_left);
        //slideLeft.setAnimationListener(this);
        final Animation slideRight = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.left_to_right);
        //slideRight.setAnimationListener(this);

        //Start Animation effect
        imgView.startAnimation(slideRight);
        txtMt.startAnimation(slideLeft);

        //Call and intent to next activity (WelcomeActivity)
        slideRight.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                finish();
                Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
                startActivity(intent);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //this.overridePendingTransition(R.anim.animation_enter,
          //      R.anim.animation_leave);
    }


}
