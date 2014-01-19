package de.htwg.memory.activity;

import de.htwg.memory.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * this class represent the setting activity
 * in this actitvity it is possible to set the the size of the memory board and set the playername
 * both parameters necessary for the GameActivity.class
 *  
 * @author Simon Schneeberger <sischnee@gmail.com>
 * @version V1.0 18-01-2014
 */
public class SettingActivity extends Activity {
	
	private String name = "Mustermann";
	private String size = "4x4";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.einstellung);
        name = (String) getIntent().getExtras().get("name");
        size = (String) getIntent().getExtras().get("size");
        
        //playername        
        EditText spielername = (EditText)findViewById(R.id.editSpielername);
        spielername.setText(name);
        spielername.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {/* Nothing*/ }
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {/* Nothing*/ }
			
			//after Text is changed
			public void afterTextChanged(Editable s) {
				name = s.toString();	
			}
		});
        
        //memoryboard size
        //init radioButton group
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        RadioButton button16 = (RadioButton)findViewById(R.id.radio16); 

        button16.setChecked(true);
   
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {

		        switch (checkedId) {
		          case R.id.radio16 : size = "4x4";
		          	break;
		          default: size = "4x4";
		        }
			}
		});

        //finish the activity and send the parameters "size" and "name" back to the startActivity.class 
        //both parameters are strings
        Button button = (Button)findViewById(R.id.backButton);
        button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent= getIntent();
				intent.putExtra("size", size);
				intent.putExtra("name", name);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
    }
}
