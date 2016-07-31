package chewin.app.com;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ajinkya on 7/15/2016.
 */

public class CheckInList extends AppCompatActivity {
    ListView list;
    private ArrayAdapter<String> listAdapter;
    DBHelper dbhelp;
    SQLiteDatabase database;
    Cursor c;
    TextView textView115;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);


        Bundle b1 = getIntent().getExtras();
        String fromclass= b1.getString("class");
        Toast.makeText(getBaseContext(), fromclass, Toast.LENGTH_LONG).show();
        textView115=(TextView) findViewById(R.id.textView115);

        dbhelp=new DBHelper(getApplicationContext(), "restaurant", null,1);
        database=dbhelp.getWritableDatabase();

        c=database.rawQuery("select * from checkin", null);
        textView115.setText("              CheckIn List");
        int count=0;
        if(c.moveToNext())
        {
            while (c.isAfterLast() == false) {
                count++;
                c.moveToNext();
            }

        }
        c.moveToFirst();

        final String[] restaurant_name = new String[count];
        final String[] latitude= new String[count];
        final String[] longitude= new String[count];


        int k=0;
        if(c.moveToNext())
        {
            while (c.isAfterLast() == false)
            {
                restaurant_name[k] = c.getString(c.getColumnIndex("restaurant_name"));
                latitude[k]=c.getString(c.getColumnIndex("latitude"));
                longitude[k]= c.getString(c.getColumnIndex("longitude"));
                k++;
                c.moveToNext();

            }
        }

        CustomListBookMark adapter = new CustomListBookMark(CheckInList.this, restaurant_name, latitude,longitude);
        list=(ListView)findViewById(R.id.mainListViewbookmark);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                //Toast.makeText(BookmarkList.this, "You Clicked at " +restaurant_name[+ position], Toast.LENGTH_SHORT).show();
                startActivity(new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("geo:"+latitude[+position]+","+longitude[+position])));
            }
        });

    }
}

