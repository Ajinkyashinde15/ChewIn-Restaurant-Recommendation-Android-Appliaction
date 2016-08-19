package chewin.app.com;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

public class GifView extends View{

	private InputStream gifInputStream; //Create an instance of InputStream
	private Movie gifMovie;	//Crate Movie object
	private int movieWidth, movieHeight;
	private long movieDuration;
	private long movieStart;
	
	public GifView(Context context) {
		super(context);
		init(context);
	}

	
	public GifView(Context context, AttributeSet attrs) { //Define constructor
		super(context, attrs);
		init(context);
	}
	
	public GifView(Context context, AttributeSet attrs, int defStyleAttr) {  //Define constructor
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		setFocusable(true);
		gifInputStream = context.getResources().openRawResource(R.raw.foodie);
		
		gifMovie = Movie.decodeStream(gifInputStream);  //Decode stream value to gifMovie variable
		movieWidth = gifMovie.width();    //Set width
		movieHeight = gifMovie.height();
		movieDuration = gifMovie.duration();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(movieWidth, movieHeight);
	}
	
	public int getMovieWidth() {
		return movieWidth;
	}
	
	public int getMovieHeight() {
		return movieHeight;
	}
	
	public long getMovieDuration() {
		return movieDuration;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		long now = SystemClock.uptimeMillis();
		
		if(movieStart == 0) {
			movieStart = now;
		}
		
		if(gifMovie != null) {
			
			int dur = gifMovie.duration();
			if(dur == 0) {
				dur = 1000;
			}
			
			int relTime = (int)((now - movieStart) % dur);  //Calculate splashscreen time
			
			gifMovie.setTime(relTime);	 //Set splashscreen time
			
			gifMovie.draw(canvas, 0, 0);
			invalidate();
		}
	}
}
