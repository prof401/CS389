package net.april1.mystic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(this.getClass().getName(), ">>onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
	}

}
