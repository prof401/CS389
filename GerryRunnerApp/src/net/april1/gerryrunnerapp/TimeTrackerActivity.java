package net.april1.gerryrunnerapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class TimeTrackerActivity extends Activity {
	TimeTrackerAdapter timeTrackerAdapter;
	public static final int TIME_ENTRY_REQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_tracker);

		ListView listView = (ListView) findViewById(R.id.times_list);
		timeTrackerAdapter = new TimeTrackerAdapter();
		listView.setAdapter(timeTrackerAdapter);
		
		TimeTrackerOpenHelper openHelper = new TimeTrackerOpenHelper(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_list_menu, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Log.d(this.getClass().getName(), ">>onMenuItemSelected");
		if (item.getItemId() == R.id.add_time_menu_item) {
			Intent intent =  new Intent(this, AddTimeActivity.class);
			startActivityForResult(intent, TIME_ENTRY_REQUEST_CODE);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (requestCode ==  TIME_ENTRY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String notes =  data.getStringExtra("notes");
				String time = data.getStringExtra("time");
				
				timeTrackerAdapter.addTimeRecord(new TimeRecord(time, notes));
				
				timeTrackerAdapter.notifyDataSetChanged();
			}
		}
	}

}
