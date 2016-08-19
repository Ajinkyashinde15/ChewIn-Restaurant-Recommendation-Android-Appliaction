package chewin.app.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



public class CheckInList extends AppCompatActivity {
    ListView list;
    private ArrayAdapter<String> listAdapter;
    DBHelper dbhelp;
    SQLiteDatabase database;
    Cursor c;
    TextView textView115;
    @Override
    public void onBackPressed() {
        //Execute your code here
        Intent i = new Intent(CheckInList.this, WelcomeScreenLogin.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);


        Bundle b1 = getIntent().getExtras();
        String fromclass= b1.getString("class");
//        Toast.makeText(getBaseContext(), fromclass, Toast.LENGTH_LONG).show();
        textView115=(TextView) findViewById(R.id.textView115);

        dbhelp=new DBHelper(getApplicationContext(), "restaurant", null,1);
        database=dbhelp.getWritableDatabase();
        new Thread(new Runnable() {
            public void run() {

                try{
                    //android bookmark
                    URL url1 = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/rest/Service/CheckInRestaurants"); //Send username to CheckInRestaurants servelet

                    URLConnection connection1 = url1.openConnection();
                    // HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    connection1.setDoOutput(true);
                    SharedPreferences uname=getSharedPreferences("loginusersdetails",0);//Access SharedPreferences value

                    Uri.Builder builder1 = new Uri.Builder()
                            .appendQueryParameter("username", uname.getString("username","").toString()); //Send username value to server

                    String query1 = builder1.build().getEncodedQuery();

                    OutputStream os1 = connection1.getOutputStream();
                    BufferedWriter writer1 = new BufferedWriter(
                            new OutputStreamWriter(os1, "UTF-8"));  //Create BufferedWriter object
                    writer1.write(query1);
                    writer1.close();  //Close BufferedWriter object

                    BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));  //Get

                    String returnString1="";
                    String feedback1=null;
                    while ((returnString1 = in1.readLine()) != null)//Access feedback from server
                    {
                        feedback1= returnString1;  // copy feedback to feedback1 varaible
                    }
                    in1.close();  //close BufferedReader object
                    final String a1=feedback1;

                }catch(Exception e) //Catch run time exception
                {
                    Log.d("Exception",e.toString());  //Write debug log file with errors
                    Log.e("Error= ", Log.getStackTraceString(e));
                }

            }
        }).start();

        textView115.setText("              CheckIn List");

        String restaurant_name[];
        final String latitude[];
        final String longitude[];

        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {   //Add permission if VERSION.SDK_INT > 9
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            URL url = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/rest/Service/CheckInRestaurants"); //Access Checked In Restaurants
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream())); //Crate an instance of Document
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("checkin");  //Access parent node

            restaurant_name = new String[nodeList.getLength()];
            latitude = new String[nodeList.getLength()];
            longitude = new String[nodeList.getLength()];

            for (int i = 0; i < nodeList.getLength(); i++) { //Scan through all the records in xml file

                Node node = nodeList.item(i);  //Access one node

                restaurant_name[i] = new String();  //Initialize restaurant_name array
                latitude[i] = new String();
                longitude[i] = new String();

                Element fstElmnt = (Element) node;

                NodeList nameList = fstElmnt.getElementsByTagName("resName");  //Access resName child
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                restaurant_name[i]=((Node) nameList.item(0)).getNodeValue();

                NodeList latitudeList = fstElmnt.getElementsByTagName("latitude");//Access latitude child
                Element latitudeElement = (Element) latitudeList.item(0);
                latitudeList = latitudeElement.getChildNodes();
                latitude[i]=((Node) latitudeList.item(0)).getNodeValue();

                NodeList longitudeList = fstElmnt.getElementsByTagName("longitude");//Access longitude child
                Element longitudeElement = (Element) longitudeList.item(0);
                longitudeList = longitudeElement.getChildNodes();
                longitude[i]=((Node) longitudeList.item(0)).getNodeValue();
            }

            CustomListBookMark adapter = new CustomListBookMark(CheckInList.this, restaurant_name, latitude,longitude); //Display details on custome listview
            list=(ListView)findViewById(R.id.mainListViewbookmark);  //Set layout where to displat list
            list.setAdapter(adapter); //Set adapter
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("geo:"+latitude[+position]+","+longitude[+position])));  // Launch google map activity
                }
            });

        } catch (Exception e) { //Catch run time exception
            System.out.println("XML Pasing Excpetion = " + e);
            Log.e("Error= ",Log.getStackTraceString(e));
        }

    }
}

