package edu.carroll.gscheetz.cccalendar;

import edu.carroll.carrollcollegecalendar.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class CalendarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		ListView eventView = (ListView) findViewById(R.id.event_list);
		EventAdapter eventAdapter = new EventAdapter();
		eventView.setAdapter(eventAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar, menu);
		return true;
	}

}
