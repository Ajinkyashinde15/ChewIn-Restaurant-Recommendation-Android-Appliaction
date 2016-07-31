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

    private final Activity context;
    String[] restaurant_name;
    String[] latitude;
    String[] longitude;
    public CustomListBookMark(Activity context, String[] restaurant_name, String[] latitude, String[] longitude)
    {
        super(context, R.layout.simplerow_bookmark, restaurant_name);
        this.context = context;
        this.restaurant_name = restaurant_name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.simplerow_bookmark, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.bookmarkname);

        txtTitle.setText(restaurant_name[position]);

        return rowView;
    }
}