package de.htwg.memory.activity;

import de.htwg.memory.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * this class is the start activity with three buttons (play, settings, about)
 * 
 * @author Simon Schneeberger <sischnee@gmail.com>
 * @version V1.0 18-01-2014
 */
public class StartActivity extends Activity {
	
	private TextView version;
	private String size = "4x4";
	private String name = "Mustermann";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        version = (TextView)findViewById(R.id.versionText);
        version.setText("Version 1.0");
        
        Button game = (Button)findViewById(R.id.spielen);   
        game.setOnClickListener(new OnClickListener() {
			
        	public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this, GameActivity.class);
				intent.putExtra("size", size);
				intent.putExtra("name", name);
				startActivity(intent);		
			}
		});
        
        Button einstellung = (Button)findViewById(R.id.einstellung);
        einstellung.setOnClickListener(new OnClickListener() {
			
        	public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this, SettingActivity.class);
				intent.putExtra("size", size);
				intent.putExtra("name", name);
				startActivityForResult(intent, 10); 		
			}
		});
        
        Button impressum = (Button)findViewById(R.id.impressum);
        impressum.setOnClickListener(new OnClickListener() {
			
        	public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this, ImpressActivity.class);
				startActivity(intent);		
			}
		});

    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	  super.onActivityResult(requestCode, resultCode, data);
    	  if(resultCode==RESULT_OK && requestCode==10) {
    		  name = data.getStringExtra("name");
    		  size = data.getStringExtra("size");
    	  }
    }
}