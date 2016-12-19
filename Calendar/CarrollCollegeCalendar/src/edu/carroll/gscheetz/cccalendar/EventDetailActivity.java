package edu.carroll.gscheetz.cccalendar;

import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import edu.carroll.carrollcollegecalendar.R;

public class EventDetailActivity extends Activity implements OnClickListener {

	final String TAG = this.getClass().getSimpleName();
	final static int RQS_1 = 1;

	Date startDate = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
		WebView eventDetail = (WebView) findViewById(R.id.event_detail_view);
		Event event = (Event) getIntent().getExtras().get("event");
		long eventId = 0;
		if (event != null) {
			eventId = event.getId();
		} else {
			eventId = getIntent().getExtras().getLong("eventId");
			NotificationManager nm = (NotificationManager) 
		            getSystemService(NOTIFICATION_SERVICE);
		 
		        //---cancel the notification---
		        nm.cancel(1);
		    Button button = (Button) findViewById(R.id.event_detail_alarm_button);
		    button.setVisibility(Button.INVISIBLE);
		    button.setVisibility(Button.GONE);
		}
		Log.d(TAG, "##Getting details for eventID=" + eventId);

		eventDetail
				.loadUrl("http://www.carroll.edu/jScripts/fullcalendar/eventCalDetail.php?eventID="
						+ eventId);

		Button okButton = (Button) findViewById(R.id.event_detail_ok_button);
		okButton.setOnClickListener(this);
		Button alarmButton = (Button) findViewById(R.id.event_detail_alarm_button);
		alarmButton.setTag(event);
		alarmButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (((Button) view).getId()) {
		case R.id.event_detail_ok_button:
			finish();
			break;
		case R.id.event_detail_alarm_button:
			Event event = (Event) view.getTag();
			Log.d(TAG, "Alarm Set for " + event.getStart());
			setAlarm(event);
			break;
		}
	}

	private void setAlarm(Event event) {
		Log.d(TAG, "Alarm is set@ " + event.getStart());

		Date targetDate = new Date((new Date()).getTime() + (1000 * 15 * 1));

		Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
		intent.putExtra("event", event);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				getBaseContext(), RQS_1, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, targetDate.getTime(),
				pendingIntent);
	}
}
