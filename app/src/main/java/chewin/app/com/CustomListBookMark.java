package chewin.app.com;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListBookMark extends ArrayAdapter<String>
{

    private final Activity context; //Create an  instance of Activity
    String[] restaurant_name; //Declare an array restaurant_name
    String[] latitude;
    String[] longitude;
    public CustomListBookMark(Activity context, String[] restaurant_name, String[] latitude, String[] longitude)
    {
        super(context, R.layout.simplerow_bookmark, restaurant_name);
        this.context = context; // //Assign appropriate value context
        this.restaurant_name = restaurant_name;
        this.latitude = latitude;
        this.longitude = longitude; //Assign appropriate value longitude
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.simplerow_bookmark, null, true);  //Set view value

        TextView txtTitle = (TextView) rowView.findViewById(R.id.bookmarkname);

        txtTitle.setText(restaurant_name[position]);   //Set restaurant name title

        return rowView; //Return value
    }
}