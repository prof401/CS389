package edu.carroll.gscheetz.cccalendar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import android.util.JsonReader;
import android.util.Log;

public class CCNetwork {
	private static final String CALENDAR_URL = "http://www.carroll.edu/jScripts/fullcalendar/eventCal.php?start=1420070400&end=1609459199";

	public List<Event> getEventList() {
		Log.d(this.getClass().getSimpleName(), ">>getEventList");

		List<Event> returnList = new java.util.ArrayList<Event>();
		DefaultHttpClient httpclient = new DefaultHttpClient(
				new BasicHttpParams());
		HttpPost httppost = new HttpPost(CALENDAR_URL);
		// Depends on your web service
		httppost.setHeader("Content-type", "application/json");

		InputStream inputStream = null;
		Log.d(this.getClass().getName(), "##getJsonCalendar.start of try");
		try {
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			inputStream = entity.getContent();
			returnList = readJsonStream(inputStream);

		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(),
					"##getJsonCalendar-->exception thrown", e);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception squish) {
			}
		}
		Log.d(this.getClass().getName(), "##getJsonCalendar.returnList size: "
				+ returnList.size());

		Collections.sort(returnList, new Comparator<Event>() {

			@Override
			public int compare(Event i1, Event i2) {
				return (i1.getStart().compareTo(i2.getStart()));
			}

		});

		return returnList;
	}

	private boolean readStringBoolean(JsonReader reader) throws IOException {
		boolean returnValue = false;

		String booleanString = reader.nextString();
		returnValue = Boolean.parseBoolean(booleanString);
		return returnValue;
	}

	private List<Event> readJsonStream(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		try {
			return readMessagesArray(reader);
		} finally {
			reader.close();
		}
	}

	private List<Event> readMessagesArray(JsonReader reader) throws IOException {
		List<Event> messages = new ArrayList<Event>();

		reader.beginArray();
		while (reader.hasNext()) {
			messages.add(readMessage(reader));
		}
		reader.endArray();
		return messages;
	}

	private Event readMessage(JsonReader reader) throws IOException {
		long id = -1;
		String title = null;
		String start = null;
		String end = null;
		boolean allDay = false;

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("id")) {
				id = reader.nextLong();
			} else if (name.equals("title")) {
				title = reader.nextString();
			} else if (name.equals("start")) {
				start = reader.nextString();
			} else if (name.equals("end")) {
				end = reader.nextString();
			} else if (name.equals("allDay")) {
				allDay = readStringBoolean(reader);
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return new Event(id, title, start, end, allDay);
	}
}
