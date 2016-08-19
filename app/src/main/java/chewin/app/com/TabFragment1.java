package chewin.app.com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TabFragment1 extends Fragment {  //Class for display tab filter

    //Define and initalize widget
    private SeekBar distance, noOfRestaurants;
    private TextView text1, text2;
    private Button btSubmit1;
    private int textInt1, textInt2;
    String tdistance, tnoOfRestaurants;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_1, container, false);


        //Decalre and map widget with appropriate
        btSubmit1 = (Button) v.findViewById(R.id.btsubmittab1);
        text1 = (TextView) v.findViewById(R.id.displaydistance);
        text2 = (TextView) v.findViewById(R.id.displaynoresta);
        distance = (SeekBar) v.findViewById(R.id.seekBar);

        //Define On SeekBar ChangeListener for handle event
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
            //when seek bar Progress value change
            public void onProgressChanged(SeekBar seekBar, int progress1, boolean fromUser)
            {
                Log.d("HM", progress1 + ": value");

                tdistance=String.valueOf(progress1);
                text1.setText(String.valueOf(Integer.parseInt(tdistance)*100));
            }
        });

        //Decalre and map widget
        text2 = (TextView) v.findViewById(R.id.displaynoresta);
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
                // Set value for text2 text view
                tnoOfRestaurants=String.valueOf(progress2);
                text2.setText(tnoOfRestaurants);
            }
        });

        //Define method to handle event
       btSubmit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to WelcomeScreen class
                Intent intent = new Intent(getActivity(), WelcomeScreen.class);
                intent.putExtra("flag", "filterflag");
                intent.putExtra("distance", text1.getText());
                intent.putExtra("nrestauratnts",text2.getText());
                startActivity(intent);

            }
 });

        return v;
    }

}