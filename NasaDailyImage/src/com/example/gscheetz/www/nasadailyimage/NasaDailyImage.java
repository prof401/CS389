package com.example.gscheetz.www.nasadailyimage;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NasaDailyImage extends Activity {
	private Handler handler; // for receiving parsed information
	private IotdHandler iotdhandler; // for parsing xml form NASA

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(this.getClass().getName(), ">>onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nasa_daily_image);
		handler = new Handler();
		refreshFromFeed();
		Log.i(this.getClass().getName(), "<<onCreate");
	}

	public void onRefresh(View view) {
		refreshFromFeed();
	}
	
	private void refreshFromFeed() {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(this.getClass().getName(), ">>run");
				try {
					if (iotdhandler == null) {
						iotdhandler = new IotdHandler();
					}
					iotdhandler.processFeed();

					handler.post(new Runnable() {
						public void run() {
							resetDisplay(iotdhandler.getTitle(),
									iotdhandler.getDate(),
									iotdhandler.getImage(),
									iotdhandler.getDescription());

						}
					}); // end Runnable, closes handler post
				} catch (Exception e) {
					e.printStackTrace();
				} // end try-catch
			} // end run function
		}); // end new runnable, close new thread function
		Log.i(this.getClass().getName(), "##start thread");
		thread.start();
	} // end refreshFromFeed function

	private void resetDisplay(String title, String date, Bitmap image,
			StringBuffer description) {
		Log.i(this.getClass().getName(), ">>resetDisplay");
		TextView titleView = (TextView) findViewById(R.id.imageTitle);
		titleView.setText(title);

		TextView dataView = (TextView) findViewById(R.id.imageDate);
		dataView.setText(date);

		ImageView imageView = (ImageView) findViewById(R.id.imageDisplay);
		imageView.setImageBitmap(image);

		TextView descriptionView = (TextView) findViewById(R.id.imageDescription);
		descriptionView.setText(description);
		Log.i(this.getClass().getName(), "<<resetDisplay");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nasa_daily_image, menu);
		return true;
	}

}
