package com.example.safetexter;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startTimer(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void startTimer(View v) {
		
		final Intent mainIntent = new Intent(this, WelcomeScreen.class);
		new Handler().postDelayed(new Runnable() {
	        @Override
	        public void run() {
	            startActivity(mainIntent);
	            finish();
	        }
	    }, 2000);
		
	}

	
	
}
