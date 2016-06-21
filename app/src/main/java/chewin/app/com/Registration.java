package chewin.app.com;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Registration extends Activity {

   private DatePicker datePicker;
   private Calendar calendar;
   private Button datebutton,submit;
   private int year, month, day;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.registration);

      datebutton = (Button) findViewById(R.id.dob);
      submit = (Button) findViewById(R.id.submitreg);
      calendar = Calendar.getInstance();
      year = calendar.get(Calendar.YEAR);
      month = calendar.get(Calendar.MONTH);
      day = calendar.get(Calendar.DAY_OF_MONTH);


      datebutton.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			 showDate(year, month+1, day);
		}
      });

      submit.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
		      Intent intent = new Intent(Registration.this, WelcomeScreen.class);
		      startActivity(intent);
			}
      	});

   }

   @SuppressWarnings("deprecation")
   public void setDate(View view) {
      showDialog(999);
      Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
      .show();
   }

   @Override
   protected Dialog onCreateDialog(int id) {
      // TODO Auto-generated method stub
      if (id == 999) {
         return new DatePickerDialog(this, myDateListener, year, month, day);
      }
      return null;
   }

   private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
         // TODO Auto-generated method stub
         // arg1 = year
         // arg2 = month
         // arg3 = day
         showDate(arg1, arg2+1, arg3);
      }
   };

   private void showDate(int year, int month, int day) {
      datebutton.setText(new StringBuilder().append(day).append("/")
      .append(month).append("/").append(year));
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
     // getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }
}
