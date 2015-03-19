package net.april1.gerryrunnerapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class TimeTrackerActivity extends Activity {
	TimeTrackerAdapter timeTrackerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_tracker);

		ListView listView = (ListView) findViewById(R.id.times_list);
		timeTrackerAdapter = new TimeTrackerAdapter();
		listView.setAdapter(timeTrackerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_tracker, menu);
		return true;
	}

}
