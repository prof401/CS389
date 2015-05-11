package edu.carroll.gscheetz.cccalendar;

import java.util.Date;

import edu.carroll.carrollcollegecalendar.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class EventDetailActivity extends Activity implements OnClickListener {

	final String TAG = this.getClass().getSimpleName();
	final static int RQS_1 = 1;

	Date startDate = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
		WebView eventDetail = (WebView) findViewById(R.id.event_detail_view);
		long id = getIntent().getExtras().getLong("eventId");
		Event event = (Event) getIntent().getExtras().get("event");
		eventDetail
				.loadUrl("http://www.carroll.edu/jScripts/fullcalendar/eventCalDetail.php?eventID="
						+ event.getId());

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
			Log.d(this.getClass().getSimpleName(),
					"Alarm Set for " + event.getStart());
			setAlarm(event);
			break;
		}
	}

	private void setAlarm(Event event) {

		Date targetDate = event.getStart();

		Log.d(TAG, "Alarm is set@ " + targetDate.getTime());

		Date tempDate = new Date((new Date()).getTime() + (1000 * 60 * 1));
		Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
		intent.putExtra("eventTitle", event.getTitle());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				getBaseContext(), RQS_1, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, tempDate.getTime(),
				pendingIntent);
	}
}
