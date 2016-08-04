package chewin.app.com;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class PostToFaceBook extends AppCompatActivity {
    private ShareDialog shareDialog;
    Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_to_face_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shareDialog = new ShareDialog(this);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Hello Guys")
                            .setContentDescription(
                                    "Coder who learned and share")
                            .setContentUrl(Uri.parse("http://ucd-nlmsc-teamproject.github.io/Snapr-Team/"))
                            .setImageUrl(Uri.parse("http://ucd-nlmsc-teamproject.github.io/Snapr-Team/images/chewInLogo.PNG"))
                            .build();

                    shareDialog.show(linkContent);
                }
            }
        });


    }

}
