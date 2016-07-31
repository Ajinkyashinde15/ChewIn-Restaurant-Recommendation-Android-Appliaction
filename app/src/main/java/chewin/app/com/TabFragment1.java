package chewin.app.com;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TabFragment1 extends Fragment {

    private SeekBar distance, noOfRestaurants;
    private TextView text1, text2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_1, container, false);

        text1 = (TextView) v.findViewById(R.id.distancetv1);
        distance = (SeekBar) v.findViewById(R.id.seekBar);

        distance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress1, boolean fromUser)
            {
                Log.d("HM", progress1 + ": value");
                // Toast.makeText(getContext(), "Seekbar Progress: " + progress1, Toast.LENGTH_SHORT).show();
                text1.setText(String.valueOf(progress1));
            }
        });

        text2 = (TextView) v.findViewById(R.id.noofrestaurant);
        noOfRestaurants = (SeekBar) v.findViewById(R.id.seekBar2);

        noOfRestaurants.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress2, boolean fromUser)
            {
                // Toast.makeText(getContext(), "Seekbar Progress: " + progress2, Toast.LENGTH_SHORT).show();
                text2.setText(String.valueOf(progress2));
            }
        });

        return v;
    }

}
