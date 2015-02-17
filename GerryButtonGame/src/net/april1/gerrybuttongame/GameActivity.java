package net.april1.gerrybuttongame;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.TextView;

public class GameActivity extends Activity implements OnClickListener {
	private int[] buttonArray = { R.id.button01, R.id.button02, R.id.button03,
			R.id.button04, R.id.button05, R.id.button06, R.id.button07,
			R.id.button08, R.id.button09, R.id.button10, R.id.button11,
			R.id.button12, R.id.button13, R.id.button14, R.id.button15,
			R.id.button16 };
	private int clickCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		setup();

		reset();
	}

	private void setup() {
		GridLayout grid = (GridLayout) findViewById(R.id.gridlayout);
		
		LayoutParams layout = new LayoutParams();
		layout.setGravity(Gravity.CENTER);
		layout.height = LayoutParams.WRAP_CONTENT;
		layout.width = LayoutParams.WRAP_CONTENT;
		grid.setLayoutParams(layout);
		
		grid.setColumnCount(4);
		grid.setOrientation(GridLayout.HORIZONTAL);
		
		for (int buttonId : buttonArray) {
			Button button = (Button) findViewById(buttonId);
			button.setOnClickListener(this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	private void reset() {

		for (int buttonId : buttonArray) {
			Button button = (Button) findViewById(buttonId);
			button.setBackgroundColor(Color.RED);
		}
		clickCount = 0;
	}

	@Override
	public void onClick(View view) {
		Button button = (Button) view;
		ColorDrawable buttonColor = (ColorDrawable) button.getBackground();

		int colorId = buttonColor.getColor();

		if (colorId == Color.RED) {
			button.setBackgroundColor(Color.GREEN);
		} else {
			if (colorId == Color.GREEN) {
				button.setBackgroundColor(Color.RED);
			}
		}

		((TextView) findViewById(R.id.clicknumber)).setText(Integer
				.toString(++clickCount));
	}
}
