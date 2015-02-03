package com.scheetz.www.fontandsound;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FontAndSound extends Activity implements OnClickListener, OnCompletionListener {
	MediaPlayer player;
	Button flyby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_and_sound);
        Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/Airplanes in the Night Sky.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Haiku\'s Script version 08.ttf");
        TextView customText1 = (TextView)findViewById(R.id.text1);
        TextView customText2 = (TextView)findViewById(R.id.text2);

        customText1.setTypeface(font1);
        customText1.setTextSize(40.f);
        customText1.setText(R.string.hello1);
        
        customText2.setTypeface(font2);
        customText2.setTextSize(30.f);
        customText2.setText(R.string.hello2);
        
        // connect the button to an object
        flyby = (Button)findViewById(R.id.button);
        flyby.setOnClickListener(this);
        
        // instantiate a MediaPlayer instance
		player = MediaPlayer.create(getApplicationContext(), R.raw.plane);
		player.setLooping(false);
		player.setOnCompletionListener(this);
    }

    // when the button is clicked we want to hear a sound
	public void onClick(View v) {
		
		// play or pause the sound, as appropriate
		if(player.isPlaying()) {
			player.pause();
			
			// reset button text
			flyby.setText(R.string.play_message);
		} else {
			flyby.setText(R.string.pause_message);
			player.start();
		}
	}
	
	// reset button text when the sound is done playing
	public void onCompletion(MediaPlayer mp) {
		flyby.setText(R.string.play_message);
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.font_and_sound, menu);
        return true;
    }
    
}
