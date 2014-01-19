package de.htwg.memory.activity;

import android.app.Activity;
import android.os.Bundle;
import de.htwg.memory.R;

/**
 * display the impressum of the game
 * 
 * @author Simon Schneeberger <sischnee@gmail.com>
 * @version V1.0 18-01-2014
 */
public class ImpressActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.impressum);
    }

}
