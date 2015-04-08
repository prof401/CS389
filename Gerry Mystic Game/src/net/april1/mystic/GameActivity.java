package net.april1.mystic;

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
	private BoardView boardView;
	private TextView clickCounter;
	private int clickCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(this.getClass().getName(), ">>onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		boardView = (BoardView) findViewById(R.id.mystic_grid);
		clickCounter = (TextView) findViewById(R.id.click_count);

		boardView.start();
		initClicks();

	}

	private void initClicks() {
		clickCount = 0;
		clickCounter.setText("0 Clicks");
	}

	private void updateClicks() {
		if (++clickCount == 1)
			clickCounter.setText("1 Click");
		else
			clickCounter.setText(clickCount + " Clicks");
	}

	protected void onStart() {
		super.onStart();
		Log.d(this.getClass().getName(), ">>onStart");
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
		default:
			// if no case, return false
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == BOARD_SIZE_CODE) {
			if (resultCode == RESULT_OK) {
				int size = data.getIntExtra("size", 4);
				boardView.setRows(size);
				boardView.setColumns(size);
				boardView.removeAllViews();
				initClicks();
				boardView.start();
			}
		}
	}

	@Override
	public void onClick(View view) {
		if (view instanceof TileView) {
			boolean validClick = boardView.moveTile(view);
			if (validClick) {
				updateClicks();
				if (boardView.isWon()) {
					Intent gameWon = new Intent(this, GameWonActivity.class);
					startActivity(gameWon);
				}
			}
		}
	}
}
