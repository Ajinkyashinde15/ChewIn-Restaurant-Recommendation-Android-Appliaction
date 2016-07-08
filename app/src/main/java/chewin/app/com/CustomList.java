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

    private final Activity context;
    private final String[] web;
    private final Bitmap[] imageId;
    private final String[] rank1;
    private final String[] textrank;

    public CustomList(Activity context, String[] web, Bitmap[] imageId, String[] rank1, String[] textrank) {
        super(context, R.layout.simplerow, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.rank1 = rank1;
        this.textrank = textrank;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.simplerow, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img1);
        TextView userrank = (TextView) rowView.findViewById(R.id.rat);
        TextView txtuserrank = (TextView) rowView.findViewById(R.id.ratxt);

        txtTitle.setText(web[position]);
        imageView.setImageBitmap(imageId[position]);
        userrank.setText(rank1[position]);
        txtuserrank.setText(textrank[position]);
        return rowView;
    }
}