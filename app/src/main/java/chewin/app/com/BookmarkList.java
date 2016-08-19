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


public class BookmarkList extends AppCompatActivity {
    ListView list;
    private ArrayAdapter<String> listAdapter;
    DBHelper dbhelp;
    SQLiteDatabase database;
    Cursor c;

    @Override
    public void onBackPressed() {
        //Execute your code here
        Intent i = new Intent(BookmarkList.this, WelcomeScreenLogin.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);


        Bundle b1 = getIntent().getExtras();
        String fromclass= b1.getString("class");
      //  Toast.makeText(getBaseContext(), fromclass, Toast.LENGTH_LONG).show();

        dbhelp=new DBHelper(getApplicationContext(), "restaurant", null,1);  //store in to local database
        database=dbhelp.getWritableDatabase();

        c=database.rawQuery("select * from bookmark", null);

        new Thread(new Runnable() {
            public void run() {

                try{
                    //android bookmark
                    URL url1 = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/BookmarkListServlet"); //connect with BookmarkListServlet

                    URLConnection connection1 = url1.openConnection();  //crate an instance of URLConnection
                    connection1.setDoOutput(true);
                    SharedPreferences uname=getSharedPreferences("loginusersdetails",0);    //Access SharedPreferences loginusersdetails

                    Uri.Builder builder1 = new Uri.Builder()
                            .appendQueryParameter("username", uname.getString("username","").toString());  //Send username to the server to access individual bookmark list

                    String query1 = builder1.build().getEncodedQuery();

                    OutputStream os1 = connection1.getOutputStream();
                    BufferedWriter writer1 = new BufferedWriter(
                            new OutputStreamWriter(os1, "UTF-8"));
                    writer1.write(query1);
//                            writer.flush();
                    writer1.close(); //Close object of BufferedWriter

                    BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));

                    String returnString1="";
                    String feedback1=null;
                    while ((returnString1 = in1.readLine()) != null)    //Waiting for feedback from server
                    {
                        feedback1= returnString1;  //accessing feedback from server
                    }
                    in1.close();

                }catch(Exception e) //Catch run time exception
                {
                    Log.d("Exception",e.toString());  //Write debug log file with errors
                    Log.e("Error= ", Log.getStackTraceString(e));
                }

            }
        }).start();

        int count=0;
        if(c.moveToNext())
        {
            while (c.isAfterLast() == false) {
                count++;
                c.moveToNext(); //Scan all records
            }

        }
        c.moveToFirst();

        String restaurant_name[];
        final String latitude[];
        final String longitude[];

        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {   //Add permission if VERSION.SDK_INT > 9
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            URL url = new URL("http://137.43.93.134:8080/MS.SNAPR.RS/rest/Service/BookmarkRestaurants"); //Call BookmarkRestaurants servelet wil return xml
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); //Create an instance of DocumentBuilderFactory
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));  //
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("bookmark");   //Access parent attributes (bookmark)

            restaurant_name = new String[nodeList.getLength()]; // Initialize array restaurant_name
            latitude = new String[nodeList.getLength()];
            longitude = new String[nodeList.getLength()];

            for (int i = 0; i < nodeList.getLength(); i++) {  //Scan through all the records in xml file

                Node node = nodeList.item(i);   //Getting one at a time

                restaurant_name[i] = new String();
                latitude[i] = new String();
                longitude[i] = new String();

                Element fstElmnt = (Element) node;

                NodeList nameList = fstElmnt.getElementsByTagName("resName");// Access child resName
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                restaurant_name[i]=((Node) nameList.item(0)).getNodeValue(); //Store value in an array

                NodeList latitudeList = fstElmnt.getElementsByTagName("latitude");// Access child latitude
                Element latitudeElement = (Element) latitudeList.item(0);
                latitudeList = latitudeElement.getChildNodes();
                latitude[i]=((Node) latitudeList.item(0)).getNodeValue();//Store value in an array

                NodeList longitudeList = fstElmnt.getElementsByTagName("longitude");// Access child longitude
                Element longitudeElement = (Element) longitudeList.item(0);
                longitudeList = longitudeElement.getChildNodes();
                longitude[i]=((Node) longitudeList.item(0)).getNodeValue();//Store value in an array
            }

            CustomListBookMark adapter = new CustomListBookMark(BookmarkList.this, restaurant_name, latitude,longitude); //Display details on custome listview
            list=(ListView)findViewById(R.id.mainListViewbookmark);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id)
                {
                    //Toast.makeText(BookmarkList.this, "You Clicked at " +restaurant_name[+ position], Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("geo:"+latitude[+position]+","+longitude[+position]))); //Call google map and point restaraunt name
                }
            });

        } catch (Exception e) {  //Catching exception
            System.out.println("XML Pasing Excpetion = " + e);
            Log.e("Error= ",Log.getStackTraceString(e));
        }




    }
}
