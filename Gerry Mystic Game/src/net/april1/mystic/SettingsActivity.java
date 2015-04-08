package net.april1.mystic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

public class SettingsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(this.getClass().getName(), ">>onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);
		NumberPicker picker = (NumberPicker) findViewById(R.id.size);
		picker.setMaxValue(5);
		picker.setMinValue(3);
	}

	public void onCancel(View view) {
		finish();
	}

	public void onSave(View view) {
		Intent intent = getIntent();

		NumberPicker picker = (NumberPicker) findViewById(R.id.size);
		intent.putExtra("size", picker.getValue());

		this.setResult(RESULT_OK, intent);
		finish();
	}

}
