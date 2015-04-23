package edu.carroll.gscheetz.cccalendar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

public class CCCalendar {

	List<Event> eventList;

	public CCCalendar() {
		populateList();
	}

	private void populateList() {
		String jsonList = getJsonCalendar();
		eventList = parseJsonCalendar(jsonList);

		// TODO Auto-generated method stub

	}

	private List<Event> parseJsonCalendar(String jsonList) {
		List<Event> returnList = new java.util.ArrayList<Event>();
		
		
		
		
		return returnList;
	}

	private String getJsonCalendar() {
		DefaultHttpClient   httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpPost httppost = new HttpPost("http://www.carroll.edu/jScripts/fullcalendar/eventCal.php?start=1427608800&end=1430632800");
		// Depends on your web service
		httppost.setHeader("Content-type", "application/json");

		InputStream inputStream = null;
		String result = null;
		try {
		    HttpResponse response = httpclient.execute(httppost);           
		    HttpEntity entity = response.getEntity();

		    inputStream = entity.getContent();
		    // json is UTF-8 by default
		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
		    StringBuilder sb = new StringBuilder();

		    String line = null;
		    while ((line = reader.readLine()) != null)
		    {
		        sb.append(line + "\n");
		    }
		    result = sb.toString();
		} catch (Exception e) { 
		    // Oops
		}
		finally {
		    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
		}		return result;
	}
}
