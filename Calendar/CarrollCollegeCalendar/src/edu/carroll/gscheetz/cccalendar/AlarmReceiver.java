package edu.carroll.gscheetz.cccalendar;

import edu.carroll.carrollcollegecalendar.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	final String TAG = this.getClass().getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Vibrator vib = (Vibrator) context
				.getSystemService(context.VIBRATOR_SERVICE); // for Vibration
		vib.vibrate(2000);

		Event event = (Event) intent.getExtras().get("event");

		NotificationManager mNM = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(R.drawable.ic_launcher,
				event.getTitle() + " Reminder", System.currentTimeMillis());
		// The PendingIntent to launch our activity if the user selects this
		// notification
		Intent newIntent = new Intent(context, EventDetailActivity.class);
		newIntent.putExtra("event", event);
		newIntent.putExtra("eventId", event.getId());
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				newIntent, 0);
		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(context,
				context.getText(R.string.alarm_service_label),
				event.getTitle(), contentIntent);
		// Send the notification.
		// We use a layout id because it is a unique number. We use it later to
		// cancel.
		mNM.notify(R.string.alarm_service_label, notification);
		// mNotificationManager.notify(1, mBuilder.build());
	}
}
