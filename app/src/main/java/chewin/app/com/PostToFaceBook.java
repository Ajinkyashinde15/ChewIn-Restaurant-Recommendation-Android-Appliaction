package chewin.app.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class PostToFaceBook extends AppCompatActivity {
    private ShareDialog shareDialog;
    Button buttonmenusearch;
    EditText editTextfeedbackapp;
    String rDp=null,name=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_to_face_book);

        Intent recieveDataIntent = getIntent();
         rDp = recieveDataIntent.getStringExtra("bImage");
        name=recieveDataIntent.getStringExtra("uname");

        buttonmenusearch = (Button) findViewById(R.id.buttonmenusearch);
        editTextfeedbackapp=(EditText)findViewById(R.id.editTextfeedbackapp);
        buttonmenusearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String YouEditTextValue = editTextfeedbackapp.getText().toString();

                new Thread(new Runnable() {
                    public void run() {

                        try{
                            //android bookmark
                            //                           http://localhost:8090/MS.SNAPR.RS/rest/Service/MenuRestaurants
                            URL url1 = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/MenuServlet");
                            URLConnection connection1 = url1.openConnection();
                            // HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                            connection1.setDoOutput(true);

                            Uri.Builder builder1 = new Uri.Builder()
                                    .appendQueryParameter("query", YouEditTextValue);

                            String query1 = builder1.build().getEncodedQuery();

                            OutputStream os1 = connection1.getOutputStream();
                            BufferedWriter writer1 = new BufferedWriter(
                                    new OutputStreamWriter(os1, "UTF-8"));
                            writer1.write(query1);
//                            writer.flush();
                            writer1.close();

                            BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                            String returnString1="";
                            String feedback1=null;
                            while ((returnString1 = in1.readLine()) != null)
                            {
                                feedback1= returnString1;
                            }
                            in1.close();
                            final String a1=feedback1;


                        }catch(Exception e)
                        {
                            Log.d("Exception",e.toString());
                            System.out.println("Conection Pasing Excpetion = " + e);
                            Log.e("Error= ", Log.getStackTraceString(e));
                        }

                    }
                }).start();

                Intent ratingIntent=new Intent(PostToFaceBook.this,MenuSearch.class);
                ratingIntent.putExtra("bImage",rDp);
                ratingIntent.putExtra("uname",name);
                ratingIntent.putExtra("search",YouEditTextValue);
                startActivity(ratingIntent);
                finish();

            }
        });



    }

}
