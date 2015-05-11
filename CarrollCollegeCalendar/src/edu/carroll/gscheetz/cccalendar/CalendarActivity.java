package edu.carroll.gscheetz.cccalendar;

import java.util.List;
import edu.carroll.carrollcollegecalendar.R;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class CalendarActivity extends Activity {

	private EventAdapter eventAdapter;
	private List<Event> events;
	private final String TAG = this.getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		ListView eventView = (ListView) findViewById(R.id.event_list);
		eventAdapter = new EventAdapter();
		eventView.setAdapter(eventAdapter);
		loadFields();
	}

	@SuppressLint("HandlerLeak")
	final Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) { // server returned null, try again
				loadFields();
			} else if (msg.what == 1) { // error in json
				// do something to treat it
			} else if (msg.what == 2) { // ready to roll the list
				eventAdapter.setEvents(events);
				eventAdapter.notifyDataSetChanged();
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar, menu);
		return true;
	}

	private void loadFields() {
		Log.d(TAG, ">>populateEventList");

		final ProgressDialog dialog = ProgressDialog.show(this, getResources()
				.getString(R.string.loading),
				getResources().getString(R.string.loadingtext));

		final CCNetwork network = new CCNetwork();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(this.getClass().getName(), ">>run");
				try {
					dialog.show();
					Log.d(TAG, "##about to populate eventList");
					events = network.getEventList();
					handler.sendEmptyMessage(2);
					Log.d(this.getClass().getName(), "##eventList populated");
					dialog.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				} // end try-catch
			} // end run function
		}); // end new runnable, close new thread function
		Log.d(this.getClass().getName(), "##start thread");
		thread.start();
		Log.d(this.getClass().getName(), "##after start thread");
	}

}
