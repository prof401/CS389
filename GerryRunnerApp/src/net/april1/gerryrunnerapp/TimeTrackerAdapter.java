package net.april1.gerryrunnerapp;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TimeTrackerAdapter extends BaseAdapter {

	private List<TimeRecord> times = new ArrayList<TimeRecord>();

	public TimeTrackerAdapter() {
		times.add(new TimeRecord("38:23", "Feeling good!"));
		times.add(new TimeRecord("49:01", "Tired. Needed more caffeine"));
		times.add(new TimeRecord("26:21", "I’m rocking it!"));
		times.add(new TimeRecord("29:42",
				"Lost some time on the hills, but pretty good."));
	}

	@Override
	public int getCount() {
		return times.size();
	}

	@Override
	public TimeRecord getItem(int index) {
		return times.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.time_list_item, parent, false);
		}

		TimeRecord time = this.getItem(index);

		TextView timeTextView = (TextView) view.findViewById(R.id.time_view);
		timeTextView.setText(time.getTime());

		TextView notesTextView = (TextView) view.findViewById(R.id.notes_view);
		notesTextView.setText(time.getNotes());

		return view;
	}

}
