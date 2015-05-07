package edu.carroll.gscheetz.cccalendar;

import java.util.List;

import edu.carroll.carrollcollegecalendar.R;

import android.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter {

	private List<Event> events;

	public void setEvents(List<Event> events) {
		Log.d(this.getClass().getSimpleName(), ">>setEvents");
		this.events = events;
		// this.notifyDataSetChanged();
		Log.d(this.getClass().getSimpleName(), "<<setEvents");
	}

	@Override
	public int getCount() {
		Log.d(this.getClass().getSimpleName(), ">>getCount");
		if (events == null)
			return 0;
		return events.size();
	}

	@Override
	public Event getItem(int location) {
		return events.get(location);
	}

	@Override
	public long getItemId(int location) {
		return events.get(location).getId();
	}

	@Override
	public View getView(final int index, View view, final ViewGroup parent) {
		Log.d(this.getClass().getSimpleName(), ">>getView " + index);

		final Event event = this.getItem(index);

		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.event_list_item, parent, false);
			view.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					Log.d(this.getClass().getSimpleName(), "Long Click: " + event.getId() + " index " + index);

					WebView details = new WebView(parent.getContext());
					details.loadUrl("http://www.carroll.edu/jScripts/fullcalendar/eventCalDetail.php?eventID="
							+ event.getId());

					AlertDialog.Builder ab = new AlertDialog.Builder(parent
							.getContext());
					ab.setView(details);
					ab.setCancelable(true);
					ab.show();

					return false;
				}
			});
		}

		TextView titleView = (TextView) view.findViewById(R.id.title_view);
		titleView.setText(event.getTitle());

		TextView startView = (TextView) view.findViewById(R.id.start_view);
		startView.setText(event.getStartString());

		TextView endView = (TextView) view.findViewById(R.id.end_view);
		endView.setText(event.getEndString());

		return view;
	}

}
