package net.april1.gerryrunnerapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class AddTimeActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_time);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu m) {
		super.onCreateOptionsMenu(m);
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.time_list_menu, m);
		return true;
	}
}
