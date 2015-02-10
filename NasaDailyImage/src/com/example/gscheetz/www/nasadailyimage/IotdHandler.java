package com.example.gscheetz.www.nasadailyimage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class IotdHandler extends DefaultHandler {
	private String url = "http://www.nasa.gov/rss/image_of_the_day.rss";
	private boolean inTitle = false;
	private boolean inDescription = false;
	private boolean inItem = false;
	private boolean inDate = false;
	private Bitmap image = null;
	private String title = null;
	private StringBuffer description = new StringBuffer();
	private Boolean descriptionDone = false;
	private String date = null;     

	@Override	
	public void startElement(String uri, String localName, String qName, Attributes attributes)  throws SAXException { 
		if (localName.startsWith("item")) { inItem = true; }    
		else if (inItem) { 
			if (localName.equals("title")) { inTitle = true; } 
			else { inTitle = false; }
		
			if (localName.equals("description")) { inDescription = true; } 
			else { inDescription = false; }
		
			if (localName.equals("pubDate")) { inDate = true; } 
			else { inDate = false; }   
			
			if (localName.equals("enclosure")&& image ==  null ){
				int length = attributes.getLength();
				 // process each attribute to find url
				for (int i=0; i<length; i++) {
					if (attributes.getQName(i) == "url") {
						image = getBitmap(attributes.getValue(i)); 
					}
				}
			}    
		}		
	} // end startelement

    /** 
     * This will be called when the tags of the XML end.
     **/
	@Override	
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
		if (localName.equals("description") && inDescription) { descriptionDone = true; } 
		if (localName.startsWith("item")) { inItem = false; }
    }
  
	// this will be called for a tag's data before endElement is called
	public void characters(char ch[], int start, int length) {   
		String chars = new String(ch).substring(start, start + length); 
		if (inTitle && title == null) {  title = chars; }   
		if (inDescription && !descriptionDone) { description.append(chars); }   
		if (inDate && date == null) {  date = chars; }
	}


	public void processFeed() {   
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			reader.setContentHandler(this);
			InputStream inputStream = new URL(url).openStream(); 
			reader.parse(new InputSource(inputStream));
		} catch (Exception e) {	e.printStackTrace();}
	}
	
	private Bitmap getBitmap(String url) {   
		try { 
			HttpURLConnection connection = 
					(HttpURLConnection)new URL(url).openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(input);
			input.close();
			return bitmap;   
		} catch (IOException ioe) { return null; }
	}
	

	public Bitmap getImage() { return image; }

	public String getTitle() { 	return title;  }

	public StringBuffer getDescription() { return description; }

	public String getDate() { return date; }
}
