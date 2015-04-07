package net.april1.mystic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameWonActivity extends Activity implements OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_won);

		Button ok = (Button) findViewById(R.id.okbutton);
		ok.setOnClickListener(this);
	}

	public void onClick(View v) {
		finish();
	}

}
