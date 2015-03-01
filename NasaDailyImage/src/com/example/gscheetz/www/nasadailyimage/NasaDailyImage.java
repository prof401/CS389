package com.example.gscheetz.www.nasadailyimage;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NasaDailyImage extends Activity {
	private Handler handler; // for receiving parsed information
	private IotdHandler iotdhandler; // for parsing xml form NASA
	Bitmap image; // image from NASA

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
		final ProgressDialog dialog = ProgressDialog.show(this, getResources()
				.getString(R.string.loading),
				getResources().getString(R.string.loadingtext));

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(this.getClass().getName(), ">>run");
				try {
					if (iotdhandler == null) {
						iotdhandler = new IotdHandler();
					}
					iotdhandler.processFeed();
					image = iotdhandler.getImage();

					handler.post(new Runnable() {
						public void run() {
							resetDisplay(iotdhandler.getTitle(),
									iotdhandler.getDate(),
									iotdhandler.getImage(),
									iotdhandler.getDescription());

							dialog.dismiss();
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

	public void onSetWallpaper(View view) {
		Thread th = new Thread() {
			public void run() {
				WallpaperManager wallpaperManager = WallpaperManager
						.getInstance(NasaDailyImage.this);
				try {
					wallpaperManager.setBitmap(image);
					handler.post(
							new Runnable () {
							 public void run() {
							 Toast.makeText(NasaDailyImage.this,getResources().getString(R.string.wallpaperset),
							 Toast.LENGTH_SHORT).show();}});
				} catch (Exception e) {
					e.printStackTrace();
					handler.post(
							new Runnable () {
							 public void run() {
							 Toast.makeText(NasaDailyImage.this,
									 getResources().getString(R.string.wallpaperseterror),
							 Toast.LENGTH_SHORT).show();
							}});
				}
			}
		};
		th.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nasa_daily_image, menu);
		return true;
	}

}
