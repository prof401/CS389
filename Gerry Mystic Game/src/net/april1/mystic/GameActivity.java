package net.april1.mystic;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;

public class GameActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.d(this.getClass().getName(), ">>onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        
		GridView gridView = (GridView) findViewById(R.id.mystic_grid);
		gridView.setAdapter(new GameAdapter(this));
    }


    protected void onStart() {
    	super.onStart();
    	Log.d(this.getClass().getName(), ">>onStart");
	}

    protected void onRestart() {
    	super.onRestart();
    	Log.d(this.getClass().getName(), ">>onRestart");
	}

    protected void onResume() {
    	super.onResume();
    	Log.d(this.getClass().getName(), ">>onResume");
	}

    protected void onPause() {
    	super.onPause();
    	Log.d(this.getClass().getName(), ">>onPause");
	}

    protected void onStop() {
    	super.onStop();
    	Log.d(this.getClass().getName(), ">>onStop");
	}

    protected void onDestroy() {
    	super.onDestroy();
    	Log.d(this.getClass().getName(), ">>onDestroy");
	}
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }
    
}
