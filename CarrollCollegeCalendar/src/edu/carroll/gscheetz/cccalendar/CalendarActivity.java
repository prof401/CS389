package edu.carroll.gscheetz.cccalendar;

import edu.carroll.carrollcollegecalendar.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CalendarActivity extends Activity {
//http://www.carroll.edu/jScripts/fullcalendar/eventCal.php?start=1427608800&end=1430632800
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendar, menu);
        return true;
    }
    
}
