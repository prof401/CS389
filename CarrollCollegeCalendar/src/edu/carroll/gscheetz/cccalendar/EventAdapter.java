package edu.carroll.gscheetz.cccalendar;

import java.util.List;

import edu.carroll.carrollcollegecalendar.R;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter {

	private List<Event> events;
	private final String TAG = this.getClass().getSimpleName();

	public void setEvents(List<Event> events) {
		Log.d(TAG, ">>setEvents");
		this.events = events;
		// this.notifyDataSetChanged();
		Log.d(TAG, "<<setEvents");
	}

	@Override
	public int getCount() {
		Log.d(TAG, ">>getCount");
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
		final Event event = this.getItem(index);

		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.event_list_item, parent, false);
		}

		view.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Context currentContext = parent.getContext();
				Log.d(TAG, "Long Click: " + event.getId() + " index " + index);

				Intent intent = new Intent(currentContext,
						EventDetailActivity.class);
				intent.putExtra("event", event);
				currentContext.startActivity(intent);
				return true;
			}
		});

		TextView titleView = (TextView) view.findViewById(R.id.title_view);
		titleView.setText(event.getTitle());

		TextView startView = (TextView) view.findViewById(R.id.start_view);
		startView.setText(event.getStartString());

		TextView endView = (TextView) view.findViewById(R.id.end_view);
		endView.setText(event.getEndString());

		return view;
	}

}
