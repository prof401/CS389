package edu.carroll.gscheetz.cccalendar;

import java.util.List;

import android.util.Log;

public class CCCalendar {

	private List<Event> eventList;
	private CCNetwork network;
	private final String TAG = this.getClass().getSimpleName();


	public CCCalendar() {
		Log.d(this.getClass().getName(), "++CCalendar");
		populateDatabase();
	}

	private void populateDatabase() {
		Log.d(TAG, ">>populateDatabase");
		populateEventList();
	}

	private void populateEventList() {
		Log.d(TAG, ">>populateEventList");

		// final ProgressDialog dialog = ProgressDialog.show(this,
		// getResources()
		// .getString(R.string.loading),
		// getResources().getString(R.string.loadingtext));
		//
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(this.getClass().getName(), ">>run");
				try {
					if (network == null) {
						network = new CCNetwork();
					}
					eventList = network.getEventList();
					Log.d(this.getClass().getName(), "##eventList populated");
				} catch (Exception e) {
					e.printStackTrace();
				} // end try-catch
			} // end run function
		}); // end new runnable, close new thread function
		Log.d(this.getClass().getName(), "##start thread");
		thread.start();
		Log.d(this.getClass().getName(), "##after start thread");
	}

	public List<Event> getEventList() {
		return eventList;
	}

}
