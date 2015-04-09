package net.april1.mystic;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class GameActivity extends Activity implements OnClickListener {
	private static final int BOARD_SIZE_CODE = 1;
	private static final int SELECT_PHOTO = 2;
	private BoardView boardView;
	private TextView clickCounter;
	private int clickCount;
	private MediaPlayer badMove;
	private MediaPlayer goodMove;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(this.getClass().getName(), ">>onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		badMove = MediaPlayer.create(getApplicationContext(), R.raw.bonk);
		badMove.setLooping(false);

		goodMove = MediaPlayer.create(getApplicationContext(), R.raw.slide);
		goodMove.setLooping(false);

		boardView = (BoardView) findViewById(R.id.mystic_grid);
		clickCounter = (TextView) findViewById(R.id.click_count);

		boardView.start();
		initClicks();

	}

	private void initClicks() {
		clickCount = 0;
		clickCounter.setText("0 Clicks");
	}

	protected void onStart() {
		super.onStart();
		Log.d(this.getClass().getName(), ">>onStart");
	}

	private void updateClicks() {
		if (++clickCount == 1)
			clickCounter.setText("1 Click");
		else
			clickCounter.setText(clickCount + " Clicks");
	}

	protected void onRestart() {
		super.onRestart();
		Log.d(this.getClass().getName(), ">>onRestart");
	}

	protected void onResume() {
		super.onResume();
		Log.d(this.getClass().getName(), ">>onResume");
	}

	protected void onPause() {
		super.onPause();
		Log.d(this.getClass().getName(), ">>onPause");
	}

	protected void onStop() {
		super.onStop();
		Log.d(this.getClass().getName(), ">>onStop");
	}

	protected void onDestroy() {
		super.onDestroy();
		Log.d(this.getClass().getName(), ">>onDestroy");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gamemenu, menu);
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Log.d(this.getClass().getName(), ">>onMenuItemSelected");
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivityForResult(intent, BOARD_SIZE_CODE);
			break;
		case R.id.action_new_game:
			boardView.removeAllViews();
			initClicks();
			boardView.start();
			break;
		case R.id.action_load_image:
			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
			photoPickerIntent.setType("image/*");
			startActivityForResult(photoPickerIntent, SELECT_PHOTO);
		default:
			// if no case, return false
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case SELECT_PHOTO: {
			//http://stackoverflow.com/questions/2507898/how-to-pick-an-image-from-gallery-sd-card-for-my-app
	        if(resultCode == RESULT_OK){  
	            Uri selectedImage = imageReturnedIntent.getData();
	            InputStream imageStream = getContentResolver().openInputStream(selectedImage);
	            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
	        }

			break;
		}
		case BOARD_SIZE_CODE: {
			if (resultCode == RESULT_OK) {
				int size = data.getIntExtra("size", 4);
				boardView.setRows(size);
				boardView.setColumns(size);
				boardView.removeAllViews();
				initClicks();
				boardView.start();
			}
			break;
		}
		}
	}

	private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
               || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }
	
	@Override
	public void onClick(View view) {
		if (view instanceof TileView) {
			boolean validClick = boardView.moveTile(view);
			if (validClick) {
				updateClicks();
				goodMove.start();
				if (boardView.isWon()) {
					Intent gameWon = new Intent(this, GameWonActivity.class);
					startActivity(gameWon);
				}
			} else {
				badMove.start();
			}
		}
	}
}
