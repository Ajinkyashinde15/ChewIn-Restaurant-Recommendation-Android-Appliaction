package chewin.app.com;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>
{

    private final Activity context; //Create an  instance of Activity
    private final String[] web;     //Crate an array web
    private final Bitmap[] imageId; //Crate an array imageId
    private final String[] rank1;   //Crate an array rank1
    private final String[] textrank; //Crate an array textrank

    public CustomList(Activity context, String[] web, Bitmap[] imageId, String[] rank1, String[] textrank) { //Parameterize constructor
        super(context, R.layout.simplerow, web);
        this.context = context; //Assign appropriate value
        this.web = web;
        this.imageId = imageId;
        this.rank1 = rank1;
        this.textrank = textrank; //Assign appropriate value

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.simplerow, null, true);  //Set view value
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);  //Define text for title
        TextView userrank = (TextView) rowView.findViewById(R.id.rat);  //Define text for rank
        TextView txtuserrank = (TextView) rowView.findViewById(R.id.ratxt);  //Define text for userrank

        txtTitle.setText(web[position]);    //Set title
        userrank.setText(rank1[position]); //Set rank value
        txtuserrank.setText(textrank[position]);//Set userrank value
        return rowView;
    }
}