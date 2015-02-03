package com.scheetz.www.fontandsound;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.TextView;

public class FontAndSound extends Activity {

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
        customText1.setText("Airplanes");
        
        customText2.setTypeface(font2);
        customText2.setTextSize(30.f);
        customText2.setText("Haiku");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.font_and_sound, menu);
        return true;
    }
    
}
