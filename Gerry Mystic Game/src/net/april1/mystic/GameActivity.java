package net.april1.mystic;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class GameActivity extends Activity {

	private static final int BOARD_SIZE_CODE = 1;
	private BoardSplitView boardView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(this.getClass().getName(), ">>onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		boardView = (BoardSplitView) findViewById(R.id.mystic_grid);
		boardView.setRows(5);
		boardView.setColumns(5);
		boardView.start();
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
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Log.d(this.getClass().getName(), ">>onMenuItemSelected");
		if (item.getItemId() == R.id.action_settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivityForResult(intent, BOARD_SIZE_CODE);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == BOARD_SIZE_CODE) {
			if (resultCode == RESULT_OK) {
				int size = data.getIntExtra("size", 4);
				boardView.setRows(size);
				boardView.setRows(size);
				boardView.start();
			}
		}
	}
}
