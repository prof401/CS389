package edu.carroll.gscheetz.cccalendar;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.util.Log;

public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final SimpleDateFormat INPUT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss", Locale.US);
	private static final SimpleDateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat(
			"MMM dd, yyyy hh:mm aa", Locale.US);
	private long id;
	private String title;
	private Date start;
	private Date end;
	private boolean allDay;

	public Event(long id, String title, String start, String end, boolean allDay) {
		this.id = id;
		this.title = title;
		this.start = toDate(start);
		this.end = toDate(end);
		this.allDay = allDay;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStart() {

		return start;
	}

	public String getStartString() {
		return OUTPUT_DATE_FORMAT.format(start);
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public String getEndString() {
		return OUTPUT_DATE_FORMAT.format(end);
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public boolean getAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	private Date toDate(final String iso8601string) {
		Date date;
		try {
			date = INPUT_DATE_FORMAT.parse(iso8601string);
		} catch (IndexOutOfBoundsException e) {
			Log.e(this.getClass().getSimpleName(),
					"IndexOutOfBounds parsing date: " + iso8601string, e);
			return (new Date());
		} catch (ParseException e) {
			Log.e(this.getClass().getSimpleName(),
					"Exception trying to parse date: " + iso8601string, e);
			return (new Date());
		}
		return date;
	}

	@SuppressWarnings("unused")
	private Calendar toCalendar(final String iso8601string) {

		Calendar calendar = GregorianCalendar.getInstance();
		Date date = toDate(iso8601string);
		calendar.setTime(date);
		return calendar;
	}
}
