package com.example.safetexter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ParentSettings extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parent_settings, menu);
		return true;
	}
	
	public void saveSettings(View v) {
		
		//will create blank page and fill in so parent can see what they entered
		Intent save = new Intent(this, ParentSettings.class);
		startActivity(save);
	}
	
	

}
