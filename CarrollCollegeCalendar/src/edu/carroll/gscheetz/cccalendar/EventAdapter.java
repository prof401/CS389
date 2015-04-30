package edu.carroll.gscheetz.cccalendar;

import java.util.List;

import edu.carroll.carrollcollegecalendar.R;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	public View getView(int index, View view, ViewGroup parent) {
		Log.d(this.getClass().getSimpleName(), ">>getView");
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.event_list_item, parent, false);
		}

		Event event = this.getItem(index);

		TextView timeTextView = (TextView) view.findViewById(R.id.title_view);
		timeTextView.setText(event.getTitle());

		TextView notesTextView = (TextView) view.findViewById(R.id.start_view);
		notesTextView.setText(event.getStart());

		return view;
	}

}
